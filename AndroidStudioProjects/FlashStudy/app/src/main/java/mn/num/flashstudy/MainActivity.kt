package mn.num.flashstudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import mn.num.flashstudy.data.Flashcard
import mn.num.flashstudy.data.FlashcardDeck
import mn.num.flashstudy.data.SampleData
import mn.num.flashstudy.ui.screens.AddCardScreen
import mn.num.flashstudy.ui.screens.CardListScreen
import mn.num.flashstudy.ui.screens.CreateDeckScreen
import mn.num.flashstudy.ui.screens.DeckListScreen
import mn.num.flashstudy.ui.screens.StudyScreen
import mn.num.flashstudy.ui.theme.FlashStudyTheme
import mn.num.flashstudy.ui.screens.LearnScreen
import androidx.compose.ui.platform.LocalContext
import mn.num.flashstudy.data.FlashcardStorage
import mn.num.flashstudy.ui.screens.StatsScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            FlashStudyTheme {
                FlashStudyApp()
            }
        }
    }
}

@Composable
fun FlashStudyApp() {

    val context = LocalContext.current

    var decks by remember {
        mutableStateOf(FlashcardStorage.loadDecks(context))
    }

    LaunchedEffect(decks) {
        FlashcardStorage.saveDecks(context, decks)
    }

    var selectedDeck by remember {
        mutableStateOf<FlashcardDeck?>(null)
    }

    var isStudyMode by remember {
        mutableStateOf(false)
    }

    var isCreateDeckMode by remember {
        mutableStateOf(false)
    }

    var isAddCardMode by remember {
        mutableStateOf(false)
    }

    var editingCard by remember {
        mutableStateOf<Flashcard?>(null)
    }

    var editingDeck by remember {
        mutableStateOf<FlashcardDeck?>(null)
    }

    var isLearnMode by remember {
        mutableStateOf(false)
    }

    var isStatsMode by remember {
        mutableStateOf(false)
    }

    if (isAddCardMode && selectedDeck != null) {

        AddCardScreen(
            existingCard = editingCard,

            onBackClick = {
                isAddCardMode = false
                editingCard = null
            },

            onSaveCard = { savedCard ->

                val updatedCards =
                    if (editingCard == null) {
                        selectedDeck!!.cards + savedCard
                    } else {
                        selectedDeck!!.cards.map { card ->
                            if (card.id == savedCard.id) savedCard else card
                        }
                    }

                val updatedDeck = selectedDeck!!.copy(
                    cards = updatedCards.toMutableList(),
                    lastStudied = System.currentTimeMillis()
                )

                decks = decks.map { deck ->
                    if (deck.id == updatedDeck.id) updatedDeck else deck
                }

                selectedDeck = updatedDeck
                editingCard = null
                isAddCardMode = false
            }
        )

    } else if (isCreateDeckMode) {

        CreateDeckScreen(
            existingDeck = editingDeck,

            onBackClick = {
                isCreateDeckMode = false
                editingDeck = null
            },

            onSaveDeck = { savedDeck ->

                decks =
                    if (editingDeck == null) {
                        decks + savedDeck
                    } else {
                        decks.map { deck ->
                            if (deck.id == savedDeck.id) savedDeck else deck
                        }
                    }

                selectedDeck =
                    if (selectedDeck?.id == savedDeck.id) {
                        savedDeck
                    } else {
                        selectedDeck
                    }

                isCreateDeckMode = false
                editingDeck = null
            }
        )

    } else if (selectedDeck == null) {

        DeckListScreen(
            decks = decks,

            onDeckClick = { deck ->
                selectedDeck = deck
            },

            onAddDeckClick = {
                editingDeck = null
                isCreateDeckMode = true
            },

            onEditDeckClick = { deck ->
                editingDeck = deck
                isCreateDeckMode = true
            },

            onDeleteDeckClick = { deck ->
                decks = decks.filter { it.id != deck.id }

                if (selectedDeck?.id == deck.id) {
                    selectedDeck = null
                }
            }
        )
    } else if (isLearnMode) {

        LearnScreen(
            deck = selectedDeck!!,

            onBackClick = {
                isLearnMode = false
            },

            onDeckUpdated = { updatedDeck ->
                decks = decks.map { deck ->
                    if (deck.id == updatedDeck.id) updatedDeck else deck
                }

                selectedDeck = updatedDeck
            }
        )
    } else if (isStatsMode) {

        StatsScreen(
            deck = selectedDeck!!,
            onBackClick = {
                isStatsMode = false
            }
        )
    } else if (isStudyMode) {

        StudyScreen(
            deck = selectedDeck!!,

            onBackClick = {
                isStudyMode = false
            },

            onDeckUpdated = { updatedDeck ->

                decks = decks.map { deck ->
                    if (deck.id == updatedDeck.id) updatedDeck else deck
                }

                selectedDeck = updatedDeck
            }
        )

    } else {

        CardListScreen(
            deck = selectedDeck!!,

            onBackClick = {
                selectedDeck = null
                isStudyMode = false
                isLearnMode = false
                isStatsMode = false
                isAddCardMode = false
                editingCard = null
            },

            onLearnClick = {
                isLearnMode = true
            },
            onStatsClick = {
                isStatsMode = true
            },

            onStudyClick = {
                isStudyMode = true
            },

            onAddCardClick = {
                editingCard = null
                isAddCardMode = true
            },

            onEditCardClick = { card ->
                editingCard = card
                isAddCardMode = true
            },


            onDeleteCardClick = { card ->

                val updatedDeck = selectedDeck!!.copy(
                    cards = selectedDeck!!.cards
                        .filter { it.id != card.id }
                        .toMutableList(),
                    lastStudied = System.currentTimeMillis()
                )

                decks = decks.map { deck ->
                    if (deck.id == updatedDeck.id) updatedDeck else deck
                }

                selectedDeck = updatedDeck
            }
        )
    }
}