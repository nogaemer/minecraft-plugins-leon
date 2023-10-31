package de.nogaemer.minecraft.chunkeffect

import de.nogaemer.minecraft.Main
import de.nogaemer.minecraft.utils.CustomFileManager
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType

class ChunkEffectRepository {
    private val chunkEffects = mutableListOf<ChunkEffectModel>()
    private val configManager = CustomFileManager(Main.instance.dataFolder, "chunk_effects.yml")
    private val cfg = configManager.cfg

    init {
        loadChunkEffects()
    }

    private fun loadChunkEffects() {
        cfg.getConfigurationSection("chunkEffects")?.getKeys(false)?.forEach { key ->
                val world = Bukkit.getWorld(cfg.getString("chunkEffects.$key.world") ?: "world")
                val chunk = world?.getChunkAt(
                    cfg.getInt("chunkEffects.$key.chunk.x"), cfg.getInt("chunkEffects.$key.chunk.z")
                )
                val effect =
                    PotionEffectType.getByName(cfg.getString("chunkEffects.$key.effect").toString()) ?: throw Exception(
                        "Effect not found"
                    )
                val amplifier = cfg.getInt("chunkEffects.$key.amplifier")

                if (world != null && chunk != null) {
                    chunkEffects.add(ChunkEffectModel(key, world, chunk, effect, amplifier))
                }
            }
    }

    private fun saveChunkEffects() {
        cfg.set("chunkEffects", null)
        chunkEffects.forEach { chunkEffect ->
            val key = chunkEffect.id
            cfg.set("chunkEffects.$key.world", chunkEffect.world.name)
            cfg.set("chunkEffects.$key.chunk.x", chunkEffect.chunk.x)
            cfg.set("chunkEffects.$key.chunk.z", chunkEffect.chunk.z)
            cfg.set("chunkEffects.$key.effect", chunkEffect.effect.name)
            cfg.set("chunkEffects.$key.amplifier", chunkEffect.amplifier)
        }
        configManager.saveAllFiles()
    }

    fun getChunkEffectsFromPlayer(player: Player): List<ChunkEffectModel> {
        return chunkEffects.filter { chunkEffect ->
            chunkEffect.activePlayers.contains(player)
        }
    }

    fun getChunkEffectsByLocation(location: Location): List<ChunkEffectModel> {
        return chunkEffects.filter { chunkEffect ->
            chunkEffect.world == location.world && chunkEffect.chunk.x == location.chunk.x && chunkEffect.chunk.z == location.chunk.z
        }
    }

    fun getChunkEffectsByEffect(effect: PotionEffectType, location: Location): List<ChunkEffectModel> {
        return chunkEffects.filter { chunkEffect ->
            chunkEffect.effect == effect && chunkEffect.world == location.world && chunkEffect.chunk.x == location.chunk.x && chunkEffect.chunk.z == location.chunk.z
        }
    }

    fun addChunkEffect(chunkEffect: ChunkEffectModel) {
        chunkEffects.add(chunkEffect)
        saveChunkEffects()
    }

    fun editChunkEffect(chunkEffect: ChunkEffectModel) {
        chunkEffects.removeIf { it.id == chunkEffect.id }
        chunkEffects.add(chunkEffect)
        saveChunkEffects()
    }

    fun removeChunkEffect(chunkEffect: ChunkEffectModel) {
        chunkEffect.activePlayers.forEach { player ->
            player.removePotionEffect(chunkEffect.effect)
        }
        chunkEffects.removeIf { it.id == chunkEffect.id }
        saveChunkEffects()
    }


}