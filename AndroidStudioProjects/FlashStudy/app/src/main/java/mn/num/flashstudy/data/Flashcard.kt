package mn.num.flashstudy.data

data class Flashcard(
    val id: String,
    val term: String,
    val definition: String,

    // Leitner box (1 - 5)
    val leitnerBox: Int = 1,

    // Давтах шаардлагатай эсэх
    val needsReview: Boolean = false,

    // Master болсон эсэх
    val isMastered: Boolean = false,

    // Хамгийн сүүлд үзсэн хугацаа
    val lastReviewed: Long = System.currentTimeMillis()
)