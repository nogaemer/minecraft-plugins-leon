package de.nogaemer.minecraft

import de.miraculixx.kpaper.main.KPaper

class Main: KPaper() {

    override fun startup() {
        Updater().update()
    }
}