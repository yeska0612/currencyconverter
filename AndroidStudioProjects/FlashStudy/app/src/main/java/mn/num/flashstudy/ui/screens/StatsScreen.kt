package mn.num.flashstudy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mn.num.flashstudy.data.FlashcardDeck
import mn.num.flashstudy.logic.LeitnerManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    deck: FlashcardDeck,
    onBackClick: () -> Unit
) {
    val totalCards = deck.cards.size
    val mastered = deck.cards.count { it.isMastered }
    val toReview = deck.cards.count { LeitnerManager.isDueForReview(it) }
    val learning = totalCards - mastered
    val masteryPercent = if (totalCards == 0) 0 else mastered * 100 / totalCards

    Scaffold(
        containerColor = Color(0xFFF5F7FB),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Deck Stats",
                        fontWeight = FontWeight.Bold
                    )
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
                .background(Color(0xFFF5F7FB))
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF4A90E2))
                    .padding(24.dp)
            ) {
                Column {
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

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "$masteryPercent%",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "Mastery Progress",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    LinearProgressIndicator(
                        progress = {
                            masteryPercent / 100f
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp),
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.3f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                StatCard(
                    title = "Total Cards",
                    value = totalCards.toString(),
                    icon = Icons.Default.List,
                    background = Color(0xFFE3F2FD),
                    content = Color(0xFF1565C0)
                )

                StatCard(
                    title = "Mastered",
                    value = mastered.toString(),
                    icon = Icons.Default.CheckCircle,
                    background = Color(0xFFE8F5E9),
                    content = Color(0xFF2E7D32)
                )

                StatCard(
                    title = "To Review",
                    value = toReview.toString(),
                    icon = Icons.Default.Refresh,
                    background = Color(0xFFFFEBEE),
                    content = Color(0xFFC62828)
                )

                StatCard(
                    title = "Learning",
                    value = learning.toString(),
                    icon = Icons.Default.Star,
                    background = Color(0xFFFFF8E1),
                    content = Color(0xFFF9A825)
                )
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    background: Color,
    content: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp),
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

            Surface(
                modifier = Modifier.size(52.dp),
                shape = RoundedCornerShape(16.dp),
                color = background
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = content
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
            }
        }
    }
}