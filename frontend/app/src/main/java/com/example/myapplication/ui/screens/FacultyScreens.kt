package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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

// ─── FACULTY HOME ─────────────────────────────────────────────────────────────
@Composable
fun FacultyHome() {
    data class TeamInfo(val id: String, val name: String, val project: String, val progress: Int, val members: List<String>, val domain: String, val deadline: String)
    val teams = listOf(
        TeamInfo("6BCA_01", "6BCA_01", "AI-Based Medical Diagnosis", 65, listOf("Vijay Shah", "Rahul Verma", "Sneha Patel", "Anita Singh"), "Data Science", "Jun 15, 2026"),
        TeamInfo("4MCA_08", "4MCA_08", "E-commerce Recommendations", 40, listOf("Priya Agarwal", "Rahul Mehta"), "Web Dev", "Jun 30, 2026"),
        TeamInfo("6BCA_03", "6BCA_03", "Chatbot Development", 55, listOf("Ankit Roy", "Meera Nair", "Suresh K."), "AI/ML", "Jul 5, 2026")
    )
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
                    t.members.forEachIndexed { i, m -> Row(verticalAlignment = Alignment.CenterVertically) { PMAvatar(m, size = 34, color = if(i==0) MaterialTheme.colorScheme.primary else Accent); Spacer(Modifier.width(10.dp)); Column { Text(m, fontSize = 13.sp, fontWeight = FontWeight.SemiBold); Text(if(i==0) "Leader" else "Member", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) } } }
                }
            }
            Text("Project Goals", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(bottom = 8.dp))
            PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
                Column(modifier = Modifier.padding(14.dp)) {
                    listOf("Build an AI model for early disease detection", "Integrate with hospital management systems", "Achieve 90%+ accuracy on test dataset").forEach { g ->
                        Row(modifier = Modifier.padding(vertical = 4.dp)) { Icon(Icons.Default.CheckCircle, null, tint = Success, modifier = Modifier.size(14.dp).padding(end=4.dp)); Spacer(Modifier.width(6.dp)); Text(g, fontSize = 13.sp) }
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
            Text("Dr. Anand Kumar 👨‍🏫", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Text("Assistant Professor, Dept. of Computer Applications", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)) {
            listOf(Triple("Active", "5", MaterialTheme.colorScheme.primary), Triple("Pending", "2", Warning), Triple("Approved", "8", Success)).forEach { s ->
                PMCard(modifier = Modifier.weight(1f)) { Column(modifier = Modifier.padding(12.dp)) { Text(s.first, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant); Text(s.second, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = s.third) } }
            }
        }
        Text("Your Teams", fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            teams.forEach { t ->
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

// ─── REGISTRATIONS ───────────────────────────────────────────────────────────
@Composable
fun FacultyRegistrations() {
    var studentId by remember { mutableStateOf("") }
    var projectName by remember { mutableStateOf("") }
    var added by remember { mutableStateOf(false) }
    val pending = listOf(
        Pair("Vijay Shah", "AI-Based Medical Diagnosis"),
        Pair("Anita Singh", "Healthcare Analytics")
    )
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Text("Registrations", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(bottom = 16.dp))
        Text("Pending Requests", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 10.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(bottom = 24.dp)) {
            pending.forEach { (name, proj) ->
                PMCard {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                PMAvatar(name, size = 36, color = MaterialTheme.colorScheme.primary)
                                Column {
                                    Text(name, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text(proj, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                            PMBadge("Pending", color = Warning)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            PMButton("Accept", onClick = {}, variant = "success", modifier = Modifier.weight(1f))
                            PMButton("Reject", onClick = {}, variant = "danger", modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
        Text("Manually Add Student", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 10.dp))
        PMCard {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Column {
                    Text("Student Roll No *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
                    PMInput("e.g. 6BCA2201", value = studentId, onValueChange = { studentId = it })
                }
                Column {
                    Text("Project Name *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
                    PMInput("e.g. AI Medical Diagnosis", value = projectName, onValueChange = { projectName = it })
                }
                if (added) {
                    Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(Success.copy(alpha = 0.1f)).padding(12.dp)) {
                        Text("✓ Student added successfully!", color = Success, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }
                PMButton("Add Student Directly", onClick = { added = true }, modifier = Modifier.fillMaxWidth())
            }
        }
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

    val submissions = listOf(
        mapOf("id" to 1, "student" to "Vijay Shah", "project" to "AI-Based Medical Diagnosis", "file" to "Proposal.pdf", "status" to "new", "domain" to "Data Science", "date" to "Jun 15, 2026"),
        mapOf("id" to 2, "student" to "Priya Agarwal", "project" to "E-commerce Recommendations", "file" to "Review1.pptx", "status" to "reviewed", "domain" to "Web Dev", "date" to "Jun 5, 2026"),
        mapOf("id" to 3, "student" to "Rahul Mehta", "project" to "Chatbot Development", "file" to "Proposal.pdf", "status" to "approved", "domain" to "AI/ML", "date" to "May 20, 2026"),
        mapOf("id" to 4, "student" to "Anita Singh", "project" to "Healthcare Analytics", "file" to "Review2.pptx", "status" to "new", "domain" to "Data Science", "date" to "Jun 18, 2026")
    )

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
                    Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.surfaceVariant).padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Description, null, tint = Danger, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(sub["file"] as String, modifier = Modifier.weight(1f), fontSize = 13.sp)
                        Icon(Icons.Default.Download, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
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
            tabs.forEach { t ->
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
            filtered.forEach { s ->
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
            listOf("announcement" to "📢 Announcement", "task" to "📋 Task").forEach { (key, label) ->
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
                            teamOptions.forEach { opt -> DropdownMenuItem(text = { Text(opt) }, onClick = { selectedTeam = opt; teamExpanded = false }) }
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
@Composable
fun FacultyProfile(prefs: com.example.myapplication.data.PreferencesManager, themeMode: String, onThemeChange: (String) -> Unit, onLogout: () -> Unit) {
    val userName = prefs.getActiveName()
    val userEmail = prefs.getActiveEmail()
    val dept = prefs.getActiveDept()

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            PMAvatar(userName, size = 80, color = Accent)
            Spacer(modifier = Modifier.height(12.dp))
            Text(if (userName.startsWith("Dr.")) userName else "Dr. $userName", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            Text(userEmail, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Faculty · $dept", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
            PMBadge("Faculty", color = Accent)
        }
        PMCard(modifier = Modifier.padding(bottom = 12.dp)) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Expertise & Experience", fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("Data Science", "AI/ML", "Web Dev", "Research").forEach { PMBadge(it, color = Accent) }
                }
                Text("8 Years of Experience", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 10.dp))
            }
        }
        PMCard(modifier = Modifier.padding(bottom = 12.dp)) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Active Research", fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
                listOf("Predictive Healthcare Analytics", "NLP for Education Systems").forEach { r ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 6.dp)) {
                        Box(modifier = Modifier.size(6.dp).clip(androidx.compose.foundation.shape.CircleShape).background(Success))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(r, fontSize = 13.sp)
                    }
                }
            }
        }
        PMCard(modifier = Modifier.padding(bottom = 16.dp)) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Links", fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
                listOf("LinkedIn", "Google Scholar", "ResearchGate").forEachIndexed { i, l ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp)) {
                        Icon(Icons.Default.Link, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(l, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                    }
                    if (i < 2) Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(MaterialTheme.colorScheme.outline))
                }
            }
        }
        PMButton("Logout", onClick = onLogout, variant = "danger", modifier = Modifier.fillMaxWidth())
    }
}
