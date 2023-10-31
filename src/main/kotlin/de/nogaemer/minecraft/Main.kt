package de.nogaemer.minecraft

import de.miraculixx.kpaper.main.KPaper
import de.nogaemer.minecraft.chunkeffect.ChunkEffectCommand
import de.nogaemer.minecraft.chunkeffect.ChunkEffectListener
import de.nogaemer.minecraft.chunkeffect.ChunkEffectRepository
import de.nogaemer.minecraft.chunkeffect.ChunkEffectService
import de.nogaemer.minecraft.discord.DiscordMsgSender
import de.nogaemer.minecraft.utils.Updater


class Main : KPaper() {

    var chunkEffectRepository: ChunkEffectRepository? = null
    var chunkEffectService: ChunkEffectService? = null
    private var discordMsgSender: DiscordMsgSender? = null

    companion object {
        lateinit var instance: Main
            private set
    }

    override fun load() {
        instance = this
    }

    override fun startup() {
        // Initialize services
        chunkEffectRepository = ChunkEffectRepository()
        chunkEffectService = ChunkEffectService(chunkEffectRepository!!)
        discordMsgSender = DiscordMsgSender()

        //
        Updater().update()

        // Register commands
        getCommand("chunk")!!.setExecutor(ChunkEffectCommand())
        getCommand("chunk")!!.tabCompleter = ChunkEffectCommand()

        // Register listeners
        ChunkEffectListener
    }

    override fun shutdown() {
        discordMsgSender!!.shutdown()
    }
}