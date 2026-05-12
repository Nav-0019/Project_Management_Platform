package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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

// ─── FACULTY HOME ─────────────────────────────────────────────────────────────
@Composable
fun FacultyHome(prefs: com.example.myapplication.data.PreferencesManager) {
    val userName = prefs.getActiveName().takeIf { it.isNotBlank() } ?: "Dr. Anand Kumar"
    val dept = prefs.getActiveDept().takeIf { it.isNotBlank() } ?: "Computer Applications"
    
    data class TeamInfo(val id: String, val name: String, val project: String, val progress: Int, val members: List<String>, val domain: String, val deadline: String)
    val teams = emptyList<TeamInfo>()
    var selectedTeam by remember { mutableStateOf<TeamInfo?>(null) }

    if (selectedTeam != null) {
        val t = selectedTeam!!
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 20.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, modifier = Modifier.clickable { selectedTeam = null }.padding(end = 12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                Column { Text(t.name, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold); Text(t.project, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) }
            }
            PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) { Text("Project Progress", fontWeight = FontWeight.Bold); Text("${t.progress}%", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp) }
                    PMProgressBar(t.progress, MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { PMBadge(t.domain, color = Accent); PMBadge("Due: ${t.deadline}", color = Warning) }
                }
            }
            Text("Team Members", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(bottom = 8.dp))
            PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    for ((i, m) in t.members.withIndex()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            PMAvatar(m, size = 34, color = if(i==0) MaterialTheme.colorScheme.primary else Accent)
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text(m, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                Text(if(i==0) "Leader" else "Member", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
            Text("Project Goals", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(bottom = 8.dp))
            PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
                Column(modifier = Modifier.padding(14.dp)) {
                    for (g in listOf("Build an AI model for early disease detection", "Integrate with hospital management systems", "Achieve 90%+ accuracy on test dataset")) {
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Icon(Icons.Default.CheckCircle, null, tint = Success, modifier = Modifier.size(14.dp).padding(end=4.dp))
                            Spacer(Modifier.width(6.dp))
                            Text(g, fontSize = 13.sp)
                        }
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PMButton("💬 Team Chat", onClick = {}, modifier = Modifier.weight(1f), variant = "outline")
                PMButton("📋 Review Project", onClick = {}, modifier = Modifier.weight(1f))
            }
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Column(modifier = Modifier.padding(bottom = 20.dp)) {
            Text("Good Morning,", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("$userName 👨‍🏫", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Text("Faculty, Dept. of $dept", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)) {
            for (s in listOf(Triple("Active", "5", MaterialTheme.colorScheme.primary), Triple("Pending", "2", Warning), Triple("Approved", "8", Success))) {
                PMCard(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(s.first, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(s.second, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = s.third)
                    }
                }
            }
        }
        Text("Your Teams", fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            if (teams.isEmpty()) {
                PMEmptyState("👥", "No Teams Assigned", "You are not mentoring any teams currently.")
            } else {
                for (t in teams) {
                    PMCard(onClick = { selectedTeam = t }) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                                Column { Text(t.name, fontWeight = FontWeight.Bold, fontSize = 14.sp); Text(t.project, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    PMBadge(if (t.progress > 50) "Active" else "Review Pending", color = if (t.progress > 50) Success else Warning)
                                    Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
                                }
                            }
                            PMProgressBar(t.progress, MaterialTheme.colorScheme.primary)
                            Row(modifier = Modifier.fillMaxWidth().padding(top = 6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("${t.members.size} members", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${t.progress}% complete", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ─── REGISTRATIONS ───────────────────────────────────────────────────────────
@Composable
fun FacultyRegistrations() {
    var showAddProject by remember { mutableStateOf(false) }
    var expandedId by remember { mutableStateOf<Int?>(null) }
    var actionMap by remember { mutableStateOf(mapOf<Int, String>()) }

    data class PendingReq(
        val id: Int,
        val leader: String,
        val project: String,
        val domain: String,
        val members: List<String>,
        val goals: List<String>,
        val deadline: String
    )

    val pending = emptyList<PendingReq>()

    if (showAddProject) {
        FacultyAddProjectForm(onBack = { showAddProject = false })
        return
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Registrations", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
            PMButton("+ Add Project", onClick = { showAddProject = true }, variant = "outline")
        }

        Text("PENDING REQUESTS", fontSize = 11.sp, fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 1.sp,
            modifier = Modifier.padding(bottom = 10.dp))

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            if (pending.isEmpty()) {
                PMEmptyState("📬", "No Pending Requests", "You have no new team registration requests.")
            } else {
                for (req in pending) {
                    val action = actionMap[req.id]
                    val isExpanded = expandedId == req.id
                    PMCard {
                        Column(modifier = Modifier.padding(14.dp)) {
                            // Header row
                            Row(
                                modifier = Modifier.fillMaxWidth().clickable {
                                    expandedId = if (isExpanded) null else req.id
                                },
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    PMAvatar(req.leader, size = 38, color = MaterialTheme.colorScheme.primary)
                                    Column {
                                        Text(req.project, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text("${req.leader} · ${req.domain}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                                if (action != null) {
                                    PMBadge(if (action == "accepted") "Accepted" else "Rejected",
                                        color = if (action == "accepted") Success else Danger)
                                } else {
                                    PMBadge("Pending", color = Warning)
                                }
                            }

                            // Expanded details
                            if (isExpanded) {
                                Spacer(Modifier.height(12.dp))
                                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(MaterialTheme.colorScheme.outline))
                                Spacer(Modifier.height(10.dp))

                                // Project info row
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 10.dp)) {
                                    PMBadge(req.domain, color = Accent)
                                    PMBadge("Due: ${req.deadline}", color = Warning)
                                }

                                // Members
                                Text("Team Members (${req.members.size})", fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
                                Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(bottom = 10.dp)) {
                                    req.members.forEachIndexed { i, m ->
                                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            PMAvatar(m, size = 28, color = if (i == 0) MaterialTheme.colorScheme.primary else Accent)
                                            Text(m, fontSize = 12.sp)
                                            if (i == 0) PMBadge("Leader", color = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                }

                                // Goals
                                Text("Project Goals", fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(bottom = 12.dp)) {
                                    req.goals.forEach { g ->
                                        Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                            Icon(Icons.Default.CheckCircle, null, tint = Success, modifier = Modifier.size(14.dp).padding(top = 2.dp))
                                            Text(g, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    }
                                }

                                if (action == null) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        PMButton("✓ Accept", onClick = { actionMap = actionMap + (req.id to "accepted") },
                                            variant = "success", modifier = Modifier.weight(1f))
                                        PMButton("✗ Reject", onClick = { actionMap = actionMap + (req.id to "rejected") },
                                            variant = "danger", modifier = Modifier.weight(1f))
                                    }
                                } else {
                                    Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
                                        .background((if (action == "accepted") Success else Danger).copy(0.1f)).padding(10.dp)) {
                                        Text(
                                            if (action == "accepted") "✓ Accepted — Student notified" else "✗ Rejected — Student notified",
                                            color = if (action == "accepted") Success else Danger,
                                            fontSize = 12.sp, fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            } else if (action == null) {
                                Spacer(Modifier.height(10.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    PMButton("✓ Accept", onClick = { actionMap = actionMap + (req.id to "accepted") },
                                        variant = "success", modifier = Modifier.weight(1f))
                                    PMButton("✗ Reject", onClick = { actionMap = actionMap + (req.id to "rejected") },
                                        variant = "danger", modifier = Modifier.weight(1f))
                                }
                            }
                            Spacer(Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth().clickable { expandedId = if (isExpanded) null else req.id },
                                horizontalArrangement = Arrangement.Center) {
                                Text(if (isExpanded) "▲ Hide Details" else "▼ View Details",
                                    fontSize = 11.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ─── FACULTY ADD PROJECT FORM ─────────────────────────────────────────────────
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FacultyAddProjectForm(onBack: () -> Unit) {
    var projectName by remember { mutableStateOf("") }
    var domain by remember { mutableStateOf("") }
    var goals by remember { mutableStateOf("") }
    var timeline by remember { mutableStateOf("") }
    var maxMarks by remember { mutableStateOf("") }
    var credits by remember { mutableStateOf("") }
    var studentEmails by remember { mutableStateOf("") }
    var studentList by remember { mutableStateOf(listOf<String>()) }
    var emailInput by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(false) }
    val domains = listOf("Data Science", "AI/ML", "Web Dev", "Cyber Security", "IoT")

    if (submitted) {
        Column(modifier = Modifier.fillMaxSize().padding(32.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text("🎉", fontSize = 48.sp)
            Spacer(Modifier.height(12.dp))
            Text("Project Added!", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Success)
            Text(projectName, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp))
            Text("${studentList.size} student(s) invited", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp, bottom = 20.dp))
            PMButton("Back to Registrations", onClick = onBack, modifier = Modifier.fillMaxWidth())
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 20.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null,
                modifier = Modifier.clickable { onBack() }.padding(end = 12.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Column {
                Text("Add New Project", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                Text("Create a project and invite students", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // Project Details Card
        Text("PROJECT DETAILS", fontSize = 11.sp, fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 1.sp, modifier = Modifier.padding(bottom = 8.dp))
        PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Column { Text("Project Title *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("e.g. AI Medical Diagnosis", value = projectName, onValueChange = { projectName = it }) }
                Column {
                    Text("Domain *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
                    PMInput("Select domain", value = domain, onValueChange = { domain = it })
                    Spacer(Modifier.height(6.dp))
                    androidx.compose.foundation.layout.FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        domains.forEach { d ->
                            Box(modifier = Modifier.padding(bottom = 6.dp).clip(RoundedCornerShape(16.dp))
                                .background(if (domain == d) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                                .clickable { domain = d }.padding(horizontal = 10.dp, vertical = 5.dp)) {
                                Text(d, fontSize = 11.sp, color = if (domain == d) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
                Column { Text("Goals & Objectives *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("List project goals...", value = goals, onValueChange = { goals = it }, singleLine = false) }
            }
        }

        // Marks & Credit Card
        Text("MARKS & CREDITS", fontSize = 11.sp, fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 1.sp, modifier = Modifier.padding(bottom = 8.dp))
        PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Column(Modifier.weight(1f)) { Text("Max Marks", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("e.g. 100", value = maxMarks, onValueChange = { maxMarks = it }) }
                    Column(Modifier.weight(1f)) { Text("Credits", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("e.g. 4", value = credits, onValueChange = { credits = it }) }
                }
                Column { Text("Timeline / Duration", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("e.g. Apr 2026 – Jun 2026", value = timeline, onValueChange = { timeline = it }) }
            }
        }

        // Invite Students Card
        Text("INVITE STUDENTS", fontSize = 11.sp, fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 1.sp, modifier = Modifier.padding(bottom = 8.dp))
        PMCard(modifier = Modifier.padding(bottom = 20.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Add students by email or roll number", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PMInput("student@university.edu or roll no.", value = emailInput, onValueChange = { emailInput = it }, modifier = Modifier.weight(1f))
                    PMButton("+", onClick = {
                        if (emailInput.isNotBlank() && emailInput !in studentList) {
                            studentList = studentList + emailInput; emailInput = ""
                        }
                    })
                }
                if (studentList.isNotEmpty()) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        studentList.forEach { s ->
                            PMCard {
                                Row(modifier = Modifier.padding(10.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                    PMAvatar(s, size = 30, color = Accent)
                                    Spacer(Modifier.width(10.dp))
                                    Text(s, fontSize = 13.sp, modifier = Modifier.weight(1f))
                                    Icon(Icons.Default.Close, null, tint = Danger, modifier = Modifier.size(16.dp).clickable { studentList = studentList - s })
                                }
                            }
                        }
                    }
                }
            }
        }

        PMButton("Create & Assign Project", onClick = { if (projectName.isNotBlank()) submitted = true }, modifier = Modifier.fillMaxWidth())
    }
}

// ─── REVIEW HUB ──────────────────────────────────────────────────────────────
@Composable
fun FacultyReviewHub() {
    var tab by remember { mutableStateOf("all") }
    var searchQuery by remember { mutableStateOf("") }
    var selectedId by remember { mutableStateOf<Int?>(null) }
    var marks by remember { mutableStateOf("") }
    var githubLink by remember { mutableStateOf("") }
    var remarks by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(setOf<Int>()) }

    val submissions = emptyList<Map<String, Any>>()

    if (selectedId != null) {
        val sub = submissions.first { it["id"] == selectedId }
        val sid = selectedId!!
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 20.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, modifier = Modifier.clickable { selectedId = null }.padding(end = 12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Review Submission", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    PMAvatar(sub["student"] as String, size = 48, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(sub["student"] as String, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(sub["project"] as String, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(4.dp))
                        PMBadge(sub["domain"] as String, color = Accent)
                    }
                }
            }
            PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Project File", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.padding(bottom = 10.dp))
                    var isDownloaded by remember { mutableStateOf(false) }
                    Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(if(isDownloaded) Success.copy(0.15f) else MaterialTheme.colorScheme.surfaceVariant).clickable { isDownloaded = true }.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Description, null, tint = Danger, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(sub["file"] as String, modifier = Modifier.weight(1f), fontSize = 13.sp)
                        Icon(if(isDownloaded) Icons.Default.CheckCircle else Icons.Default.Download, null, tint = if(isDownloaded) Success else MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Due: ${sub["date"]}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            // GitHub & Links card
            PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Project Links", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.surfaceVariant).padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Code, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp)); Spacer(Modifier.width(8.dp))
                        Text("github.com/team/ai-medical-diagnosis", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(1f))
                        Icon(Icons.Default.OpenInNew, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                    }
                    Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.surfaceVariant).padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Link, null, tint = Accent, modifier = Modifier.size(16.dp)); Spacer(Modifier.width(8.dp))
                        Text("Live Demo: ai-medical.vercel.app", fontSize = 12.sp, color = Accent, modifier = Modifier.weight(1f))
                        Icon(Icons.Default.OpenInNew, null, tint = Accent, modifier = Modifier.size(14.dp))
                    }
                }
            }
            if (sid !in submitted) {
                PMCard {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Add Review", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Column {
                            Text("Marks (out of 25)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
                            PMInput("Enter marks", value = marks, onValueChange = { marks = it })
                        }
                        Column {
                            Text("GitHub / Repo Link", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
                            PMInput("github.com/...", value = githubLink, onValueChange = { githubLink = it })
                        }
                        Column {
                            Text("Remarks & Feedback", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
                            PMInput("Add your feedback...", value = remarks, onValueChange = { remarks = it }, singleLine = false)
                        }
                        PMButton("Submit Review & Marks", onClick = { submitted = submitted + sid }, modifier = Modifier.fillMaxWidth())
                    }
                }
            } else {
                PMCard {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, null, tint = Success, modifier = Modifier.size(22.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Review Submitted Successfully!", fontWeight = FontWeight.Bold, color = Success)
                    }
                }
            }
        }
        return
    }

    val tabs = listOf("all", "new", "reviewed", "approved")
    val filtered = submissions.filter { s ->
        (tab == "all" || s["status"] == tab) &&
        (searchQuery.isBlank() || (s["student"] as String).contains(searchQuery, true) || (s["project"] as String).contains(searchQuery, true))
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Text("Review Hub", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(bottom = 14.dp))
        PMInput("Search submissions...", value = searchQuery, onValueChange = { searchQuery = it }, icon = Icons.Default.Search)
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.horizontalScroll(rememberScrollState()).padding(bottom = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            for (t in tabs) {
                val isActive = tab == t
                Box(modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant).clickable { tab = t }.padding(horizontal = 14.dp, vertical = 8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(t.replaceFirstChar { it.uppercase() }, color = if (isActive) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                        if (t == "new") {
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(modifier = Modifier.clip(RoundedCornerShape(10.dp)).background(Danger).padding(horizontal = 5.dp, vertical = 1.dp)) {
                                Text("2", color = MaterialTheme.colorScheme.onPrimary, fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            if (filtered.isEmpty()) {
                PMEmptyState("📂", "No Submissions", "No submissions match the selected filter.")
            } else {
                for (s in filtered) {
                    val status = s["status"] as String
                    val statusColor = when (status) { "approved" -> Success; "new" -> MaterialTheme.colorScheme.primary; else -> Warning }
                    PMCard(onClick = { selectedId = s["id"] as Int }) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    PMAvatar(s["student"] as String, size = 38, color = MaterialTheme.colorScheme.primary)
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(s["student"] as String, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                                        Text(s["project"] as String, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                                    }
                                }
                                PMBadge(status, color = statusColor)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Description, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(s["file"] as String, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)).padding(horizontal = 12.dp, vertical = 5.dp)) {
                                    Text(if ((s["id"] as Int) in submitted) "Reviewed ✓" else "Review", color = MaterialTheme.colorScheme.primary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ─── FORM (ANNOUNCEMENT / TASK) ──────────────────────────────────────────────
@Composable
fun FacultyForm() {
    var type by remember { mutableStateOf("announcement") }
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var sent by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Text("Create Form", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(bottom = 16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            for ((key, label) in listOf("announcement" to "📢 Announcement", "task" to "📋 Task")) {
                val isActive = type == key
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(10.dp))
                        .background(if (isActive) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { type = key }.padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(label, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
        if (!sent) {
            PMCard {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Column {
                        Text("Title *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
                        PMInput(if (type == "announcement") "Announcement title..." else "Task title...", value = title, onValueChange = { title = it })
                    }
                    Column {
                        Text("Target Team", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
                        var teamExpanded by remember { mutableStateOf(false) }
                        var selectedTeam by remember { mutableStateOf("All Teams") }
                        val teamOptions = listOf("All Teams", "6BCA_01 – AI Medical Diagnosis", "4MCA_08 – E-commerce Reco", "6BCA_03 – Chatbot Development")
                        Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp)).clickable { teamExpanded = true }.padding(14.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text(selectedTeam, fontSize = 14.sp); Icon(Icons.Default.ArrowDropDown, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        DropdownMenu(expanded = teamExpanded, onDismissRequest = { teamExpanded = false }) {
                            for (opt in teamOptions) {
                                DropdownMenuItem(text = { Text(opt) }, onClick = { selectedTeam = opt; teamExpanded = false })
                            }
                        }
                    }
                    if (type == "task") {
                        Column {
                            Text("Deadline", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
                            PMInput("Select date", value = "", onValueChange = {})
                        }
                    }
                    Column {
                        Text(if (type == "announcement") "Message *" else "Description *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
                        PMInput("Write your message...", value = body, onValueChange = { body = it }, singleLine = false)
                    }
                    PMButton("Send ${type.replaceFirstChar { it.uppercase() }}", onClick = { sent = true }, modifier = Modifier.fillMaxWidth())
                }
            }
        } else {
            PMCard {
                Column(modifier = Modifier.padding(24.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("✅", fontSize = 40.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("${if (type == "announcement") "Announcement" else "Task"} Sent!", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Success)
                    Text("All target teams have been notified.", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 6.dp))
                    Spacer(modifier = Modifier.height(14.dp))
                    PMButton("Create Another", onClick = { sent = false; title = ""; body = "" })
                }
            }
        }
    }
}

// ─── FACULTY PROFILE ─────────────────────────────────────────────────────────
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FacultyProfile(prefs: com.example.myapplication.data.PreferencesManager, themeMode: String, onThemeChange: (String) -> Unit, onLogout: () -> Unit, onOpenSettings: () -> Unit = {}) {
    val rawName = prefs.getActiveName()
    val userName = if (rawName.startsWith("Dr. ", ignoreCase = true) || rawName.startsWith("Dr.", ignoreCase = true)) rawName else "Dr. $rawName"
    val userEmail = prefs.getActiveEmail()
    val dept = prefs.getActiveDept()
    val expertiseTags = listOf("Data Science", "AI/ML", "Web Dev", "Research", "Cloud Computing", "Cyber Security")
    var selectedExpertise by remember { mutableStateOf(listOf("Data Science", "AI/ML")) }
    val researchOptions = listOf("Predictive Healthcare Analytics", "NLP for Education Systems", "Computer Vision", "IoT & Embedded Systems", "Blockchain Applications")
    var selectedResearch by remember { mutableStateOf(listOf("Predictive Healthcare Analytics", "NLP for Education Systems")) }
    var mentorAvailability by remember { mutableStateOf(true) }
    var officeHours by remember { mutableStateOf("Mon-Wed 10am-12pm") }
    val commPlatforms = listOf("Email", "Google Meet", "Zoom", "MS Teams", "WhatsApp")
    var selectedPlatform by remember { mutableStateOf("Google Meet") }
    var editMode by remember { mutableStateOf(false) }
    var headline by remember { mutableStateOf("Associate Professor | AI/ML Researcher | Mentor") }
    var biography by remember { mutableStateOf("Passionate educator with 8+ years in academia, specializing in AI/ML and Data Science.") }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            PMAvatar(userName, size = 80, color = Accent)
            Spacer(modifier = Modifier.height(12.dp))
            Text(userName, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            if (!editMode) {
                Text(headline, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            } else {
                Spacer(Modifier.height(4.dp))
                PMInput("Professional Headline", value = headline, onValueChange = { headline = it })
            }
            Text(userEmail, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Faculty · $dept", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
            PMBadge("Faculty", color = Accent)
        }
        PMCard(modifier = Modifier.padding(bottom = 12.dp)) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Expertise & Experience", fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
                androidx.compose.foundation.layout.FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    for (tag in expertiseTags) {
                        val selected = tag in selectedExpertise
                        Box(
                            modifier = Modifier.padding(bottom = 6.dp).clip(androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
                                .background(if (selected) Accent else MaterialTheme.colorScheme.surfaceVariant)
                                .clickable { selectedExpertise = if (selected) selectedExpertise - tag else selectedExpertise + tag }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(tag, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
                Text("8 Years of Experience", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 10.dp))
            }
        }
        PMCard(modifier = Modifier.padding(bottom = 12.dp)) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Active Research", fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
                for (r in researchOptions) {
                    val selected = r in selectedResearch
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().clickable {
                            selectedResearch = if (selected) selectedResearch - r else selectedResearch + r
                        }.padding(vertical = 5.dp)
                    ) {
                        Box(
                            modifier = Modifier.size(7.dp).clip(androidx.compose.foundation.shape.CircleShape)
                                .background(if (selected) Success else MaterialTheme.colorScheme.outline)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(r, fontSize = 13.sp, color = if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
        // Biography section
        if (editMode) {
            Text("BIOGRAPHY", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 1.sp, modifier = Modifier.padding(bottom = 8.dp))
            PMCard(modifier = Modifier.padding(bottom = 12.dp)) {
                Column(modifier = Modifier.padding(14.dp)) {
                    PMInput("Your biography...", value = biography, onValueChange = { biography = it }, singleLine = false)
                }
            }
        } else {
            PMCard(modifier = Modifier.padding(bottom = 12.dp)) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Biography", fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 6.dp))
                    Text(biography, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
        // Academic Management
        Text("ACADEMIC MANAGEMENT", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 1.sp, modifier = Modifier.padding(bottom = 8.dp))
        PMCard(modifier = Modifier.padding(bottom = 12.dp)) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                for ((label, value, icon) in listOf(
                    Triple("Subjects Handled", "Data Structures, AI/ML, DBMS", Icons.Default.MenuBook),
                    Triple("Student Batches", "6BCA-A, 4MCA-B, 2MSc-CS", Icons.Default.Groups),
                    Triple("Research Groups", "AI Research Lab, Data Science Club", Icons.Default.Science),
                    Triple("Guided Projects", "12 Active, 34 Completed", Icons.Default.Assignment)
                )) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(34.dp).clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.primaryContainer), contentAlignment = Alignment.Center) {
                            Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                        }
                        Spacer(Modifier.width(10.dp))
                        Column { Text(label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant); Text(value, fontSize = 13.sp, fontWeight = FontWeight.Medium) }
                    }
                }
            }
        }
        // Mentorship Settings
        Text("MENTORSHIP SETTINGS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 1.sp, modifier = Modifier.padding(bottom = 8.dp))
        PMCard(modifier = Modifier.padding(bottom = 12.dp)) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Mentorship Availability", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Text(if (mentorAvailability) "Open for mentorship requests" else "Currently unavailable", fontSize = 11.sp, color = if (mentorAvailability) Success else Danger)
                    }
                    androidx.compose.material3.Switch(checked = mentorAvailability, onCheckedChange = { mentorAvailability = it })
                }
                Column {
                    Text("Office Hours", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 4.dp))
                    if (!editMode) Text(officeHours, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    else PMInput("e.g. Mon-Wed 10am-12pm", value = officeHours, onValueChange = { officeHours = it })
                }
                Column {
                    Text("Preferred Communication", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 8.dp))
                    androidx.compose.foundation.layout.FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        for (p in commPlatforms) {
                            val sel = p == selectedPlatform
                            Box(
                                modifier = Modifier.padding(bottom = 6.dp).clip(androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
                                    .background(if (sel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                                    .clickable { selectedPlatform = p }.padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(p, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = if (sel) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
        // Analytics & Reputation
        Text("ANALYTICS & REPUTATION", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 1.sp, modifier = Modifier.padding(bottom = 8.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(bottom = 14.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                PMCard(modifier = Modifier.weight(1f)) { Column(modifier = Modifier.padding(14.dp)) { Text("Students Mentored", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant); Text("48", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary) } }
                PMCard(modifier = Modifier.weight(1f)) { Column(modifier = Modifier.padding(14.dp)) { Text("Avg. Mentor Rating", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant); Text("4.8 ⭐", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Warning) } }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                PMCard(modifier = Modifier.weight(1f)) { Column(modifier = Modifier.padding(14.dp)) { Text("Active Sessions", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant); Text("12", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Accent) } }
                PMCard(modifier = Modifier.weight(1f)) { Column(modifier = Modifier.padding(14.dp)) { Text("Project Success Rate", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant); Text("92%", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Success) } }
            }
        }
        PMCard(modifier = Modifier.padding(bottom = 16.dp)) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Links", fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
                for ((i, l) in listOf("LinkedIn", "Google Scholar", "ResearchGate").withIndex()) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp)) {
                        Icon(Icons.Default.Link, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(l, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                    }
                    if (i < 2) Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(MaterialTheme.colorScheme.outline))
                }
            }
        }
        // Account section
        Text("ACCOUNT", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 1.sp, modifier = Modifier.padding(bottom = 8.dp))
        PMCard(modifier = Modifier.padding(bottom = 10.dp)) {
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                for ((label, icon) in listOf(
                    "Settings" to Icons.Default.Settings,
                    "Help & Support" to Icons.Default.HelpOutline
                )) {
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable { if (label == "Settings") onOpenSettings() }.padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(Modifier.size(32.dp).clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.surfaceVariant), contentAlignment = Alignment.Center) {
                            Icon(icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                        }
                        Text(label, fontSize = 13.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
                        Icon(Icons.Default.ChevronRight, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                    }
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(MaterialTheme.colorScheme.outline.copy(0.4f)))
                }
            }
        }
        Spacer(Modifier.height(4.dp))
        PMButton(if (editMode) "Save Profile" else "Edit Profile", onClick = { editMode = !editMode }, variant = if (editMode) "success" else "outline", modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        PMButton("Logout", onClick = onLogout, variant = "danger", modifier = Modifier.fillMaxWidth())
    }
}
