package de.nogaemer.minecraft.chunkeffect

import de.nogaemer.minecraft.Main
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType
import java.util.*

class ChunkEffectCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        val player = sender as? Player ?: return false
        var amplifier = 1

        if ((args?.size ?: return false) < 1) return false
        if ((args.size ?: return false) >= 2) amplifier = args[1].toInt()

        val effect = PotionEffectType.getByName(args[0])
            ?: throw Exception("Effect not found")

        val chunkEffectRepository = Main.instance.chunkEffectRepository ?: throw Exception("ChunkEffectRepository not found")
        val chunkEffectService = Main.instance.chunkEffectService ?: throw Exception("ChunkEffectService not found")
        val chunkEffect = ChunkEffectModel(UUID.randomUUID().toString(), player.world, player.location.chunk, effect, amplifier)

        val chunkEffectsByEffect = chunkEffectRepository.getChunkEffectsByEffect(effect, player.location)
        if (chunkEffectsByEffect.isNotEmpty()){
            chunkEffectsByEffect.forEach(chunkEffectRepository::removeChunkEffect)
            return true
        }

        chunkEffectRepository.addChunkEffect(chunkEffect)
        chunkEffectService.updateChunkEffects()



        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String> {
        return PotionEffectType.values()
            .map { it.name }
            .toMutableList()
            .filter { it.startsWith(args?.get(0) ?: "") }
            .toMutableList()
    }

}