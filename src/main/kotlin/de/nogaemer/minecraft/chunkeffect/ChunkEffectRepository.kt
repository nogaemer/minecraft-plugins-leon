package de.nogaemer.minecraft.chunkeffect

import de.nogaemer.minecraft.Main
import de.nogaemer.minecraft.utils.CustomFileManager
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType
import java.io.File
import java.util.*

class ChunkEffectRepository {
    private val chunkEffects = mutableListOf<ChunkEffectModel>()
    private val configManager = CustomFileManager(File(Main.instance.dataFolder, "discord.yml"))
    private val cfg = configManager.cfg

    init {
        loadChunkEffects()
    }

    private fun loadChunkEffects() {
        cfg.getConfigurationSection("chunkEffects")
            ?.getKeys(false)
            ?.forEach { key ->
                val world = Bukkit.getWorld(cfg.getString("chunkEffects.$key.world") ?: "world")
                val chunk = world?.getChunkAt(
                    cfg.getInt("chunkEffects.$key.chunk.x"),
                    cfg.getInt("chunkEffects.$key.chunk.z")
                )
                val effect = PotionEffectType.getByName(cfg.getString("chunkEffects.$key.effect")
                    ?: throw Exception("Effect not found"))
                    ?: throw Exception("Effect not found")

                if (world != null && chunk != null) {
                    chunkEffects.add(ChunkEffectModel(world, chunk, effect))
                }
            }
    }

    private fun saveChunkEffects() {
        cfg.set("chunkEffects", null)
        chunkEffects.forEach { chunkEffect ->
            val key = UUID.randomUUID().toString()
            cfg.set("chunkEffects.$key.world", chunkEffect.world.name)
            cfg.set("chunkEffects.$key.chunk.x", chunkEffect.chunk.x)
            cfg.set("chunkEffects.$key.chunk.z", chunkEffect.chunk.z)
            cfg.set("chunkEffects.$key.effect", chunkEffect.effect.name)
        }
        configManager.saveAllFiles()
    }

    fun getChunkEffectsByPlayer(player: Player): List<ChunkEffectModel> {
        return chunkEffects.filter { chunkEffect ->
            chunkEffect.world == player.world
                    && chunkEffect.chunk.x == player.location.chunk.x
                    && chunkEffect.chunk.z == player.location.chunk.z
        }
    }

    fun getChunkEffectsByLocation(location: Location): List<ChunkEffectModel> {
        return chunkEffects.filter { chunkEffect ->
            chunkEffect.world == location.world
                    && chunkEffect.chunk.x == location.chunk.x
                    && chunkEffect.chunk.z == location.chunk.z
        }
    }

    fun addChunkEffect(chunkEffect: ChunkEffectModel) {
        chunkEffects.add(chunkEffect)
        saveChunkEffects()
    }


}