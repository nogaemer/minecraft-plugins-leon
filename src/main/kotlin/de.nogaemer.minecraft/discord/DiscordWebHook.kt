package de.nogaemer.minecraft.discord

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class DiscordWebHook(
    val id: String,
    val token: String
) {
    private val url = "https://discord.com/api/webhooks/$id/$token"

    fun sendMsg(message: String): String {
        var msgId: String;

        val connection = URL("$url?wait=true").openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true

        val outputStream = connection.outputStream
        outputStream.write(message.toString().toByteArray())
        outputStream.flush()
        outputStream.close()

        msgId = JSONObject(connection.inputStream.readBytes().toString(Charsets.UTF_8)).getString("id")

        connection.inputStream.close()
        connection.disconnect()
        return msgId
    }

    fun editMsg(message: String, messageId: String): Boolean {
        var deleted: Boolean;

        val connection = URL("$url/messages/$messageId").openConnection() as HttpURLConnection
        connection.requestMethod = "PATCH"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true

        val outputStream = connection.outputStream
        outputStream.write(message.toString().toByteArray())
        outputStream.flush()
        outputStream.close()

        deleted = connection.responseCode == 200

        connection.inputStream.close()
        connection.disconnect()
        return deleted
    }

    fun deleteMsg(messageId: String): Boolean {
        var deleted: Boolean;

        val connection = URL("$url/messages/$messageId").openConnection() as HttpURLConnection
        connection.requestMethod = "DELETE"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true

        deleted = connection.responseCode == 204

        connection.inputStream.close()
        connection.disconnect()
        return deleted
    }
}