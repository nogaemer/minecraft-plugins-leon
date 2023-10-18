package de.nogaemer.minecraft

import org.bukkit.Bukkit
import org.json.JSONObject
import java.awt.Desktop
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.net.HttpURLConnection
import java.net.URL


class Updater {
    val jsonObject = getRepoJson()

    fun update() {
        println(jsonObject)
        println(jsonObject.getString("name"))
        println(jsonObject.getJSONArray("assets").getJSONObject(0).getString("browser_download_url"))
        downloadJarFromUrl(jsonObject
            .getJSONArray("assets")
            .getJSONObject(0)
            .getString("browser_download_url"),
            File(Bukkit.getPluginsFolder().path + "\\leon1.jar"))

        createBatFile("restart.bat", "java -jar leon1.jar")
        println(batchMsg)
    }

    fun getRepoJson(): JSONObject {
        return JSONObject(URL("https://api.github.com/repos/update4j/update4j/releases/latest").readText())
    }

    fun downloadJarFromUrl(url: String, destinationFile: File) {
        val urlConnection = URL(url).openConnection() as HttpURLConnection
        val inputStream = urlConnection.inputStream
        val outputStream = FileOutputStream(destinationFile)

        inputStream.copyTo(outputStream)

        inputStream.close()
        outputStream.close()

        val mp3 = File(
            "C:\\Users\\Noah\\Downloads\\test.bat"
        )
        Desktop.getDesktop().open(mp3)
    }

    fun createBatFile(batFileName: String, batFileContents: String) {
        val batFile = File(batFileName)
        val printWriter = PrintWriter(batFile)

        printWriter.println(batFileContents)

        printWriter.flush()
        printWriter.close()
    }

    fun runBatFile(batFileName: String): Int {
        val processBuilder = ProcessBuilder(batFileName)
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT)
        processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT)

        val process = processBuilder.start()
        process.waitFor()

        return process.exitValue()
    }

    val batchMsg = "\n" +
            " _______   __                      __                  __    __                  __              __                                                           \n" +
            "|       \\ |  \\                    |  \\                |  \\  |  \\                |  \\            |  \\                                                          \n" +
            "| \$\$\$\$\$\$\$\\| \$\$ __    __   ______   \\\$\$ _______        | \$\$  | \$\$  ______    ____| \$\$  ______   _| \$\$_     ______    ______                                    \n" +
            "| \$\$__/ \$\$| \$\$|  \\  |  \\ /      \\ |  \\|       \\       | \$\$  | \$\$ /      \\  /      \$\$ |      \\ |   \$\$ \\   /      \\  /      \\                                   \n" +
            "| \$\$    \$\$| \$\$| \$\$  | \$\$|  \$\$\$\$\$\$\\| \$\$| \$\$\$\$\$\$\$\\      | \$\$  | \$\$|  \$\$\$\$\$\$\\|  \$\$\$\$\$\$\$  \\\$\$\$\$\$\$\\ \\\$\$\$\$\$\$  |  \$\$\$\$\$\$\\|  \$\$\$\$\$\$\\                                  \n" +
            "| \$\$\$\$\$\$\$ | \$\$| \$\$  | \$\$| \$\$  | \$\$| \$\$| \$\$  | \$\$      | \$\$  | \$\$| \$\$  | \$\$| \$\$  | \$\$ /      \$\$  | \$\$ __ | \$\$    \$\$| \$\$   \\\$\$                                  \n" +
            "| \$\$      | \$\$| \$\$__/ \$\$| \$\$__| \$\$| \$\$| \$\$  | \$\$      | \$\$__/ \$\$| \$\$__/ \$\$| \$\$__| \$\$|  \$\$\$\$\$\$\$  | \$\$|  \\| \$\$\$\$\$\$\$\$| \$\$                                        \n" +
            "| \$\$      | \$\$ \\\$\$    \$\$ \\\$\$    \$\$| \$\$| \$\$  | \$\$       \\\$\$    \$\$| \$\$    \$\$ \\\$\$    \$\$ \\\$\$    \$\$   \\\$\$  \$\$ \\\$\$     \\| \$\$                                        \n" +
            " \\\$\$       \\\$\$  \\\$\$\$\$\$\$  _\\\$\$\$\$\$\$\$ \\\$\$ \\\$\$   \\\$\$        \\\$\$\$\$\$\$ | \$\$\$\$\$\$\$   \\\$\$\$\$\$\$\$  \\\$\$\$\$\$\$\$    \\\$\$\$\$   \\\$\$\$\$\$\$\$ \\\$\$                                        \n" +
            "                        |  \\__| \$\$                              | \$\$                                                                                          \n" +
            "                         \\\$\$    \$\$                              | \$\$                                                                                          \n" +
            "                          \\\$\$\$\$\$\$                                \\\$\$                                                                                          \n" +
            " __       __                  __                  __                        __    __   ______    ______    ______   ________  __       __  ________  _______  \n" +
            "|  \\     /  \\                |  \\                |  \\                      |  \\  |  \\ /      \\  /      \\  /      \\ |        \\|  \\     /  \\|        \\|       \\ \n" +
            "| \$\$\\   /  \$\$  ______    ____| \$\$  ______        | \$\$____   __    __       | \$\$\\ | \$\$|  \$\$\$\$\$\$\\|  \$\$\$\$\$\$\\|  \$\$\$\$\$\$\\| \$\$\$\$\$\$\$\$| \$\$\\   /  \$\$| \$\$\$\$\$\$\$\$| \$\$\$\$\$\$\$\\\n" +
            "| \$\$\$\\ /  \$\$\$ |      \\  /      \$\$ /      \\       | \$\$    \\ |  \\  |  \\      | \$\$\$\\| \$\$| \$\$  | \$\$| \$\$ __\\\$\$| \$\$__| \$\$| \$\$__    | \$\$\$\\ /  \$\$\$| \$\$__    | \$\$__| \$\$\n" +
            "| \$\$\$\$\\  \$\$\$\$  \\\$\$\$\$\$\$\\|  \$\$\$\$\$\$\$|  \$\$\$\$\$\$\\      | \$\$\$\$\$\$\$\\| \$\$  | \$\$      | \$\$\$\$\\ \$\$| \$\$  | \$\$| \$\$|    \\| \$\$    \$\$| \$\$  \\   | \$\$\$\$\\  \$\$\$\$| \$\$  \\   | \$\$    \$\$\n" +
            "| \$\$\\\$\$ \$\$ \$\$ /      \$\$| \$\$  | \$\$| \$\$    \$\$      | \$\$  | \$\$| \$\$  | \$\$      | \$\$\\\$\$ \$\$| \$\$  | \$\$| \$\$ \\\$\$\$\$| \$\$\$\$\$\$\$\$| \$\$\$\$\$   | \$\$\\\$\$ \$\$ \$\$| \$\$\$\$\$   | \$\$\$\$\$\$\$\\\n" +
            "| \$\$ \\\$\$\$| \$\$|  \$\$\$\$\$\$\$| \$\$__| \$\$| \$\$\$\$\$\$\$\$      | \$\$__/ \$\$| \$\$__/ \$\$      | \$\$ \\\$\$\$\$| \$\$__/ \$\$| \$\$__| \$\$| \$\$  | \$\$| \$\$_____ | \$\$ \\\$\$\$| \$\$| \$\$_____ | \$\$  | \$\$\n" +
            "| \$\$  \\\$ | \$\$ \\\$\$    \$\$ \\\$\$    \$\$ \\\$\$     \\      | \$\$    \$\$ \\\$\$    \$\$      | \$\$  \\\$\$\$ \\\$\$    \$\$ \\\$\$    \$\$| \$\$  | \$\$| \$\$     \\| \$\$  \\\$ | \$\$| \$\$     \\| \$\$  | \$\$\n" +
            " \\\$\$      \\\$\$  \\\$\$\$\$\$\$\$  \\\$\$\$\$\$\$\$  \\\$\$\$\$\$\$\$       \\\$\$\$\$\$\$\$  _\\\$\$\$\$\$\$\$       \\\$\$   \\\$\$  \\\$\$\$\$\$\$   \\\$\$\$\$\$\$  \\\$\$   \\\$\$ \\\$\$\$\$\$\$\$\$ \\\$\$      \\\$\$ \\\$\$\$\$\$\$\$\$ \\\$\$   \\\$\$\n" +
            "                                                           |  \\__| \$\$                                                                                         \n" +
            "                                                            \\\$\$    \$\$                                                                                         \n" +
            "                                                             \\\$\$\$\$\$\$                                                                                          \n"

}