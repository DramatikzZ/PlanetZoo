import android.util.Log
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
object GeminiHelper {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = ApiKeys.GEMINI_API_KEY
    )

    suspend fun fetchAnimalInfo(animalName: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val prompt = """
                Donne-moi une description courte et bien structurée pour $animalName en 6 lignes maximum :
                - Nom : $animalName
                - Origine : [De quel pays il vient]
                - Habitat : [Son habitat naturel, par ex. savane, forêt, etc.]
                - Alimentation : [Ce qu'il mange, par ex. carnivore, omnivore, etc.]
                - Fun Fact : [Un fait intéressant sur lui]
            """.trimIndent()

                val response = generativeModel.generateContent(prompt)
                Log.d("GeminiHelper", "Réponse API : ${response.text}")

                response.text ?: "Aucune information trouvée."
            } catch (e: Exception) {
                Log.e("GeminiHelper", "Erreur API : ${e.message}")
                "Erreur: ${e.message}"
            }
        }
    }

    fun parseAnimalInfoAnnotated(info: String): AnnotatedString {
        val lines = info.split("\n").map { it.trim() }.filter { it.isNotEmpty() }

        return buildAnnotatedString {
            append("🌍 ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Origine : ")
            }
            appendLine(lines.getOrNull(1)?.removePrefix("Origine :")?.trim() ?: "Inconnu")

            append("\n🏞 ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Habitat : ")
            }
            appendLine(lines.getOrNull(2)?.removePrefix("Habitat :")?.trim() ?: "Inconnu")

            append("\n🍽 ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Alimentation : ")
            }
            appendLine(lines.getOrNull(3)?.removePrefix("Alimentation :")?.trim() ?: "Inconnu")

            append("\n🤩 ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Fun Fact : ")
            }
            appendLine(lines.getOrNull(4)?.removePrefix("Fun Fact :")?.trim() ?: "Aucun")
        }
    }



    suspend fun fetchAnimalImage(animalName: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val apiKey = "AIzaSyDBjOJvzH-pJZ3vXYAZN9cO6IqWEeOrtlE"
                val searchEngineId = "67b6ba1039bef4815"
                val searchQuery = "$animalName animal"
                val url = "https://www.googleapis.com/customsearch/v1?q=$searchQuery&cx=$searchEngineId&searchType=image&key=$apiKey"

                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonResponse = JSONObject(response)
                val imageUrl = jsonResponse.getJSONArray("items").getJSONObject(0).getString("link")

                Log.d("GeminiHelper", "Image trouvée: $imageUrl")
                imageUrl
            } catch (e: Exception) {
                Log.e("GeminiHelper", "Erreur API pour image : ${e.message}")
                "https://via.placeholder.com/150"
            }
        }
    }
}
