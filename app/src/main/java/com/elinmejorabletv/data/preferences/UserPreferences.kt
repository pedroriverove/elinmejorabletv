package com.elinmejorabletv.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserPreferences(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var username: String
        get() = preferences.getString(KEY_USERNAME, "") ?: ""
        set(value) = preferences.edit { putString(KEY_USERNAME, value) }

    var email: String
        get() = preferences.getString(KEY_EMAIL, "") ?: ""
        set(value) = preferences.edit { putString(KEY_EMAIL, value) }

    var isLoggedIn: Boolean
        get() = preferences.getBoolean(KEY_LOGGED_IN, false)
        set(value) = preferences.edit { putBoolean(KEY_LOGGED_IN, value) }

    var lastLoginDate: Long
        get() = preferences.getLong(KEY_LAST_LOGIN, 0)
        set(value) = preferences.edit { putLong(KEY_LAST_LOGIN, value) }

    var favoriteTracks: Set<String>
        get() = preferences.getStringSet(KEY_FAVORITE_TRACKS, emptySet()) ?: emptySet()
        set(value) = preferences.edit { putStringSet(KEY_FAVORITE_TRACKS, value) }

    var notificationsEnabled: Boolean
        get() = preferences.getBoolean(KEY_NOTIFICATIONS, true)
        set(value) = preferences.edit { putBoolean(KEY_NOTIFICATIONS, value) }

    var darkModeEnabled: Boolean
        get() = preferences.getBoolean(KEY_DARK_MODE, false)
        set(value) = preferences.edit { putBoolean(KEY_DARK_MODE, value) }

    var lastRefreshTime: Long
        get() = preferences.getLong(KEY_LAST_REFRESH, 0)
        set(value) = preferences.edit { putLong(KEY_LAST_REFRESH, value) }

    fun addFavoriteTrack(trackId: String) {
        val currentFavorites = favoriteTracks.toMutableSet()
        currentFavorites.add(trackId)
        favoriteTracks = currentFavorites
    }

    fun removeFavoriteTrack(trackId: String) {
        val currentFavorites = favoriteTracks.toMutableSet()
        currentFavorites.remove(trackId)
        favoriteTracks = currentFavorites
    }

    fun isFavoriteTrack(trackId: String): Boolean {
        return favoriteTracks.contains(trackId)
    }

    fun clear() {
        preferences.edit { clear() }
    }

    companion object {
        private const val PREFS_NAME = "elinmejorable_preferences"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
        private const val KEY_LOGGED_IN = "logged_in"
        private const val KEY_LAST_LOGIN = "last_login"
        private const val KEY_FAVORITE_TRACKS = "favorite_tracks"
        private const val KEY_NOTIFICATIONS = "notifications_enabled"
        private const val KEY_DARK_MODE = "dark_mode"
        private const val KEY_LAST_REFRESH = "last_refresh"
    }
}