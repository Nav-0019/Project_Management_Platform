package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.components.*
import com.example.myapplication.ui.screens.*
import com.example.myapplication.ui.theme.ProManageTheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.data.PreferencesManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = PreferencesManager(this)
        
        // ONE-TIME MOCK DATA WIPE to force fresh start for the user
        val sp = getSharedPreferences("ProManagePrefs", MODE_PRIVATE)
        if (!sp.getBoolean("mock_wiped", false)) {
            prefs.clear()
            sp.edit().putBoolean("mock_wiped", true).apply()
        }

        setContent { 
            var themeMode by remember { mutableStateOf(prefs.getThemeMode()) }
            val isDark = when(themeMode) {
                "dark" -> true
                "light" -> false
                else -> isSystemInDarkTheme()
            }
            
            ProManageTheme(darkTheme = isDark) { 
                App(
                    prefs = prefs,
                    themeMode = themeMode,
                    onThemeChange = { 
                        themeMode = it
                        prefs.saveThemeMode(it)
                    }
                ) 
            } 
        }
    }
}

@Composable
fun App(prefs: PreferencesManager, themeMode: String, onThemeChange: (String) -> Unit) {
    // Unconditionally show splash screen first
    var screen by remember { mutableStateOf("splash") }
    var role by remember { mutableStateOf(prefs.getRole() ?: "student") }
    var activeTab by remember { mutableStateOf(if (role == "admin") "dashboard" else "home") }
    var projectMode by remember { mutableStateOf(prefs.getProjectMode()) }
    var showChat by remember { mutableStateOf(false) }
    var showNotifications by remember { mutableStateOf(false) }

    val studentNav = listOf(
        NavItem("home", "Home", Icons.Default.Home),
        NavItem("chat", "Chat", Icons.Default.Email),
        NavItem("resources", "Resources", Icons.AutoMirrored.Filled.MenuBook),
        NavItem("settings", "Settings", Icons.Default.Settings)
    )
    val facultyNav = listOf(
        NavItem("home", "Home", Icons.Default.Home),
        NavItem("chat", "Chat", Icons.Default.Email),
        NavItem("review", "Review Hub", Icons.Default.RateReview),
        NavItem("form", "Form", Icons.AutoMirrored.Filled.Send),
        NavItem("settings", "Settings", Icons.Default.Settings)
    )
    val adminNav = listOf(
        NavItem("dashboard", "Dashboard", Icons.Default.Dashboard),
        NavItem("requests", "Requests", Icons.Default.Inbox),
        NavItem("chat", "Chat", Icons.Default.Email),
        NavItem("section", "Add", Icons.Default.AddBox),
        NavItem("settings", "Settings", Icons.Default.Settings)
    )

    when (screen) {
        "splash" -> SplashScreen(onDone = { 
            if (prefs.getRole() != null) {
                screen = "app"
            } else {
                screen = "auth"
            }
        })
        "auth" -> AuthScreen(prefs = prefs, onLogin = { selectedRole ->
            role = selectedRole
            prefs.saveRole(selectedRole)
            activeTab = if (selectedRole == "admin") "dashboard" else "home"
            screen = "app"
        })
        "app" -> {
            val navItems = when (role) {
                "faculty" -> facultyNav
                "admin" -> adminNav
                else -> studentNav
            }
            val title = when (role) {
                "student" -> mapOf("home" to "Home", "chat" to "Chat", "scorecard" to "Scorecard", "resources" to "Resources", "notifications" to "Notifications", "profile" to "Profile", "findGuide" to "Browse Faculty", "browse" to "Browse Faculty", "submit" to "Submit Project", "status" to "Submission Status", "review" to "Faculty Review", "progress" to "Progress", "eval" to "Evaluation", "group_chat" to "Team Group Chat", "mentor_chat" to "Mentor Chat", "settings" to "Settings")
                "faculty" -> mapOf("home" to "Dashboard", "chat" to "Chat", "registrations" to "Registrations", "review" to "Review Hub", "form" to "Create Form", "profile" to "Profile", "settings" to "Settings")
                else -> mapOf("dashboard" to "Admin Dashboard", "chat" to "Chat", "requests" to "Requests", "section" to "Add Section", "profile" to "Profile", "manageusers" to "Manage Users", "timeline" to "Timeline Config", "reports" to "System Reports", "lock" to "Lock Submissions", "complaints" to "Help & Complaints", "notify" to "Send Notification", "settings" to "Settings")
            }

            // Hide chrome on full-screen chat/settings pages
            val isChatScreen = activeTab in listOf("group_chat", "mentor_chat", "settings")

            Scaffold(
                topBar = {
                    if (!isChatScreen) PMTopBar(
                        title = title[activeTab] ?: "ProManage",
                        role = role,
                        onProfileClick = { activeTab = "profile" },
                        onNotificationsClick = { showNotifications = !showNotifications }
                    )
                },
                bottomBar = {
                    if (!isChatScreen) PMBottomNav(items = navItems, activeRoute = activeTab, onSelect = { activeTab = it })
                },
                floatingActionButton = {}
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                    when (role) {
                        "student" -> when (activeTab) {
                            "home" -> StudentHomeFull(prefs = prefs, mode = projectMode, onModeChange = { projectMode = it; prefs.saveProjectMode(it) }, onNavigate = { activeTab = it })
                            "scorecard" -> StudentScorecard()
                            "chat" -> ChatScreen()
                            "resources" -> StudentResources()
                            "notifications" -> StudentNotificationsPage()
                            "profile" -> StudentProfile(prefs = prefs, themeMode = themeMode, onThemeChange = onThemeChange, onLogout = { prefs.clear(); role = "student"; projectMode = ""; activeTab = "home"; screen = "auth" }, onOpenSettings = { activeTab = "settings" })
                            "findGuide", "browse" -> StudentFindGuide(onBack = { activeTab = "home" })
                            "submit" -> ResearchSubmission(onBack = { activeTab = "home" }, onSubmit = { activeTab = "status" })
                            "status" -> ResearchSubmissionStatus(onBack = { activeTab = "home" }, onViewProgress = { activeTab = "progress" })
                            "review" -> ResearchFacultyReview(onBack = { activeTab = "home" }, onProceed = { activeTab = "progress" })
                            "progress" -> ResearchProgressTracking(onBack = { activeTab = "home" }, onViewEval = { activeTab = "eval" })
                            "eval" -> ResearchEvaluation(onBack = { activeTab = "progress" })
                            "group_chat" -> TeamGroupChatScreen(onBack = { activeTab = "home" })
                            "mentor_chat" -> MentorChatScreen(onBack = { activeTab = "home" })
                            "settings" -> SettingsScreen(themeMode = themeMode, onThemeChange = { onThemeChange(it); prefs.saveThemeMode(it) }, onBack = { activeTab = "home" }, onLogout = { prefs.clear(); role = "student"; projectMode = ""; activeTab = "home"; screen = "auth" })
                            else -> StudentHomeFull(prefs = prefs, mode = projectMode, onModeChange = { projectMode = it; prefs.saveProjectMode(it) }, onNavigate = { activeTab = it })
                        }
                        "faculty" -> when (activeTab) {
                            "home" -> FacultyHome(prefs = prefs)
                            "chat" -> ChatScreen()
                            "registrations" -> FacultyRegistrations()
                            "review" -> FacultyReviewHub()
                            "form" -> FacultyForm()
                            "profile" -> FacultyProfile(
                                prefs = prefs,
                                themeMode = themeMode,
                                onThemeChange = onThemeChange,
                                onLogout = { 
                                    prefs.clear()
                                    role = "student"
                                    projectMode = ""
                                    activeTab = "home"
                                    screen = "auth" 
                                },
                                onOpenSettings = { activeTab = "settings" }
                            )
                            "settings" -> SettingsScreen(themeMode = themeMode, onThemeChange = { onThemeChange(it); prefs.saveThemeMode(it) }, onBack = { activeTab = "home" }, onLogout = { prefs.clear(); role = "student"; projectMode = ""; activeTab = "home"; screen = "auth" })
                            else -> FacultyHome(prefs = prefs)
                        }
                        "admin" -> when (activeTab) {
                            "dashboard" -> AdminDashboard(setAdminTab = { activeTab = it })
                            "chat" -> ChatScreen()
                            "requests" -> AdminRequests()
                            "section" -> AdminAddSection()
                            "manageusers" -> AdminManageUsers()
                            "timeline" -> AdminTimeline()
                            "reports" -> AdminReports()
                            "lock" -> AdminLockSubmissions()
                            "complaints" -> AdminComplaints()
                            "notify" -> AdminNotificationForm()
                            "profile" -> AdminProfile(prefs = prefs, themeMode = themeMode, onThemeChange = onThemeChange, setAdminTab = { activeTab = it }, onLogout = { prefs.clear(); role = "student"; projectMode = ""; activeTab = "home"; screen = "auth" }, onOpenSettings = { activeTab = "settings" })
                            "settings" -> SettingsScreen(themeMode = themeMode, onThemeChange = { onThemeChange(it); prefs.saveThemeMode(it) }, onBack = { activeTab = "home" }, onLogout = { prefs.clear(); role = "student"; projectMode = ""; activeTab = "home"; screen = "auth" })
                            else -> AdminDashboard(setAdminTab = { activeTab = it })
                        }
                    }

                    if (showNotifications) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.3f))
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                                ) { showNotifications = false },
                            contentAlignment = androidx.compose.ui.Alignment.TopCenter
                        ) {
                            Box(modifier = Modifier.padding(top = 8.dp).clickable(indication = null, interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }) {}) {
                                NotificationPanel(
                                    onClose = { showNotifications = false },
                                    onViewAll = { 
                                        showNotifications = false
                                        activeTab = "notifications"
                                    }
                                )
                            }
                        }
                    }


                }
            }
        }
    }
}