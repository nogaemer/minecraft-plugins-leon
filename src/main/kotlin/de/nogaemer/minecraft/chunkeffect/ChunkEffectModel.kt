package de.nogaemer.minecraft.chunkeffect

import org.bukkit.Chunk
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType

data class ChunkEffectModel(
    val id: String,
    val world: World,
    val chunk: Chunk,
    val effect: PotionEffectType,
    val amplifier: Int,
    val activePlayers: MutableList<Player> = mutableListOf()
)