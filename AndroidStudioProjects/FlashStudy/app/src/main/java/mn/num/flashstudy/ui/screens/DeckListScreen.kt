package mn.num.flashstudy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mn.num.flashstudy.data.FlashcardDeck
import mn.num.flashstudy.logic.LeitnerManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DeckListScreen(
    decks: List<FlashcardDeck>,
    onDeckClick: (FlashcardDeck) -> Unit,
    onAddDeckClick: () -> Unit,
    onEditDeckClick: (FlashcardDeck) -> Unit,
    onDeleteDeckClick: (FlashcardDeck) -> Unit
) {

    var searchQuery by remember {
        mutableStateOf("")
    }

    val filteredDecks = decks.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddDeckClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Deck",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F7FB))
        ) {

            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFF4A90E2)
                    )
                    .padding(24.dp)
            ) {

                Column {

                    Text(
                        text = "FlashStudy",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Master your flashcards efficiently",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = searchQuery,

                        onValueChange = {
                            searchQuery = it
                        },

                        placeholder = {
                            Text("Search decks...")
                        },

                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },

                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(18.dp),

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "My Flashcard Sets",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = 20.dp,
                    vertical = 8.dp
                ),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                items(filteredDecks) { deck ->

                    DeckItem(
                        deck = deck,

                        onClick = {
                            onDeckClick(deck)
                        },

                        onEditClick = {
                            onEditDeckClick(deck)
                        },

                        onDeleteClick = {
                            onDeleteDeckClick(deck)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DeckItem(
    deck: FlashcardDeck,
    onClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),

        onClick = onClick,

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE3F2FD)),

                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = deck.title.take(2).uppercase(),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1976D2)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = deck.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = deck.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }

                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit"
                    )
                }

                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                StatusChip(
                    text = "${deck.cards.size} Cards",
                    background = Color(0xFFE8F5E9),
                    content = Color(0xFF2E7D32)
                )

                StatusChip(
                    text = "${calculateMastery(deck)}% Mastered",
                    background = Color(0xFFE3F2FD),
                    content = Color(0xFF1565C0)
                )

                StatusChip(
                    text = "${calculateToReview(deck)} Review",
                    background = Color(0xFFFFEBEE),
                    content = Color(0xFFC62828)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Last studied: ${formatLastStudied(deck.lastStudied)}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun StatusChip(
    text: String,
    background: Color,
    content: Color
) {

    Surface(
        shape = RoundedCornerShape(50.dp),
        color = background
    ) {

        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 6.dp
            ),
            color = content,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
    }
}

fun calculateMastery(deck: FlashcardDeck): Int {

    if (deck.cards.isEmpty()) return 0

    val masteredCount = deck.cards.count {
        it.isMastered
    }

    return masteredCount * 100 / deck.cards.size
}

fun calculateToReview(deck: FlashcardDeck): Int {

    return deck.cards.count { card ->
        LeitnerManager.isDueForReview(card)
    }
}

fun formatLastStudied(time: Long): String {

    val formatter = SimpleDateFormat(
        "MMM dd, yyyy",
        Locale.getDefault()
    )

    return formatter.format(Date(time))
}