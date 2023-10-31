package de.nogaemer.minecraft.chunkeffect

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect

class ChunkEffectService(
    private val chunkEffectRepository: ChunkEffectRepository
) {

    init {
        updateChunkEffects()
    }

    fun updateChunkEffects() {
        Bukkit.getOnlinePlayers().forEach { player ->
            updateChunkEffects(player, player.location)
        }
    }

    fun updateChunkEffects(player: Player, location: Location) {
        chunkEffectRepository.getChunkEffectsFromPlayer(player).forEach { chunkEffect ->
            player.removePotionEffect(chunkEffect.effect)
            chunkEffect.activePlayers.remove(player)
            chunkEffectRepository.editChunkEffect(chunkEffect)
        }


        chunkEffectRepository.getChunkEffectsByLocation(location).forEach { chunkEffect ->
            player.addPotionEffect(PotionEffect(chunkEffect.effect, Int.MAX_VALUE, chunkEffect.amplifier))
            chunkEffect.activePlayers.add(player)
            chunkEffectRepository.editChunkEffect(chunkEffect)
        }
    }


}