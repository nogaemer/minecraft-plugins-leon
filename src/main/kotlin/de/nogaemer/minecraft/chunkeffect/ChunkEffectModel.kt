package de.nogaemer.minecraft.chunkeffect

import org.bukkit.Chunk
import org.bukkit.World
import org.bukkit.potion.PotionEffectType

data class ChunkEffectModel(
    val world: World,
    val chunk: Chunk,
    val effect: PotionEffectType
){
    val x: Int
        get() = chunk.x

    val z: Int
        get() = chunk.z

    fun getChunkLocation(): IntArray {
        return intArrayOf(chunk.x, chunk.z)
    }
}