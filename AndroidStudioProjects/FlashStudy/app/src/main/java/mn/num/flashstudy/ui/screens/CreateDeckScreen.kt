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
import mn.num.flashstudy.data.FlashcardDeck
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDeckScreen(
    existingDeck: FlashcardDeck? = null,
    onBackClick: () -> Unit,
    onSaveDeck: (FlashcardDeck) -> Unit
) {

    var title by remember {
        mutableStateOf(existingDeck?.title ?: "")
    }

    var description by remember {
        mutableStateOf(existingDeck?.description ?: "")
    }

    Scaffold(
        containerColor = Color(0xFFF5F7FB),

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text =
                            if (existingDeck == null)
                                "Create Deck"
                            else
                                "Edit Deck",

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
                        text =
                            if (existingDeck == null)
                                "Create New Deck"
                            else
                                "Update Deck",

                        style = MaterialTheme.typography.headlineMedium,

                        fontWeight = FontWeight.Bold,

                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Organize your flashcards efficiently.",
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
                    text = "Deck Name",
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1E293B)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = title,

                    onValueChange = {
                        title = it
                    },

                    placeholder = {
                        Text("Enter deck name")
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
                    text = "Description",
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1E293B)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,

                    onValueChange = {
                        description = it
                    },

                    placeholder = {
                        Text("Enter description")
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

                        val savedDeck = FlashcardDeck(

                            id =
                                existingDeck?.id
                                    ?: UUID.randomUUID().toString(),

                            title = title,

                            description = description,

                            cards =
                                existingDeck?.cards
                                    ?: mutableListOf(),

                            lastStudied =
                                existingDeck?.lastStudied
                                    ?: System.currentTimeMillis()
                        )

                        onSaveDeck(savedDeck)
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),

                    enabled = title.isNotBlank(),

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
                        text =
                            if (existingDeck == null)
                                "Save Deck"
                            else
                                "Update Deck",

                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}