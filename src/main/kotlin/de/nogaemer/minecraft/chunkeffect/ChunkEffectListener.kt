package de.nogaemer.minecraft.chunkeffect

import de.miraculixx.kpaper.event.listen
import de.nogaemer.minecraft.Main
import org.bukkit.event.player.PlayerMoveEvent

object ChunkEffectListener {

    val playerMoveListener = listen<PlayerMoveEvent> {
        if (it.from.chunk != it.to.chunk) {
            Main.instance.chunkEffectService?.updateChunkEffects(it.player, it.to)
        }
    }
}