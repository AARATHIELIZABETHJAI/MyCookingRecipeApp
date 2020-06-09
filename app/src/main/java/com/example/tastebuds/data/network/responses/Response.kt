
data class ResponseData(
        val number: Int,
        val offset: Int,
        val results: List<Result>,
        val totalResults: Int
)

data class Result(
        val id: Int,
        val image: String,
        val imageUrls: List<String>,
        val readyInMinutes: Int,
        val servings: Int,
        val title: String
)