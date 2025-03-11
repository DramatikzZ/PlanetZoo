import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GeminiHelper {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = ApiKeys.GEMINI_API_KEY
    )

    suspend fun fetchAnimalInfo(animalName: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent("Décris-moi un $animalName.")
                Log.d("GeminiHelper", "Réponse API : ${response.text}")

                response.text ?: "Aucune information trouvée."
            } catch (e: Exception) {
                Log.e("GeminiHelper", "Erreur API : ${e.message}")
                "Erreur: ${e.message}"
            }
        }
    }
}
