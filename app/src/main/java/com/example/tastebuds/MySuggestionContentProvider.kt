package com.example.tastebuds

import android.content.SearchRecentSuggestionsProvider

class MySuggestionContentProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(
            AUTHORITY,
            MODE
        )
    }

    companion object {
        const val AUTHORITY = "com.example.tastebuds.MySuggestionContentProvider"
        const val MODE: Int = SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES
    }
}
