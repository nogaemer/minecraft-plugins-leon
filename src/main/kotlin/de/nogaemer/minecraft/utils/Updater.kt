package de.nogaemer.minecraft.utils

import de.nogaemer.minecraft.Main
import org.bukkit.Bukkit
import org.json.JSONObject
import java.awt.Desktop
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.PrintWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.io.path.Path
import kotlin.io.path.pathString


class Updater {
    private val jsonObject = getRepoJson()

    fun update() {

        deleteFile(File(Bukkit.getPluginsFolder().path + "\\update.bat"))

        if (!isVersionLower(Main.instance.pluginMeta.version, jsonObject.getString("tag_name")))
            return

        downloadJarFromUrl(
            jsonObject
                .getJSONArray("assets")
                .getJSONObject(0)
                .getString("browser_download_url"),
            File(Bukkit.getPluginsFolder().path + "\\leon1.jar")
        )

        runBatFile(
            createBatFile("@echo off \n" +
                        batchMsg +
                        "timeout /T 5 \n" +
                        "cd ${Bukkit.getPluginsFolder().absolutePath} \n" +
                        "del leon.jar \n" +
                        "move leon1.jar leon.jar \n" +
                        "cd ..\n" +
                        "${Path(Bukkit.getPluginsFolder().absolutePath).parent.pathString}\\start.bat \n" +
                        "@Pause \n"
            )
        )

        Bukkit.shutdown()
    }

    private fun getRepoJson(): JSONObject {
        return JSONObject(URL("https://api.github.com/repos/nogaemer/minecraft-plugins-leon/releases/latest").readText())
    }

    private fun downloadJarFromUrl(url: String, destinationFile: File) {
        val urlConnection = URL(url).openConnection() as HttpURLConnection
        val inputStream = urlConnection.inputStream
        val outputStream = FileOutputStream(destinationFile)

        inputStream.copyTo(outputStream)

        inputStream.close()
        outputStream.close()
    }

    fun isVersionLower(version1: String, version2: String): Boolean {
        val version1Parts = version1.split(".")
        val version2Parts = version2.split(".")

        // Compare the version parts one by one.
        for (i in 0..<3) {
            val version1Part = version1Parts[i].toInt()
            val version2Part = version2Parts[i].toInt()

            if (version1Part < version2Part) {
                return true
            } else if (version1Part > version2Part) {
                return false
            }
        }

        // If all the version parts are the same, then the versions are equal.
        return false
    }


    private fun createBatFile(batFileContents: String): File {
        val batFile = File(Bukkit.getPluginsFolder().absolutePath + "/update.bat")
        val printWriter = PrintWriter(batFile)

        printWriter.println(batFileContents)

        printWriter.flush()
        printWriter.close()
        return batFile
    }

    private fun runBatFile(batFileName: File) {
        Desktop.getDesktop().open(batFileName)
    }

    private fun deleteFile(file: File) {
        if (!file.exists()) {
            return
        }

        if (!file.delete()) {
            throw IOException("Failed to delete file: ${file.absolutePath}")
        }
    }


    private val batchMsg = "\n" +
            "ECHO ______  _                _          _   _             _         _                                    \n" +
            "ECHO ^| ___ ^\\^| ^|              (_)        ^| ^| ^| ^|           ^| ^|       ^| ^|                                   \n" +
            "ECHO ^| ^|_/ /^| ^| _   _   __ _  _  _ __   ^| ^| ^| ^| _ __    __^| ^|  __ _ ^| ^|_   ___  _ __                      \n" +
            "ECHO ^|  __/ ^| ^|^| ^| ^| ^| / _` ^|^| ^|^| '_ ^\\  ^| ^| ^| ^|^| '_ ^\\  / _` ^| / _` ^|^| __^| / _ ^\\^| '__^|                     \n" +
            "ECHO ^| ^|    ^| ^|^| ^|_^| ^|^| (_^| ^|^| ^|^| ^| ^| ^| ^| ^|_^| ^|^| ^|_) ^|^| (_^| ^|^| (_^| ^|^| ^|_ ^|  __/^| ^|                        \n" +
            "ECHO ^\\_^|    ^|_^| ^\\__,_^| ^\\__, ^|^|_^|^|_^| ^|_^|  ^\\___/ ^| .__/  ^\\__,_^| ^\\__,_^| ^\\__^| ^\\___^|^|_^|                        \n" +
            "ECHO                    __/ ^|                  ^| ^|                                                        \n" +
            "ECHO                   ^|___/                   ^|_^|                                                        \n" +
            "ECHO ___  ___            _         _              _   _  _____  _____   ___   _____ ___  ___ _____ ______ \n" +
            "ECHO ^|  ^\\/  ^|           ^| ^|       ^| ^|            ^| ^\\ ^| ^|^|  _  ^|^|  __ ^\\ / _ ^\\ ^|  ___^|^|  ^\\/  ^|^|  ___^|^| ___ ^\\\n" +
            "ECHO ^| .  . ^|  __ _   __^| ^|  ___  ^| ^|__   _   _  ^|  ^\\^| ^|^| ^| ^| ^|^| ^|  ^\\// /_^\\ ^\\^| ^|__  ^| .  . ^|^| ^|__  ^| ^|_/ /\n" +
            "ECHO ^| ^|^\\/^| ^| / _` ^| / _` ^| / _ ^\\ ^| '_ ^\\ ^| ^| ^| ^| ^| . ` ^|^| ^| ^| ^|^| ^| __ ^|  _  ^|^|  __^| ^| ^|^\\/^| ^|^|  __^| ^|    / \n" +
            "ECHO ^| ^|  ^| ^|^| (_^| ^|^| (_^| ^|^|  __/ ^| ^|_) ^|^| ^|_^| ^| ^| ^|^\\  ^|^\\ ^\\_/ /^| ^|_^\\ ^\\^| ^| ^| ^|^| ^|___ ^| ^|  ^| ^|^| ^|___ ^| ^|^\\ ^\\ \n" +
            "ECHO ^\\_^|  ^|_/ ^\\__,_^| ^\\__,_^| ^\\___^| ^|_.__/  ^\\__, ^| ^\\_^| ^\\_/ ^\\___/  ^\\____/^\\_^| ^|_/^\\____/ ^\\_^|  ^|_/^\\____/ ^\\_^| ^\\_^|\n" +
            "ECHO                                       __/ ^|                                                          \n" +
            "ECHO                                      ^|___/                                                           \n"

}