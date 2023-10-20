package de.nogaemer.minecraft

import de.miraculixx.kpaper.main.KPaper
import de.nogaemer.minecraft.discord.DiscordMsgSender


class Main : KPaper() {

    private var discordMsgSender: DiscordMsgSender? = null

    companion object {
        lateinit var instance: Main
    }

    override fun load() {
        instance = this
    }

    override fun startup() {
        discordMsgSender = DiscordMsgSender()
        Updater().update()


        println("test")

        /*Bukkit.getScheduler().runTaskLater(Main.instance, Runnable { ConsoleManager().runConsoleCommand("msg NoGaemer test", worlds[0]) }, 10)
        val statusWebhook = DiscordWebHook("1164570552388829345", "vdH20y_XgGbRJ-lkrstZ0NOIpwkjRPAIv_OpcXpVux-Ei5ok6I_DmZyV4zGfFSq067OJ")
        var test = 0
        val log = DiscordLogMsg(File("C:\\Users\\Noah\\Desktop\\log.txt")).getMsgAsList().forEach() { msg ->
            Bukkit.getScheduler().runTaskLater(Main.instance, Runnable {
                webhook.sendMsg(msg)
                test++
           }, test.toLong() * 20)
        }
*/
    }

    override fun shutdown() {
        discordMsgSender!!.shutdown()
    }
}