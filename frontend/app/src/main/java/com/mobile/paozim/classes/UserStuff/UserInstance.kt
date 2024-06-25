package com.mobile.paozim.classes.UserStuff

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder

object UserInstance {
    var Usuario = User("", "", "", "", "", "", "", "", "", "", 0, "", "")
    var logged = false

    fun setUser(context: Context, user: User) {
        Usuario = user
        logged = true
        saveUser(context)
    }
    fun clearUser(context: Context) {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        Usuario = User("", "", "", "", "", "", "", "", "", "", 0, "", "")
        logged = false
    }
    fun saveUser(context: Context) {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val jsonString = GsonBuilder().create().toJson(Usuario)
        sharedPreferences.edit().putString("UserF", jsonString).apply()
        sharedPreferences.edit().putBoolean("Logged", logged).apply()
        Log.d("USER", jsonString)
    }
    fun loadUser(context: Context) {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("UserF", null)
        logged = sharedPreferences.getBoolean("Logged", false)
        if (!json.isNullOrEmpty()) {
            Log.d("USER", json)
            Usuario = GsonBuilder().create().fromJson(json, User::class.java)
        } else {
            Log.d("USER", "null")
        }
    }
    fun logout() {
        Usuario = User("", "", "", "", "", "", "", "", "", "", 0, "", "")
        logged = false
    }
}
