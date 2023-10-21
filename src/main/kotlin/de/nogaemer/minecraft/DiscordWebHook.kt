package de.nogaemer.minecraft

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class DiscordWebHook(
    id: String,
    token: String
) {
    private val url = "https://discord.com/api/webhooks/$id/$token"

    fun sendMsg(message: String): String {
        val msgId: String

        val connection = URL("$url?wait=true").openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true

        val outputStream = connection.outputStream
        outputStream.write(message.toByteArray())
        outputStream.flush()
        outputStream.close()

        if (connection.responseCode == 429)
            throw Exception("Error while sending message: ${connection.responseCode} ${connection.responseMessage}\n${message}" +
                    "\n${connection.errorStream.readBytes().toString(Charsets.UTF_8)}" +
                    "\n${connection.headerFields}")

        msgId = JSONObject(connection.inputStream.readBytes().toString(Charsets.UTF_8)).getString("id")

        connection.inputStream.close()
        connection.disconnect()
        return msgId
    }

    fun editMsg(messageId: String, message: String): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$url/messages/$messageId"))
            .header("Content-Type", "application/json")
            .method("PATCH", HttpRequest.BodyPublishers.ofString(message))
            .build()

        val response: HttpResponse<String> =
            HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString())

        return response.statusCode() == 200
    }

    fun deleteMsg(messageId: String): Boolean {
        val deleted: Boolean

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