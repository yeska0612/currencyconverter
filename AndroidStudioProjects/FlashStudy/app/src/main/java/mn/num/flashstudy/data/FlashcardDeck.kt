package mn.num.flashstudy.data

data class FlashcardDeck(
    val id: String,

    // Deck нэр
    val title: String,

    // Deck тайлбар
    val description: String,

    // Card-ууд
    val cards: MutableList<Flashcard> = mutableListOf(),

    // Хамгийн сүүлд үзсэн хугацаа
    val lastStudied: Long = System.currentTimeMillis()
)