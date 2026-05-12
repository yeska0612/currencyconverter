package mn.num.flashstudy.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mn.num.flashstudy.data.Flashcard
import mn.num.flashstudy.data.FlashcardDeck
import mn.num.flashstudy.logic.LeitnerManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyScreen(
    deck: FlashcardDeck,
    onBackClick: () -> Unit,
    onDeckUpdated: (FlashcardDeck) -> Unit
) {
    val cards = remember(deck.id) {
        deck.cards
            .filter { LeitnerManager.isDueForReview(it) }
            .ifEmpty { deck.cards }
            .toMutableStateList()
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }

    if (cards.isEmpty()) {
        EmptyStudyState(onBackClick = onBackClick)
        return
    }

    val safeIndex = currentIndex.coerceIn(0, cards.lastIndex)
    val currentCard = cards[safeIndex]
    val progress = (safeIndex + 1).toFloat() / cards.size.toFloat()

    fun updateCard(updatedCard: Flashcard) {
        cards[safeIndex] = updatedCard

        val nextIndex =
            if (cards.size <= 1) 0
            else (safeIndex + 1) % cards.size

        currentIndex = nextIndex
        isFlipped = false

        val updatedDeck = deck.copy(
            cards = deck.cards.map { card ->
                if (card.id == updatedCard.id) updatedCard else card
            }.toMutableList(),
            lastStudied = System.currentTimeMillis()
        )

        onDeckUpdated(updatedDeck)
    }

    Scaffold(
        containerColor = Color(0xFFF5F7FB),
        topBar = {
            TopAppBar(
                title = {
                    Text("Study Session", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                color = Color(0xFF4A90E2),
                trackColor = Color(0xFFD6E4F5)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "${safeIndex + 1} / ${cards.size}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(30.dp))

            StudyFlashcard(
                card = currentCard,
                isFlipped = isFlipped,
                onCardClick = {
                    isFlipped = !isFlipped
                }
            )

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                Button(
                    onClick = {
                        updateCard(
                            LeitnerManager.markForReview(currentCard)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC62828)
                    ),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Review"
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text("To Review")
                }

                Button(
                    onClick = {
                        updateCard(
                            LeitnerManager.markAsKnown(currentCard)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2E7D32)
                    ),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Known"
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text("I Knew")
                }
            }
        }
    }
}

@Composable
fun StudyFlashcard(
    card: Flashcard,
    isFlipped: Boolean,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(430.dp)
            .clickable { onCardClick() },
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Surface(
                shape = RoundedCornerShape(50.dp),
                color = if (card.needsReview) Color(0xFFFFEBEE) else Color(0xFFE8F5E9)
            ) {
                Text(
                    text = if (card.needsReview) "Needs Review" else "Good Progress",
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                    color = if (card.needsReview) Color(0xFFC62828) else Color(0xFF2E7D32),
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Leitner Box ${card.leitnerBox}",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(40.dp))

            AnimatedContent(
                targetState = isFlipped,
                label = "flashcard_flip"
            ) { flipped ->
                Text(
                    text = if (flipped) card.definition else card.term,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = "Tap card to flip",
                color = Color.Gray
            )
        }
    }
}

@Composable
fun EmptyStudyState(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FB))
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "No cards available",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Add some flashcards first.",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onBackClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4A90E2)
            ),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text("Go Back")
        }
    }
}