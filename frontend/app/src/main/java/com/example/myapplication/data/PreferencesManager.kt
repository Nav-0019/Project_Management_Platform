package com.example.myapplication.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("ProManagePrefs", Context.MODE_PRIVATE)

    fun saveThemeMode(mode: String) { // "system", "light", "dark"
        prefs.edit().putString("theme_mode", mode).apply()
    }

    fun getThemeMode(): String {
        return prefs.getString("theme_mode", "system") ?: "system"
    }

    fun saveRole(role: String?) {
        prefs.edit().putString("user_role", role).apply()
    }

    fun getRole(): String? {
        return prefs.getString("user_role", null)
    }

    fun saveActiveUser(email: String, name: String, id: String, dept: String) {
        prefs.edit()
            .putString("active_email", email)
            .putString("active_name", name)
            .putString("active_id", id)
            .putString("active_dept", dept)
            .apply()
    }

    fun getActiveEmail(): String {
        return prefs.getString("active_email", "") ?: ""
    }

    fun getActiveName(): String {
        return prefs.getString("active_name", "User") ?: "User"
    }

    fun getActiveId(): String {
        return prefs.getString("active_id", "ID_UNSET") ?: "ID_UNSET"
    }

    fun getActiveDept(): String {
        return prefs.getString("active_dept", "Department") ?: "Department"
    }

    fun saveProjectMode(mode: String) { // "team", "research", ""
        prefs.edit().putString("project_mode", mode).apply()
    }

    fun getProjectMode(): String {
        return prefs.getString("project_mode", "") ?: ""
    }

    fun saveAccount(email: String, pass: String, role: String, name: String, id: String, dept: String) {
        prefs.edit()
            .putString("account_email_$email", email)
            .putString("account_pass_$email", pass)
            .putString("account_role_$email", role)
            .putString("account_name_$email", name)
            .putString("account_id_$email", id)
            .putString("account_dept_$email", dept)
            .apply()
    }

    fun verifyAccount(email: String, pass: String): String? {
        val savedPass = prefs.getString("account_pass_$email", null)
        if (savedPass != null && savedPass == pass) {
            return prefs.getString("account_role_$email", "student")
        }
        return null
    }
    
    fun getAccountName(email: String): String {
        return prefs.getString("account_name_$email", "User") ?: "User"
    }

    fun getAccountId(email: String): String {
        return prefs.getString("account_id_$email", "ID_UNSET") ?: "ID_UNSET"
    }

    fun getAccountDept(email: String): String {
        return prefs.getString("account_dept_$email", "Department") ?: "Department"
    }

    fun clear() {
        val theme = getThemeMode() // Preserve theme on logout
        prefs.edit().clear().apply()
        saveThemeMode(theme)
    }
}
