package de.nogaemer.minecraft.discord

import de.miraculixx.kpaper.event.listen
import de.nogaemer.minecraft.Main
import de.nogaemer.minecraft.utils.CustomFileManager
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DiscordMsgSender {

    private val cfgManager = CustomFileManager(File(Main.instance.dataFolder, "discord.yml"))
    private val cfg = cfgManager.cfg
    private val statusMsg = DiscordStatusMsg()
    private val logMsg = DiscordLogMsg(cfgManager, File("${File(".").absolutePath}\\logs\\latest.log"))
    private var statusMsgId: String? = cfg.getConfigurationSection("status")?.getString("messageId")

    val logWebhook = DiscordWebHook(
        cfg.getConfigurationSection("log")?.getString("id")
            ?: throw NoSuchFieldError("Bitte füge die DiscordWebhookId in ${cfg.currentPath} hinzu"),
        cfg.getConfigurationSection("log")?.getString("token")
            ?: throw NoSuchFieldError("Bitte füge den DiscordWebhookToken in ${cfg.currentPath} hinzu")
    )

    private val statusWebhook = DiscordWebHook(
        cfg.getConfigurationSection("status")?.getString("id")
            ?: throw NoSuchFieldError("Bitte füge die DiscordWebhookId in ${cfg.currentPath} hinzu"),
        cfg.getConfigurationSection("status")?.getString("token")
            ?: throw NoSuchFieldError("Bitte füge den DiscordWebhookToken in ${cfg.currentPath} hinzu")
    )

    init {
        statusMsg.isRunning = true

        if (statusMsgId == null) {
            statusMsgId = statusWebhook.sendMsg(statusMsg.status())

            cfg.getConfigurationSection("status")
                ?.set("messageId", statusMsgId)
            cfgManager.saveAllFiles()
        } else {
            statusWebhook.editMsg(statusMsgId!!, statusMsg.status())
        }

        logWebhook.sendMsg(
            "{\n" +
                    "  \"content\": \"# Server Start\\n```\\nPlugin Version: ${Main.instance.description.version}\\n\\n\\n${
                        LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                        )
                    }\\n```\",\n" +
                    "  \"embeds\": null,\n" +
                    "  \"username\": \"Status\",\n" +
                    "  \"avatar_url\": \"https://styles.redditmedia.com/t5_sihjn/styles/communityIcon_3ytvy6tdjkz41.jpg\",\n" +
                    "  \"attachments\": []\n" +
                    "}"
        )
        var delay = 0

        Bukkit.getScheduler().runTaskTimer(Main.instance, Runnable {
            delay -= 5
            delay = delay.coerceAtLeast(0)

            statusWebhook.editMsg(statusMsgId!!, statusMsg.status())
            delay ++

            val msgAsList = logMsg.getMsgAsList()
            msgAsList.forEach() { msg ->
                Bukkit.getScheduler().runTaskLater(Main.instance, Runnable {
                    logWebhook.sendMsg(msg)
                }, delay.toLong() * 30)
                delay++
            }

        }, 0, 10 * 20L)

    }

    fun shutdown() {
        statusMsg.isRunning = false
        statusWebhook.editMsg(statusMsgId!!, statusMsg.status())
    }


    val onJoin = listen<PlayerJoinEvent> {
        statusWebhook.editMsg(statusMsgId!!, statusMsg.status())
    }

    val onQuit = listen<PlayerQuitEvent> {
        Bukkit.getScheduler().runTaskLater(Main.instance, Runnable {
            statusWebhook.editMsg(statusMsgId!!, statusMsg.status())
        }, 100L)
    }

}
