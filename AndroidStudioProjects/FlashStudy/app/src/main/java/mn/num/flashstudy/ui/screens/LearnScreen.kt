package mn.num.flashstudy.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mn.num.flashstudy.data.FlashcardDeck
import mn.num.flashstudy.logic.LeitnerManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnScreen(
    deck: FlashcardDeck,
    onBackClick: () -> Unit,
    onDeckUpdated: (FlashcardDeck) -> Unit
) {
    val cards = remember(deck) {
        deck.cards
            .filter { card ->
                LeitnerManager.isDueForReview(card)
            }
            .ifEmpty {
                deck.cards
            }
            .toMutableStateList()
    }

    var currentIndex by remember {
        mutableIntStateOf(0)
    }

    var answer by remember {
        mutableStateOf("")
    }

    var resultText by remember {
        mutableStateOf("")
    }

    if (cards.isEmpty()) {
        Text("No cards available")
        return
    }

    val currentCard = cards[currentIndex]

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Learn Mode", fontWeight = FontWeight.Bold)
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

            Text(
                text = "${currentIndex + 1} / ${cards.size}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Term",
                        style = MaterialTheme.typography.bodySmall
                    )

                    Text(
                        text = currentCard.term,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = answer,
                onValueChange = {
                    answer = it
                },
                label = {
                    Text("Write definition")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val isCorrect =
                        answer.trim().equals(currentCard.definition.trim(), ignoreCase = true)

                    cards[currentIndex] =
                        if (isCorrect) {
                            resultText = "Correct!"
                            LeitnerManager.markAsKnown(currentCard)
                        } else {
                            resultText = "Wrong. Correct answer: ${currentCard.definition}"
                            LeitnerManager.markForReview(currentCard)
                        }

                    val updatedCard = cards[currentIndex]

                    val updatedDeck = deck.copy(
                        cards = deck.cards.map { card ->
                            if (card.id == updatedCard.id) {
                                updatedCard
                            } else {
                                card
                            }
                        }.toMutableList(),
                        lastStudied = System.currentTimeMillis()
                    )

                    onDeckUpdated(updatedDeck)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = answer.isNotBlank()
            ) {
                Text("Check Answer")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = resultText,
                style = MaterialTheme.typography.bodyMedium,
                color = if (resultText.startsWith("Correct")) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedButton(
                onClick = {
                    if (currentIndex < cards.lastIndex) {
                        currentIndex++
                        answer = ""
                        resultText = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = resultText.isNotBlank() && currentIndex < cards.lastIndex
            ) {
                Text("Next")
            }
        }
    }
}