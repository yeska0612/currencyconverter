package mn.num.flashstudy.logic

import mn.num.flashstudy.data.Flashcard

object LeitnerManager {

    fun markAsKnown(card: Flashcard): Flashcard {
        val newBox = (card.leitnerBox + 1).coerceAtMost(5)

        return card.copy(
            leitnerBox = newBox,
            needsReview = false,
            isMastered = newBox == 5,
            lastReviewed = System.currentTimeMillis()
        )
    }

    fun markForReview(card: Flashcard): Flashcard {
        return card.copy(
            leitnerBox = 1,
            needsReview = true,
            isMastered = false,
            lastReviewed = System.currentTimeMillis()
        )
    }

    fun isDueForReview(card: Flashcard): Boolean {
        if (card.needsReview) return true

        val now = System.currentTimeMillis()
        val daysPassed = (now - card.lastReviewed) / (1000 * 60 * 60 * 24)

        val intervalDays = when (card.leitnerBox) {
            1 -> 1
            2 -> 2
            3 -> 4
            4 -> 7
            else -> 14
        }

        return daysPassed >= intervalDays
    }
}