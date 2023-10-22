package de.nogaemer.minecraft.discord

import de.nogaemer.minecraft.utils.CustomFileManager
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class DiscordLogMsg(
    private val cfgFile: CustomFileManager,
    private val logFile: File
){
    private val cfg: YamlConfiguration = cfgFile.cfg

    init {
        cfg.getConfigurationSection("log")
            ?.set("lastLine", 0)

        cfgFile.saveAllFiles()
    }

    fun getMsgAsList(): List<String> {
        val lines = separateFile(logFile)
        val msg = mutableListOf<String>()
        lines.forEach { line ->
            when {
                line.contains("/INFO]") -> {
                    msg.add(this.getMsg(line, DiscordEmbedColors.BLUE.value))
                }
                line.contains("/WARN]") -> {
                    msg.add(this.getMsg(line, DiscordEmbedColors.YELLOW.value))
                }
                line.contains("/ERROR]") -> {
                    msg.add(this.getMsg(line, DiscordEmbedColors.RED.value))
                }
            }
        }
        return msg
    }
    private fun separateFile(file: File): List<String> {
        val lastLine = cfg.getConfigurationSection("log")
            ?.getInt("lastLine")
            ?: 0
        val lines = mutableListOf<String>()
        var currentLine = ""

        file.readLines().forEach { line ->
            if (line.startsWith('[')) {
                lines.add(currentLine)
                currentLine = validateString(line)
            } else {
                currentLine += " ${validateString(line)}"
            }
        }

        lines.add(currentLine)

        removeFirstEntriesOfList(lines, lastLine)

        cfg.getConfigurationSection("log")
            ?.set("lastLine", lines.size + lastLine)
        cfgFile.saveAllFiles()

        return lines
    }

    private fun removeFirstEntriesOfList(list: MutableList<String>, count: Int) {
        for (i in 0..<count) {
            list.removeAt(0)
        }
    }

    private fun getMsg(rawMsg: String, color: Int): String{
        val msg = splitStringIntoTwoParts(rawMsg)

        return "{\n" +
                "  \"content\": null,\n" +
                "  \"embeds\": [\n" +
                "    {\n" +
                "      \"title\": \"${msg[0]}\"," +
                "      \"description\": \"${msg[1]}\",\n" +
                "      \"color\": $color\n" +
                "    }\n" +
                "  ],\n" +
                "  \"username\": \"Status\",\n" +
                "  \"avatar_url\": \"https://styles.redditmedia.com/t5_sihjn/styles/communityIcon_3ytvy6tdjkz41.jpg\",\n" +
                "  \"attachments\": []\n" +
                "}"
    }

    private fun validateString(text: String): String {

        return text
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\t", "\\t")
    }

    private fun splitStringIntoTwoParts(string: String): List<String> {
        val index = string.indexOf(':', string.indexOf(']') + 1)
        return if (index == -1) {
            listOf(string)
        } else {
            listOf(string.substring(0, index + 1), string.substring(index + 1))
        }
    }

}
