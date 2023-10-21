package de.nogaemer.minecraft.utils

import de.nogaemer.minecraft.Main
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.EntityType

class ConsoleManager {

    fun runConsoleCommand(command: String?, world: World) {
        val entity = world.spawnEntity(Location(world, 0.0, 0.0, 0.0), EntityType.MINECART_COMMAND)
        val tempWorld = Bukkit.getWorld("world")
        val gameRule = tempWorld!!.getGameRuleValue(GameRule.SEND_COMMAND_FEEDBACK)!!
        tempWorld.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false)
        Main.instance.server.dispatchCommand(entity, command!!)
        tempWorld.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, gameRule)
        entity.remove()
    }
}
