package mn.num.flashstudy.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FlashcardStorage {

    private const val PREF_NAME = "flashstudy_prefs"
    private const val KEY_DECKS = "flashstudy_decks"

    fun saveDecks(context: Context, decks: List<FlashcardDeck>) {
        val json = Gson().toJson(decks)

        context
            .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_DECKS, json)
            .apply()
    }

    fun loadDecks(context: Context): List<FlashcardDeck> {
        val json = context
            .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_DECKS, null)

        if (json.isNullOrBlank()) {
            return SampleData.sampleDecks
        }

        val type = object : TypeToken<List<FlashcardDeck>>() {}.type

        return Gson().fromJson(json, type)
    }
}