package mn.num.flashstudy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mn.num.flashstudy.data.Flashcard
import mn.num.flashstudy.data.FlashcardDeck
import mn.num.flashstudy.logic.LeitnerManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListScreen(
    deck: FlashcardDeck,
    onBackClick: () -> Unit,
    onStudyClick: () -> Unit,
    onLearnClick: () -> Unit,
    onStatsClick: () -> Unit,
    onAddCardClick: () -> Unit,
    onEditCardClick: (Flashcard) -> Unit,
    onDeleteCardClick: (Flashcard) -> Unit
) {
    val mastered = deck.cards.count { it.isMastered }
    val toReview = deck.cards.count { LeitnerManager.isDueForReview(it) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddCardClick,
                containerColor = Color(0xFF4A90E2)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Card",
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF4A90E2))
                    .padding(20.dp)
            ) {
                Column {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = deck.title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = deck.description.ifBlank { "No description" },
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        HeaderChip("${deck.cards.size} Cards")
                        HeaderChip("$mastered Mastered")
                        HeaderChip("$toReview Review")
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Button(
                    onClick = onStudyClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = deck.cards.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A90E2)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Study"
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Start Study Session")
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onLearnClick,
                        modifier = Modifier.weight(1f),
                        enabled = deck.cards.isNotEmpty(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "Learn"
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text("Learn")
                    }

                    OutlinedButton(
                        onClick = onStatsClick,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Stats"
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text("Stats")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "List of Cards",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (deck.cards.isEmpty()) {
                EmptyCardState(
                    onAddCardClick = onAddCardClick
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        horizontal = 20.dp,
                        vertical = 8.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(deck.cards) { card ->
                        CardListItem(
                            card = card,
                            onEditClick = {
                                onEditCardClick(card)
                            },
                            onDeleteClick = {
                                onDeleteCardClick(card)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderChip(
    text: String
) {
    Surface(
        color = Color.White.copy(alpha = 0.18f),
        shape = RoundedCornerShape(50.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = Color.White,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun CardListItem(
    card: Flashcard,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val statusColor =
        if (card.needsReview) Color(0xFFC62828) else Color(0xFF2E7D32)

    val statusBackground =
        if (card.needsReview) Color(0xFFFFEBEE) else Color(0xFFE8F5E9)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(statusBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (card.needsReview) {
                        Icons.Default.Warning
                    } else {
                        Icons.Default.CheckCircle
                    },
                    contentDescription = "Status",
                    tint = statusColor
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = card.term,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = card.definition,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    color = statusBackground,
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text(
                        text = "Box ${card.leitnerBox} • ${
                            if (card.needsReview) "Needs Review" else "Good Progress"
                        }",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        color = statusColor,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Card",
                    tint = Color(0xFF4A90E2)
                )
            }

            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Card",
                    tint = Color(0xFFC62828)
                )
            }
        }
    }
}

@Composable
fun EmptyCardState(
    onAddCardClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No cards yet",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Add your first flashcard to start learning.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = onAddCardClick,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4A90E2)
            )
        ) {
            Text("Add Card")
        }
    }
}