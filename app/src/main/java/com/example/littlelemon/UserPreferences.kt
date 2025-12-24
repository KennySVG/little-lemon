package com.example.littlelemon

import android.content.Context

class UserPreferences(context: Context) {

    private val sharedPrefs =
        context.getSharedPreferences("little_lemon_prefs", Context.MODE_PRIVATE)

    fun saveUser(firstName: String, lastName: String, email: String) {
        sharedPrefs.edit()
            .putString("first_name", firstName)
            .putString("last_name", lastName)
            .putString("email", email)
            .putBoolean("onboarding_complete", true)
            .apply()
    }

    fun isOnboardingComplete(): Boolean {
        return sharedPrefs.getBoolean("onboarding_complete", false)
    }

    fun getFirstName(): String? {
        return sharedPrefs.getString("first_name", "")
    }

    fun getLastName(): String? {
        return sharedPrefs.getString("last_name", "")
    }

    fun getEmail(): String? {
        return sharedPrefs.getString("email", "")
    }

    fun clearUserData() {
        sharedPrefs.edit().clear().apply()
    }
}