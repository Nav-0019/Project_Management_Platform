package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.*
import com.example.myapplication.ui.theme.*

// ─── ADMIN DASHBOARD ─────────────────────────────────────────────────────────
@Composable
fun AdminDashboard(setAdminTab: (String) -> Unit = {}) {
    val stats = listOf(
        Triple("Total Students", "248", MaterialTheme.colorScheme.primary),
        Triple("Faculty", "32", Accent),
        Triple("Projects", "61", Success),
        Triple("Reviews", "12", Warning)
    )
    val quickActions = listOf(
        Triple("Manage Users", Icons.Default.Person, MaterialTheme.colorScheme.primary),
        Triple("Timeline Config", Icons.Default.Schedule, Accent),
        Triple("System Reports", Icons.Default.BarChart, Success),
        Triple("Lock Submissions", Icons.Default.Lock, Danger),
        Triple("Send Notification", Icons.Default.Notifications, Warning),
        Triple("Complaints & Help", Icons.Default.HelpCenter, MaterialTheme.colorScheme.primary)
    )
    val quickRoutes = listOf("manageusers", "timeline", "reports", "lock", "notify", "complaints")

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Column(modifier = Modifier.padding(bottom = 20.dp)) {
            Text("Admin Dashboard", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Text("Academic Year 2025–26", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        // Stats grid
        Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(bottom = 20.dp)) {
            for (row in stats.chunked(2)) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    for ((label, value, color) in row) {
                        PMCard(modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
                                Text(value, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = color)
                            }
                        }
                    }
                }
            }
        }
        Text("Quick Actions", fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(bottom = 20.dp)) {
            for ((rowIdx, row) in quickActions.chunked(2).withIndex()) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    for ((colIdx, action) in row.withIndex()) {
                        val (label, icon, color) = action
                        val route = quickRoutes.getOrElse(rowIdx * 2 + colIdx) { "dashboard" }
                        PMCard(modifier = Modifier.weight(1f), onClick = { setAdminTab(route) }) {
                            Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(38.dp).clip(RoundedCornerShape(12.dp)).background(color.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                                    Icon(icon, null, tint = color, modifier = Modifier.size(18.dp))
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f), maxLines = 2, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                            }
                        }
                    }
                }
            }
        }
        Text("Submission Progress", fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
        PMCard {
            Column(modifier = Modifier.padding(16.dp)) {
                for ((i, item) in listOf(Triple("Review 1 Submissions", 82, MaterialTheme.colorScheme.primary), Triple("Review 2 Submissions", 60, Accent), Triple("Final Submissions", 25, Success)).withIndex()) {
                    val (label, pct, color) = item
                    Column(modifier = Modifier.padding(bottom = if (i < 2) 14.dp else 0.dp)) {
                        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(label, fontSize = 13.sp)
                            Text("$pct%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
                        }
                        PMProgressBar(pct, color)
                    }
                }
            }
        }
    }
}

// ─── ADMIN REQUESTS ───────────────────────────────────────────────────────────
@Composable
fun AdminRequests() {
    data class Request(val id: Int, val type: String, val from: String, val team: String, val project: String, val domain: String, var status: String)
    var requests by remember {
        mutableStateOf(listOf(
            Request(1, "Student Guide Request", "Vijay Shah", "6BCA_01", "AI-Based Medical Diagnosis", "Data Science", "pending"),
            Request(2, "Faculty Assignment", "Dr. Lisa Wong", "4MCA_08", "E-commerce Reco", "Web Dev", "pending"),
            Request(3, "Student Guide Request", "Priya Agarwal", "6BCA_02", "Healthcare Analytics", "AI/ML", "approved")
        ))
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Text("Requests", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(bottom = 16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            for (r in requests) {
                PMCard {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(r.type.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 0.5.sp)
                                Text(r.from, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                                Text("${r.team} · ${r.project}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                            }
                            PMBadge(r.status, color = when (r.status) { "approved" -> Success; "rejected" -> Danger; else -> Warning })
                        }
                        PMBadge(r.domain, color = Accent)
                        if (r.status == "pending") {
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                PMButton("Accept", onClick = {
                                    requests = requests.map { if (it.id == r.id) it.copy(status = "approved") else it }
                                }, variant = "success", modifier = Modifier.weight(1f))
                                PMButton("Reject", onClick = {
                                    requests = requests.map { if (it.id == r.id) it.copy(status = "rejected") else it }
                                }, variant = "danger", modifier = Modifier.weight(1f))
                            }
                        } else {
                            Spacer(modifier = Modifier.height(10.dp))
                            val isApproved = r.status == "approved"
                            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background((if (isApproved) Success else Danger).copy(alpha = 0.1f)).padding(10.dp)) {
                                Text(if (isApproved) "✓ Approved — Team notified" else "✗ Rejected — Team notified", color = if (isApproved) Success else Danger, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ─── ADD SECTION ─────────────────────────────────────────────────────────────
@Composable
fun AdminAddSection() {
    var active by remember { mutableStateOf("student") }
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var dept by remember { mutableStateOf("") }
    var teamName by remember { mutableStateOf("") }
    var maxMembers by remember { mutableStateOf("4") }
    var saved by remember { mutableStateOf(false) }

    val sections = listOf(
        Triple("student", "Student", Icons.Default.Person),
        Triple("faculty", "Faculty", Icons.Default.Star),
        Triple("team", "Team", Icons.Default.Groups),
        Triple("mentor", "Mentor", Icons.Default.School)
    )

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Text("Add Section", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(bottom = 16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            for ((key, label, icon) in sections) {
                val isActive = active == key
                Column(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(10.dp))
                        .background(if (isActive) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { active = key; saved = false; fullName = ""; email = ""; userId = "" }.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(icon, null, tint = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.height(4.dp))
                    Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
        PMCard(modifier = Modifier.padding(bottom = 16.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Create ${sections.find { it.first == active }?.second ?: ""}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                when (active) {
                    "student", "faculty", "mentor" -> {
                        Column { Text("Full Name *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("Enter full name", value = fullName, onValueChange = { fullName = it }) }
                        Column { Text("Email Address *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("user@university.edu", value = email, onValueChange = { email = it }) }
                        Column { Text(if (active == "student") "Roll Number / ID *" else "Employee ID *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput(if (active == "student") "e.g. 6BCA2201" else "e.g. FAC001", value = userId, onValueChange = { userId = it }) }
                        Column { Text("Department *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("e.g. Computer Science", value = dept, onValueChange = { dept = it }) }
                        if (active == "student") { Column { Text("Year / Semester", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("e.g. 3rd Year, 6th Sem", value = "", onValueChange = {}) } }
                        if (active == "faculty" || active == "mentor") { Column { Text("Designation", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("e.g. Assistant Professor", value = "", onValueChange = {}) } }
                    }
                    "team" -> {
                        Column { Text("Team Name / ID *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("e.g. 6BCA_01", value = teamName, onValueChange = { teamName = it }) }
                        Column { Text("Course / Department", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("e.g. BCA / Computer Science", value = dept, onValueChange = { dept = it }) }
                        Column { Text("Max Members", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("4", value = maxMembers, onValueChange = { maxMembers = it }) }
                        Column { Text("Assign Mentor", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("Faculty email or ID", value = "", onValueChange = {}) }
                    }
                }
            }
        }
        if (saved) {
            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(Success.copy(alpha = 0.1f)).padding(12.dp).padding(bottom = 12.dp)) {
                Text("✓ ${sections.find { it.first == active }?.second} created successfully!", color = Success, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            }
        }
        PMButton("Create & Save", onClick = { saved = true }, modifier = Modifier.fillMaxWidth())
    }
}

// ─── PROFILE ─────────────────────────────────────────────────────────────────
@Composable
fun AdminProfile(prefs: com.example.myapplication.data.PreferencesManager, themeMode: String, onThemeChange: (String) -> Unit, onLogout: () -> Unit, setAdminTab: (String) -> Unit = {}) {
    val userName = prefs.getActiveName()
    val userEmail = prefs.getActiveEmail()
    var editMode by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf(userName) }
    var editOrg by remember { mutableStateOf("State University of Technology") }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            PMAvatar(if (editMode) editName else userName, size = 80, color = Warning)
            Spacer(modifier = Modifier.height(12.dp))
            if (!editMode) {
                Text(userName, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                Text(userEmail, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Platform Administrator", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            } else {
                PMInput("Display Name", value = editName, onValueChange = { editName = it })
                Spacer(Modifier.height(8.dp))
                PMInput("Organization", value = editOrg, onValueChange = { editOrg = it })
            }
            Spacer(modifier = Modifier.height(8.dp))
            PMBadge("Admin", color = Warning)
        }
        PMCard(modifier = Modifier.padding(bottom = 12.dp)) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Quick Navigation", fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
                for ((i, item) in listOf(
                    Triple("Manage Users", Icons.Default.Person, "manageusers"),
                    Triple("System Reports", Icons.Default.BarChart, "reports"),
                    Triple("Timeline Config", Icons.Default.Settings, "timeline"),
                    Triple("Lock Submissions", Icons.Default.Lock, "lock")
                ).withIndex()) {
                    val (label, icon, route) = item
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable { setAdminTab(route) }.padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(label, fontSize = 13.sp, modifier = Modifier.weight(1f))
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
                    }
                    if (i < 3) Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(MaterialTheme.colorScheme.outline))
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        com.example.myapplication.ui.components.PMThemeToggle(themeMode = themeMode, onThemeChange = onThemeChange)
        Spacer(modifier = Modifier.height(8.dp))
        PMButton(
            if (editMode) "Save Profile" else "Edit Profile",
            onClick = { editMode = !editMode },
            variant = if (editMode) "success" else "outline",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        PMButton("Logout", onClick = onLogout, variant = "danger", modifier = Modifier.fillMaxWidth())
    }
}
