package de.nogaemer.minecraft

import de.miraculixx.kpaper.extensions.worlds
import de.miraculixx.kpaper.main.KPaper
import de.nogaemer.minecraft.discord.DiscordWebHook
import org.bukkit.Bukkit




class Main: KPaper() {

    companion object {
        lateinit var instance: Main
    }

    override fun load() {
        instance = this
    }

    override fun startup() {
        Updater().update()
        println("test")

        Bukkit.getScheduler().runTaskLater(Main.instance, Runnable { ConsoleManager().runConsoleCommand("msg NoGaemer test", worlds[0]) }, 10)
        val webhook = DiscordWebHook("1164570552388829345", "vdH20y_XgGbRJ-lkrstZ0NOIpwkjRPAIv_OpcXpVux-Ei5ok6I_DmZyV4zGfFSq067OJ")

    }
}