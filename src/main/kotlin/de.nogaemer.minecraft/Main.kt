package de.nogaemer.minecraft

import de.miraculixx.kpaper.main.KPaper

class Main: KPaper() {

    companion object {
        lateinit var instance: Main
            private set
    }

    override fun load() {
        instance = this
    }

    override fun startup() {
        Updater().update()
        Test().test()
        println("test")
    }
}