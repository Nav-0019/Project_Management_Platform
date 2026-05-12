package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
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

@Composable
fun AdminManageUsers() {
    var search by remember { mutableStateOf("") }
    var activeTab by remember { mutableStateOf("students") }
    data class User(val name: String, val id: String, val dept: String, val status: String)
    val students = remember { mutableStateOf(emptyList<User>()) }
    val faculty = remember { mutableStateOf(emptyList<User>()) }
    val shown = (if (activeTab == "students") students.value else faculty.value).filter { it.name.contains(search, true) || it.id.contains(search, true) }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Text("Manage Users", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(bottom = 16.dp))
        PMInput("Search by name or ID...", value = search, onValueChange = { search = it }, icon = Icons.Default.Search)
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 16.dp)) {
            for (t in listOf("students", "faculty")) {
                val active = activeTab == t
                Box(modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant).clickable { activeTab = t }.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text(t.replaceFirstChar { it.uppercase() }, color = if (active) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                }
            }
        }
        if (shown.isEmpty()) {
            PMEmptyState(
                emoji = if (activeTab == "students") "🎓" else "👨‍🏫",
                title = "No ${if (activeTab == "students") "students" else "faculty"} found",
                subtitle = "Users will appear here once added to the platform.",
                ctaLabel = "+ Add User",
                onCta = {}
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                for (u in shown) {
                    PMCard {
                        Row(modifier = Modifier.padding(14.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            PMAvatar(u.name, size = 40, color = if (activeTab == "students") MaterialTheme.colorScheme.primary else Accent)
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text(u.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text("${u.id} · ${u.dept}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            PMBadge(u.status, color = if (u.status == "Active") Success else MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(Modifier.width(8.dp))
                            Icon(Icons.Default.MoreVert, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        PMButton("+ Add New User", onClick = {}, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun AdminTimeline() {
    data class Phase(val name: String, val start: String, val end: String, var locked: Boolean)
    var phases by remember { mutableStateOf(listOf(
        Phase("Idea Submission", "1 Apr 2026", "15 Apr 2026", true),
        Phase("Review 1", "20 Apr 2026", "5 May 2026", true),
        Phase("Review 2", "10 May 2026", "25 May 2026", false),
        Phase("Final Submission", "1 Jun 2026", "15 Jun 2026", false),
        Phase("Evaluation", "20 Jun 2026", "30 Jun 2026", false)
    )) }
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Text("Timeline Configuration", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(bottom = 4.dp))
        Text("Academic Year 2025–26", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 20.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            for ((i, phase) in phases.withIndex()) {
                PMCard {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Box(modifier = Modifier.size(28.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.primary.copy(0.15f)), contentAlignment = Alignment.Center) { Text("${i+1}", fontWeight = FontWeight.ExtraBold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary) }
                                Text(phase.name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Switch(checked = !phase.locked, onCheckedChange = { phases = phases.toMutableList().also { list -> list[i] = list[i].copy(locked = !it) } })
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            PMBadge("Start: ${phase.start}", color = MaterialTheme.colorScheme.primary)
                            PMBadge("End: ${phase.end}", color = Warning)
                            PMBadge(if (phase.locked) "Locked" else "Open", color = if (phase.locked) Danger else Success)
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        PMButton("Save Timeline", onClick = {}, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun AdminReports() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Text("System Reports", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(bottom = 20.dp))
        for ((label, pct, color) in listOf(Triple("Review 1 Submissions", 0, MaterialTheme.colorScheme.primary), Triple("Review 2 Submissions", 0, Accent), Triple("Final Submissions", 0, Success))) {
            PMCard(modifier = Modifier.padding(bottom = 12.dp)) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp), horizontalArrangement = Arrangement.SpaceBetween) { Text(label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold); Text("$pct%", color = color, fontWeight = FontWeight.Bold) }
                    PMProgressBar(pct, color)
                }
            }
        }
        Text("Summary Statistics", fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            for (row in listOf(
                listOf(Triple("Total Students", "—", MaterialTheme.colorScheme.primary), Triple("Faculty", "—", Accent)),
                listOf(Triple("Active Projects", "—", Success), Triple("Pending Reviews", "—", Warning)),
                listOf(Triple("Open Complaints", "—", Danger), Triple("Resolved", "—", Success))
            )) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    for ((label, value, color) in row) {
                        PMCard(modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(value, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = color)
                            }
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        PMButton("Export Report (PDF)", onClick = {}, modifier = Modifier.fillMaxWidth(), variant = "outline")
    }
}

@Composable
fun AdminLockSubmissions() {
    data class SubmitWindow(val name: String, var isOpen: Boolean)
    var windows by remember { mutableStateOf(listOf(SubmitWindow("Review 1 Submission", true), SubmitWindow("Review 2 Submission", false), SubmitWindow("Final Submission", false), SubmitWindow("Project Registration", true), SubmitWindow("Guide Request", true))) }
    var globalLock by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Text("Lock Submissions", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(bottom = 4.dp))
        Text("Control submission windows across the platform", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 16.dp))
        PMCard(modifier = Modifier.padding(bottom = 16.dp).border(1.5.dp, Danger.copy(0.4f), RoundedCornerShape(16.dp))) {
            Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(10.dp)).background(Danger.copy(0.15f)), contentAlignment = Alignment.Center) { Icon(Icons.Default.Lock, null, tint = Danger, modifier = Modifier.size(20.dp)) }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) { Text("Global Lock", fontWeight = FontWeight.Bold, fontSize = 14.sp); Text("Lock ALL submission windows instantly", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                Switch(checked = globalLock, onCheckedChange = { v -> globalLock = v; if (v) windows = windows.map { it.copy(isOpen = false) } })
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            for ((i, w) in windows.withIndex()) {
                PMCard {
                    Row(modifier = Modifier.padding(14.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Icon(if (w.isOpen) Icons.Default.LockOpen else Icons.Default.Lock, null, tint = if (w.isOpen) Success else Danger, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) { Text(w.name, fontWeight = FontWeight.SemiBold, fontSize = 13.sp); Text(if (w.isOpen) "Accepting submissions" else "Closed", fontSize = 11.sp, color = if (w.isOpen) Success else Danger) }
                        Switch(checked = w.isOpen, onCheckedChange = { v -> windows = windows.toMutableList().also { it[i] = it[i].copy(isOpen = v) } })
                    }
                }
            }
        }
    }
}

@Composable
fun AdminComplaints() {
    data class Complaint(val id: Int, val user: String, val role: String, val subject: String, val msg: String, val time: String, var status: String)
    var complaints by remember { mutableStateOf(emptyList<Complaint>()) }
    var newSubject by remember { mutableStateOf("") }
    var newMsg by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Text("Complaints & Help Desk", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(bottom = 4.dp))
        Text("User-reported issues and app crashes", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            for ((l, v, c) in listOf(Triple("Open", complaints.count { it.status == "open" }.toString(), Danger), Triple("In Progress", complaints.count { it.status == "in-progress" }.toString(), Warning), Triple("Resolved", complaints.count { it.status == "resolved" }.toString(), Success))) {
                PMCard(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(l, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(v, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = c)
                    }
                }
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            for (c in complaints) {
                val statusColor = when (c.status) { "resolved" -> Success; "in-progress" -> Warning; else -> Danger }
                PMCard {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                            Column(Modifier.weight(1f)) { Text(c.subject, fontWeight = FontWeight.Bold, fontSize = 13.sp); Text("${c.user} · ${c.role} · ${c.time}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                            PMBadge(c.status, color = statusColor)
                        }
                        Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.surfaceVariant).padding(10.dp)) { Text(c.msg, fontSize = 12.sp) }
                        if (c.status != "resolved") {
                            Spacer(Modifier.height(10.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                PMButton("Mark Resolved", onClick = { complaints = complaints.map { if (it.id == c.id) it.copy(status = "resolved") else it } }, variant = "success", modifier = Modifier.weight(1f))
                                if (c.status == "open") PMButton("In Progress", onClick = { complaints = complaints.map { if (it.id == c.id) it.copy(status = "in-progress") else it } }, variant = "outline", modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminNotificationForm() {
    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var targetRole by remember { mutableStateOf("All Users") }
    var sent by remember { mutableStateOf(false) }
    val roles = listOf("All Users", "Students Only", "Faculty Only", "Final Year Students", "Specific Team")
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Text("Send Notification / Alert", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(bottom = 20.dp))
        if (!sent) {
            PMCard {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Column { Text("Notification Title *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("e.g. Submission Deadline Reminder", value = title, onValueChange = { title = it }) }
                    Column {
                        Text("Target Audience", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            for (row in roles.chunked(2)) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    for (r in row) {
                                        Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(8.dp)).background(if (targetRole == r) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant).clickable { targetRole = r }.padding(10.dp), contentAlignment = Alignment.Center) {
                                            Text(r, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = if (targetRole == r) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    }
                                    if (row.size == 1) Spacer(Modifier.weight(1f))
                                }
                            }
                        }
                    }
                    Column { Text("Message *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("Write your announcement or alert...", value = message, onValueChange = { message = it }, singleLine = false) }
                    PMButton("Send Notification", onClick = { if (title.isNotBlank() && message.isNotBlank()) sent = true }, modifier = Modifier.fillMaxWidth())
                }
            }
        } else {
            PMCard { Column(modifier = Modifier.padding(24.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("✅", fontSize = 40.sp); Spacer(Modifier.height(10.dp))
                Text("Notification Sent!", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Success)
                Text("Delivered to: $targetRole", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 6.dp))
                Spacer(Modifier.height(14.dp))
                PMButton("Send Another", onClick = { sent = false; title = ""; message = "" })
            } }
        }
    }
}
