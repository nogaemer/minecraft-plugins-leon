package de.nogaemer.minecraft.chunkeffect

import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect

class ChunkEffectService(
    private val chunkEffectRepository: ChunkEffectRepository
) {

    fun updateChunkEffects(player: Player) {
        chunkEffectRepository.getChunkEffectsByPlayer(player).forEach { chunkEffect ->
            player.addPotionEffect(PotionEffect(chunkEffect.effect, 100, 1))
            player.world.playEffect(Location(chunkEffect.world, chunkEffect.x.toDouble(), 0.0, chunkEffect.z.toDouble()), Effect.MOBSPAWNER_FLAMES, 1)

        }
    }
}