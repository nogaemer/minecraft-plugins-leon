package de.nogaemer.minecraft.utils

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

class CustomFileManager(
    private var cfgFile: File
) {
    var cfg: YamlConfiguration

    init {
        if (!this.cfgFile.parentFile.exists()) {
            this.cfgFile.parentFile.mkdirs()
        }
        if (!this.cfgFile.exists()) {
            try {
                this.cfgFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        cfg = YamlConfiguration.loadConfiguration(this.cfgFile)
    }

    fun saveAllFiles() {
        try {
            cfg.save(cfgFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
