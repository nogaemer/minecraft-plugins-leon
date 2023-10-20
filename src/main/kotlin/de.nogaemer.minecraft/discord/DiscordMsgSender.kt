package de.nogaemer.minecraft.discord

import de.miraculixx.kpaper.event.listen
import de.nogaemer.minecraft.Main
import de.nogaemer.minecraft.utils.CustomFileManager
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.io.File

class DiscordMsgSender {

    private val cfgManager = CustomFileManager(File(Main.instance.dataFolder, "discord.yml"))
    private val cfg = cfgManager.cfg
    private val statusMsg = DiscordStatusMsg()
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


        Bukkit.getScheduler().runTaskTimer(Main.instance, Runnable {
            statusWebhook.editMsg(statusMsgId!!, statusMsg.status())
        }, 60 * 20L, 60 * 20L)

    }

    fun updateLogs() {

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
