package de.nogaemer.minecraft.discord

import org.bukkit.Bukkit

class DiscordStatusMsg{
    var isRunning = true

    fun status(): String = "{\n" +
            "  \"content\": null,\n" +
            "  \"embeds\": [\n" +
            "    {\n" +
            "      \"title\": \"Server Leon\",\n" +
            "      \"description\": \"`98.789.89.453`\",\n" +
            "      \"color\": ${if (isRunning) 6356877 else 16736352}\n" +
            "    },\n" +
            "    ${
                if (isRunning) {
                    "    {\n" +
                            "      \"color\": null,\n" +
                            "      \"fields\": [\n" +
                            "        {\n" +
                            "          \"name\": \"ONLINE\",\n" +
                            "          \"value\": \"${getOnlinePlayers()}\"\n" +
                            "        }\n" +
                            "      ]\n" +
                            "    }\n" 
                } else ""
            }" +
            "  ],\n" +
            "  \"username\": \"Status\",\n" +
            "  \"avatar_url\": \"https://styles.redditmedia.com/t5_sihjn/styles/communityIcon_3ytvy6tdjkz41.jpg\",\n" +
            "  \"attachments\": []\n" +
            "}"

    private fun getOnlinePlayers(): String{
        var msg = ""
        Bukkit.getOnlinePlayers().forEach() { player ->
                msg += "- ${player.name}\\n"
        }
        return msg
    }
}
