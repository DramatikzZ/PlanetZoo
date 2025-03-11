import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApiService {
    @Headers("Content-Type: application/json")
    @POST("models/gemini-2.0-flash:generateContent")
    suspend fun getAnimalInfo(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}
