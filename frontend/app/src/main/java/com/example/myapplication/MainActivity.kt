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
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.data.PreferencesManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = PreferencesManager(this)
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
    var screen by remember { mutableStateOf(if (prefs.getRole() != null) "app" else "splash") }
    var role by remember { mutableStateOf(prefs.getRole() ?: "student") }
    var activeTab by remember { mutableStateOf(if (role == "admin") "dashboard" else "home") }
    var projectMode by remember { mutableStateOf(prefs.getProjectMode()) }
    var showChat by remember { mutableStateOf(false) }
    var showNotifications by remember { mutableStateOf(false) }

    val studentNav = listOf(
        NavItem("home", "Home", Icons.Default.Home),
        NavItem("scorecard", "Scorecard", Icons.Default.Star),
        NavItem("resources", "Resources", Icons.AutoMirrored.Filled.MenuBook),
        NavItem("notifications", "Alerts", Icons.Default.Notifications),
        NavItem("profile", "Profile", Icons.Default.Person)
    )
    val facultyNav = listOf(
        NavItem("home", "Home", Icons.Default.Home),
        NavItem("registrations", "Register", Icons.Default.PersonAdd),
        NavItem("review", "Review Hub", Icons.Default.RateReview),
        NavItem("form", "Form", Icons.AutoMirrored.Filled.Send),
        NavItem("profile", "Profile", Icons.Default.Person)
    )
    val adminNav = listOf(
        NavItem("dashboard", "Dashboard", Icons.Default.Dashboard),
        NavItem("requests", "Requests", Icons.Default.Inbox),
        NavItem("complaints", "Help", Icons.Default.HelpCenter),
        NavItem("section", "Add", Icons.Default.AddBox),
        NavItem("profile", "Profile", Icons.Default.Person)
    )

    when (screen) {
        "splash" -> SplashScreen(onDone = { screen = "auth" })
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
                "student" -> mapOf("home" to "Home", "scorecard" to "Scorecard", "resources" to "Resources", "notifications" to "Notifications", "profile" to "Profile", "findGuide" to "Browse Faculty", "browse" to "Browse Faculty", "submit" to "Submit Project", "status" to "Submission Status", "review" to "Faculty Review", "progress" to "Progress", "eval" to "Evaluation")
                "faculty" -> mapOf("home" to "Dashboard", "registrations" to "Registrations", "review" to "Review Hub", "form" to "Create Form", "profile" to "Profile")
                else -> mapOf("dashboard" to "Admin Dashboard", "requests" to "Requests", "section" to "Add Section", "profile" to "Profile", "manageusers" to "Manage Users", "timeline" to "Timeline Config", "reports" to "System Reports", "lock" to "Lock Submissions", "complaints" to "Help & Complaints", "notify" to "Send Notification")
            }

            Scaffold(
                topBar = {
                    PMTopBar(
                        title = title[activeTab] ?: "ProManage",
                        role = role,
                        onProfileClick = { activeTab = if (role == "admin") "profile" else "profile" },
                        onNotificationsClick = { showNotifications = !showNotifications }
                    )
                },
                bottomBar = {
                    PMBottomNav(items = navItems, activeRoute = activeTab, onSelect = { activeTab = it })
                },
                floatingActionButton = {
                    if (role != "admin") {
                        FloatingActionButton(
                            onClick = { showChat = !showChat },
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ) {
                            Icon(androidx.compose.material.icons.Icons.AutoMirrored.Filled.Chat, contentDescription = "Chat")
                        }
                    }
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                    when (role) {
                        "student" -> when (activeTab) {
                            "home" -> StudentHomeFull(prefs = prefs, mode = projectMode, onModeChange = { projectMode = it; prefs.saveProjectMode(it) }, onNavigate = { activeTab = it })
                            "scorecard" -> StudentScorecard()
                            "resources" -> StudentResources()
                            "notifications" -> StudentNotificationsPage()
                            "profile" -> StudentProfile(prefs = prefs, themeMode = themeMode, onThemeChange = onThemeChange, onLogout = { prefs.clear(); role = "student"; projectMode = ""; activeTab = "home"; screen = "auth" })
                            "findGuide", "browse" -> StudentFindGuide(onBack = { activeTab = "home" })
                            "submit" -> ResearchSubmission(onBack = { activeTab = "home" }, onSubmit = { activeTab = "status" })
                            "status" -> ResearchSubmissionStatus(onBack = { activeTab = "home" }, onViewProgress = { activeTab = "progress" })
                            "review" -> ResearchFacultyReview(onBack = { activeTab = "home" }, onProceed = { activeTab = "progress" })
                            "progress" -> ResearchProgressTracking(onBack = { activeTab = "home" }, onViewEval = { activeTab = "eval" })
                            "eval" -> ResearchEvaluation(onBack = { activeTab = "progress" })
                            else -> StudentHomeFull(prefs = prefs, mode = projectMode, onModeChange = { projectMode = it; prefs.saveProjectMode(it) }, onNavigate = { activeTab = it })
                        }
                        "faculty" -> when (activeTab) {
                            "home" -> FacultyHome()
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
                                }
                            )
                            else -> FacultyHome()
                        }
                        "admin" -> when (activeTab) {
                            "dashboard" -> AdminDashboard(setAdminTab = { activeTab = it })
                            "requests" -> AdminRequests()
                            "section" -> AdminAddSection()
                            "manageusers" -> AdminManageUsers()
                            "timeline" -> AdminTimeline()
                            "reports" -> AdminReports()
                            "lock" -> AdminLockSubmissions()
                            "complaints" -> AdminComplaints()
                            "notify" -> AdminNotificationForm()
                            "profile" -> AdminProfile(prefs = prefs, themeMode = themeMode, onThemeChange = onThemeChange, setAdminTab = { activeTab = it }, onLogout = { prefs.clear(); role = "student"; projectMode = ""; activeTab = "home"; screen = "auth" })
                            else -> AdminDashboard(setAdminTab = { activeTab = it })
                        }
                    }

                    if (showNotifications) {
                        Box(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), contentAlignment = androidx.compose.ui.Alignment.TopCenter) {
                            NotificationPanel(onClose = { showNotifications = false })
                        }
                    }

                    if (showChat) {
                        Box(modifier = Modifier.fillMaxSize().padding(bottom = 80.dp), contentAlignment = androidx.compose.ui.Alignment.BottomEnd) {
                            ChatPanel(onClose = { showChat = false })
                        }
                    }
                }
            }
        }
    }
}