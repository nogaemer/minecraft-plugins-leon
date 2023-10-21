package de.nogaemer.minecraft

import de.miraculixx.kpaper.extensions.kotlin.round
import org.bukkit.Bukkit
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.time.Instant


class DiscordStatusMsg{
    var isRunning = true

    fun status(): String{
        val color = if (isRunning) DiscordEmbedColors.GREEN.value else DiscordEmbedColors.RED.value
        val status = if (isRunning) "\uD83D\uDFE2 Running" else "\uD83D\uDD34 Stopped"

        return "{\n" +
                "  \"content\": null,\n" +
                "  \"embeds\": [\n" +
                "    {\n" +
                "      \"description\": \"# Server Info\",\n" +
                "      \"color\": $color,\n" +
                "      \"fields\": [\n" +
                "        {\n" +
                "          \"name\": \"Server Status\",\n" +
                "          \"value\": \"```$status```\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Server  IP⠀\",\n" +
                "          \"value\": \"```${getIp()}```⠀\",\n" +
                "          \"inline\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Current TPS\",\n" +
                "          \"value\": \"```${getTps()}```\",\n" +
                "          \"inline\": true\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Online Players\",\n" +
                "          \"value\": \"```${if (isRunning && Bukkit.getOnlinePlayers().isNotEmpty()) getOnlinePlayers() else "No Players online..."}```\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"footer\": {\n" +
                "        \"text\": \"Powered by NoGaemer\"\n" +
                "      },\n" +
                "      \"timestamp\": \"${getTimestamp()}\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"attachments\": []\n" +
                "}"
    }

    private fun getOnlinePlayers(): String{
        var msg = ""
        Bukkit.getOnlinePlayers().forEach() { player ->
                msg += " ${player.name}\\n"
        }
        return msg
    }

    private fun getTimestamp(): String {
        val timestamp = Instant.now()
        return timestamp.toString()
    }

    fun getIp(): String {
        val whatIsMyIp = URL("https://checkip.amazonaws.com")
        val `in` = BufferedReader(
            InputStreamReader(
                whatIsMyIp.openStream()
            )
        )

        return `in`.readLine()
    }

    fun getTps(): String {
        if (!isRunning) return " ——— ｜ ——— ｜ ——— "

        var tps = ""
        tps += " ${Bukkit.getTPS()[0].round(1)} ｜"
        tps += " ${Bukkit.getTPS()[1].round(1)} ｜"
        tps += " ${Bukkit.getTPS()[2].round(1)} "
        return tps
    }
}
