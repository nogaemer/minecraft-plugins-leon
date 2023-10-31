package de.nogaemer.minecraft.utils

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException

class CustomFileManager(
    private var cfgFileFolder: File,
    cfgFileName: String
) {
    private var cfgFile: File = cfgFileFolder
    var cfg: YamlConfiguration

    init {
        if (!this.cfgFileFolder.exists()) {
            this.cfgFileFolder.mkdirs()
        }

        cfgFile = File(cfgFileFolder, cfgFileName)

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
