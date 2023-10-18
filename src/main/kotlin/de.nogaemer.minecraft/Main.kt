package de.nogaemer.minecraft

import de.miraculixx.kpaper.main.KPaper
import org.bukkit.Bukkit

class Main: KPaper() {

    override fun startup() {
        Updater().update()
        println("test")
    }
}