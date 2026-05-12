package mn.num.flashstudy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import mn.num.flashstudy.data.Flashcard
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(
    existingCard: Flashcard? = null,
    onBackClick: () -> Unit,
    onSaveCard: (Flashcard) -> Unit
) {
    var term by remember {
        mutableStateOf(existingCard?.term ?: "")
    }

    var definition by remember {
        mutableStateOf(existingCard?.definition ?: "")
    }

    Scaffold(
        containerColor = Color(0xFFF5F7FB),

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (existingCard == null) "Add Card" else "Edit Card",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
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
                        text = if (existingCard == null) "Create Flashcard" else "Update Flashcard",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Add a term and definition for your study deck.",
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {

                Text(
                    text = "Term",
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1E293B)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = term,
                    onValueChange = {
                        term = it
                    },
                    placeholder = {
                        Text("Enter term")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF4A90E2),
                        focusedLabelColor = Color(0xFF4A90E2),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "Definition",
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1E293B)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = definition,
                    onValueChange = {
                        definition = it
                    },
                    placeholder = {
                        Text("Enter definition")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    minLines = 5,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF4A90E2),
                        focusedLabelColor = Color(0xFF4A90E2),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        val savedCard = Flashcard(
                            id = existingCard?.id ?: UUID.randomUUID().toString(),
                            term = term,
                            definition = definition,
                            leitnerBox = existingCard?.leitnerBox ?: 1,
                            needsReview = existingCard?.needsReview ?: false,
                            isMastered = existingCard?.isMastered ?: false,
                            lastReviewed = existingCard?.lastReviewed ?: System.currentTimeMillis()
                        )

                        onSaveCard(savedCard)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    enabled = term.isNotBlank() && definition.isNotBlank(),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A90E2)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save"
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = if (existingCard == null) "Save Card" else "Update Card",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}