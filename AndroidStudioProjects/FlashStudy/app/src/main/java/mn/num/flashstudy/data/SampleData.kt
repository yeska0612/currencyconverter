package mn.num.flashstudy.data

object SampleData {

    val sampleDecks = listOf(
        FlashcardDeck(
            id = "1",
            title = "Spanish Fundamentals",
            description = "Basic Spanish vocabulary",
            cards = mutableListOf(
                Flashcard("1", "manzana", "apple"),
                Flashcard("2", "perro", "dog"),
                Flashcard("3", "casa", "house")
            )
        ),
        FlashcardDeck(
            id = "2",
            title = "Basic Coding Terms",
            description = "Programming vocabulary",
            cards = mutableListOf(
                Flashcard("4", "Variable", "Stores data"),
                Flashcard("5", "Function", "Reusable block of code")
            )
        ),
        FlashcardDeck(
            id = "3",
            title = "Medical Terminology",
            description = "Basic medical terms",
            cards = mutableListOf(
                Flashcard("6", "Cardiology", "Study of heart"),
                Flashcard("7", "Dermatology", "Study of skin")
            )
        )
    )
}