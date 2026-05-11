package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.*
import com.example.myapplication.ui.theme.*

// ═══════════════════════════════════════════════════════════════
// STUDENT HOME  (Dashboard + Team/Research chooser)
// ═══════════════════════════════════════════════════════════════

@Composable
fun StudentHomeFull(prefs: com.example.myapplication.data.PreferencesManager, mode: String, onModeChange: (String) -> Unit, onNavigate: (String) -> Unit) {
    val userName = prefs.getActiveName()
    val userDept = prefs.getActiveDept()
    when (mode) {
        "team" -> TeamDashboard(userName = userName, onBack = { onModeChange("") }, onNavigate = onNavigate)
        "research" -> ResearchDashboard(onBack = { onModeChange("") }, onNavigate = onNavigate)
        else -> ModeChooser(userName = userName, userDept = userDept, onSelectMode = onModeChange)
    }
}

@Composable
private fun ModeChooser(userName: String, userDept: String, onSelectMode: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(listOf(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.surfaceVariant)))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hello, $userName 👋", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
        Text("MCA Final Year • $userDept", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 36.dp))

        Text("Choose Your Project Mode", fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))

        PMCard(modifier = Modifier.padding(bottom = 14.dp), onClick = { onSelectMode("team") }) {
            Row(modifier = Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Groups, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Team Project", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                    Text("Collaborate with 3–4 teammates", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        PMCard(onClick = { onSelectMode("research") }) {
            Row(modifier = Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Accent.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Science, contentDescription = null, tint = Accent, modifier = Modifier.size(28.dp))
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Individual Research", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
                    Text("Solo research with a faculty guide", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// TEAM DASHBOARD
// ═══════════════════════════════════════════════════════════════

@Composable
fun TeamDashboard(userName: String, onBack: () -> Unit, onNavigate: (String) -> Unit) {
    var hasTeam by remember { mutableStateOf(false) }
    var showGroupChat by remember { mutableStateOf(false) }
    var showMentorChat by remember { mutableStateOf(false) }
    if (!hasTeam) { CreateTeamFlow(userName = userName, onBack = onBack, onTeamCreated = { hasTeam = true }); return }

    val members = listOf(
        Pair(userName, "Leader"),
        Pair("Rahul Verma", "Member"),
        Pair("Sneha Patel", "Member"),
        Pair("Anita Singh", "Member")
    )
    val tasks = listOf(
        Triple("Design UI", "In Progress", Warning),
        Triple("Build Backend", "Completed", Success),
        Triple("Write Documentation", "Pending", MaterialTheme.colorScheme.onSurfaceVariant)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .padding(bottom = 90.dp)
    ) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 20.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null,
                modifier = Modifier.clickable { onBack() }.padding(end = 12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Column {
                Text("AI-Based Medical Diagnosis", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                Text("Team 6BCA_01 · Data Science", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // Progress Card
        PMCard(modifier = Modifier.padding(bottom = 16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("Project Progress", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("65%", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.height(8.dp))
                PMProgressBar(65, MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(6.dp))
                Text("Guide: Dr. Anand Kumar · Due: Jun 15, 2026", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // Stat Row
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            listOf(Triple("Tasks", "8", MaterialTheme.colorScheme.primary), Triple("Done", "5", Success), Triple("Days Left", "22", Warning)).forEach { s ->
                PMCard(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(s.first, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(s.second, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = s.third)
                    }
                }
            }
        }

        // Team Members
        Text("Team Members", fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
        PMCard(modifier = Modifier.padding(bottom = 16.dp)) {
            Column(modifier = Modifier.padding(14.dp)) {
                members.forEach { (name, role) ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        PMAvatar(name = name, size = 36, color = if (role == "Leader") MaterialTheme.colorScheme.primary else Accent)
                        Column(modifier = Modifier.weight(1f)) {
                            Text(name, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            Text(role, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        if (role == "Leader") PMBadge("Leader", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }

        // Task List
        Text("Recent Tasks", fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            tasks.forEach { (task, status, color) ->
                PMCard {
                    Row(modifier = Modifier.padding(14.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(task, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        PMBadge(status, color = color)
                    }
                }
            }
        }

        // Workflow grid
        val steps = listOf(
            listOf("browse", "Find Guide", "Browse & request faculty", true, false),
            listOf("submit", "Submit Project", "Upload your documents", true, false),
            listOf("review", "Faculty Review", "Approval from guide", true, false),
            listOf("progress", "Track Progress", "Milestones & phases", false, true),
            listOf("eval", "Evaluation", "Results & final score", false, false),
            listOf("resources", "Resources", "Guidelines & templates", false, false)
        )
        val colors = listOf(MaterialTheme.colorScheme.primary, Color(0xFF6366F1), Color(0xFFF59E0B), Success, Color(0xFF8B5CF6), Danger)

        Text("⚡ Team Workflow", fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp, bottom = 12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            steps.chunked(2).forEachIndexed { rowIdx, row ->
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    row.forEachIndexed { colIdx, step ->
                        val idx = rowIdx * 2 + colIdx
                        val color = colors.getOrElse(idx) { MaterialTheme.colorScheme.primary }
                        val key = step[0] as String
                        val label = step[1] as String
                        val desc = step[2] as String
                        val done = step[3] as Boolean
                        val active = step[4] as Boolean

                        PMCard(modifier = Modifier.weight(1f), onClick = { onNavigate(key) }) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(
                                            if (done || active) color
                                            else MaterialTheme.colorScheme.surfaceVariant
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        when (idx) {
                                            0 -> Icons.Default.Search
                                            1 -> Icons.Default.CloudUpload
                                            2 -> Icons.AutoMirrored.Filled.FactCheck
                                            3 -> Icons.Default.Timeline
                                            4 -> Icons.Default.Star
                                            else -> Icons.Default.Folder
                                        },
                                        contentDescription = null,
                                        tint = if (done || active) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (done || active) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                                Text(desc, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 2.dp), lineHeight = 12.sp, maxLines = 2, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Group chat & mentor chat buttons
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            PMButton("💬 Team Group Chat", onClick = { showGroupChat = !showMentorChat.also { showMentorChat = false }; showGroupChat = !showGroupChat }, modifier = Modifier.weight(1f), variant = "outline")
            PMButton("📩 Mentor Chat", onClick = { showMentorChat = !showGroupChat.also { showGroupChat = false }; showMentorChat = !showMentorChat }, modifier = Modifier.weight(1f))
        }
    }
    if (showGroupChat) {
        Box(modifier = Modifier.fillMaxSize().padding(bottom = 80.dp), contentAlignment = Alignment.BottomEnd) {
            com.example.myapplication.ui.components.GroupChatPanel(onClose = { showGroupChat = false })
        }
    }
    if (showMentorChat) {
        Box(modifier = Modifier.fillMaxSize().padding(bottom = 80.dp), contentAlignment = Alignment.BottomEnd) {
            com.example.myapplication.ui.components.ChatPanel(onClose = { showMentorChat = false })
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// CREATE TEAM FLOW
// ═══════════════════════════════════════════════════════════════
@Composable
private fun CreateTeamFlow(userName: String, onBack: () -> Unit, onTeamCreated: () -> Unit) {
    var step by remember { mutableStateOf(0) }
    var projectName by remember { mutableStateOf("") }
    var projectDesc by remember { mutableStateOf("") }
    var projectDomain by remember { mutableStateOf("") }
    var projectGoals by remember { mutableStateOf("") }
    var memberEmail by remember { mutableStateOf("") }
    var members by remember { mutableStateOf(listOf(userName)) }
    var selectedFaculty by remember { mutableStateOf("") }
    val domains = listOf("Data Science", "AI/ML", "Web Dev", "Cyber Security", "IoT", "Blockchain")
    val facultyList = listOf("Dr. Anand Kumar", "Dr. Lisa Wong", "Dr. Aman Kumar", "Dr. Anita Sharma")
    val stepLabels = listOf("Project Details", "Invite Members", "Assign Mentor", "Confirm")

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 16.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, modifier = Modifier.clickable { onBack() }.padding(end = 12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Create New Team", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        }
        // Step indicator
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            stepLabels.forEachIndexed { i, label ->
                val done = i < step; val active = i == step
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier.size(28.dp).clip(androidx.compose.foundation.shape.CircleShape)
                        .background(if (done || active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant), contentAlignment = Alignment.Center) {
                        if (done) Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(14.dp))
                        else Text("${i+1}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (active) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text(label, fontSize = 9.sp, color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 3.dp), maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                }
            }
        }
        when (step) {
            0 -> PMCard { Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Project Details", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Column { Text("Project Name *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("e.g. AI Medical Diagnosis", value = projectName, onValueChange = { projectName = it }) }
                Column { Text("Domain *", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("Select domain", value = projectDomain, onValueChange = { projectDomain = it }) }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) { domains.take(3).forEach { d -> Box(modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(if (projectDomain == d) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant).clickable { projectDomain = d }.padding(horizontal = 10.dp, vertical = 5.dp)) { Text(d, fontSize = 11.sp, color = if (projectDomain == d) Color.White else MaterialTheme.colorScheme.onSurfaceVariant) } } }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) { domains.drop(3).forEach { d -> Box(modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(if (projectDomain == d) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant).clickable { projectDomain = d }.padding(horizontal = 10.dp, vertical = 5.dp)) { Text(d, fontSize = 11.sp, color = if (projectDomain == d) Color.White else MaterialTheme.colorScheme.onSurfaceVariant) } } }
                Column { Text("Goals & Objectives", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("Describe your project goals...", value = projectGoals, onValueChange = { projectGoals = it }, singleLine = false) }
                Column { Text("Description", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp)); PMInput("Brief project description...", value = projectDesc, onValueChange = { projectDesc = it }, singleLine = false) }
                PMButton("Next: Invite Members →", onClick = { if (projectName.isNotBlank()) step = 1 }, modifier = Modifier.fillMaxWidth())
            } }
            1 -> PMCard { Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Invite Team Members", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("Add up to 3 more members (by email or roll number)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PMInput("Enter member email or roll no.", value = memberEmail, onValueChange = { memberEmail = it }, modifier = Modifier.weight(1f))
                    PMButton("+", onClick = { if (memberEmail.isNotBlank() && members.size < 4) { members = members + memberEmail; memberEmail = "" } })
                }
                members.forEach { m -> PMCard { Row(modifier = Modifier.padding(10.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) { PMAvatar(m, size = 32, color = if (m == userName) MaterialTheme.colorScheme.primary else Accent); Spacer(Modifier.width(10.dp)); Column(modifier = Modifier.weight(1f)) { Text(m, fontSize = 13.sp, fontWeight = FontWeight.SemiBold); Text(if (m == userName) "You · Leader" else "Member", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) } } } }
                PMButton("Next: Assign Mentor →", onClick = { step = 2 }, modifier = Modifier.fillMaxWidth())
                PMButton("← Back", onClick = { step = 0 }, modifier = Modifier.fillMaxWidth(), variant = "outline")
            } }
            2 -> PMCard { Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Assign Faculty Mentor", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("Select a faculty mentor for your project", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                facultyList.forEach { f -> PMCard(onClick = { selectedFaculty = f }, modifier = Modifier.border(if (selectedFaculty == f) 2.dp else 0.dp, if (selectedFaculty == f) MaterialTheme.colorScheme.primary else Color.Transparent, RoundedCornerShape(12.dp))) { Row(modifier = Modifier.padding(12.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) { PMAvatar(f, size = 36, color = Accent); Spacer(Modifier.width(10.dp)); Column(Modifier.weight(1f)) { Text(f, fontWeight = FontWeight.Bold, fontSize = 13.sp); Text("Assistant Professor · Available", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) }; if (selectedFaculty == f) Icon(Icons.Default.CheckCircle, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp)) } } }
                PMButton("✓ Confirm & Create Team", onClick = { if (selectedFaculty.isNotBlank()) { step = 3 } }, modifier = Modifier.fillMaxWidth())
                PMButton("← Back", onClick = { step = 1 }, modifier = Modifier.fillMaxWidth(), variant = "outline")
            } }
            3 -> PMCard { Column(modifier = Modifier.padding(24.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🎉", fontSize = 48.sp)
                Spacer(Modifier.height(12.dp))
                Text("Team Created!", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Success)
                Text("$projectName", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp))
                Text("Mentor: $selectedFaculty", fontSize = 13.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 4.dp))
                Text("${members.size} member(s) invited", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp, bottom = 16.dp))
                PMButton("Go to Team Dashboard", onClick = onTeamCreated, modifier = Modifier.fillMaxWidth())
            } }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// RESEARCH DASHBOARD
// ═══════════════════════════════════════════════════════════════

@Composable
fun ResearchDashboard(onBack: () -> Unit, onNavigate: (String) -> Unit) {
    val steps = listOf(
        listOf("browse", "Find Guide", "Browse & request faculty", true, false),
        listOf("submit", "Submit Project", "Upload your documents", true, false),
        listOf("review", "Faculty Review", "Approval from guide", true, false),
        listOf("progress", "Track Progress", "Milestones & phases", false, true),
        listOf("eval", "Evaluation", "Results & final score", false, false),
        listOf("resources", "Resources", "Guidelines & templates", false, false)
    )
    val colors = listOf(MaterialTheme.colorScheme.primary, Color(0xFF6366F1), Color(0xFFF59E0B), Success, Color(0xFF8B5CF6), Danger)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .padding(bottom = 90.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 20.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null,
                modifier = Modifier.clickable { onBack() }.padding(end = 12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Column {
                Text("Research Dashboard 🔬", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                Text("Individual Research Mode", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // Progress overview card
        PMCard(modifier = Modifier.padding(bottom = 16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Overall Research Progress", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("75%", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(8.dp))
                PMProgressBar(75, MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(6.dp))
                Text("Mid Review in Progress", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // Mini stat row
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)) {
            listOf(Triple("Guide", "✓", MaterialTheme.colorScheme.primary), Triple("Score", "87", Warning), Triple("Phase", "3/4", Success)).forEach { s ->
                PMCard(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(s.first, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(s.second, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = s.third)
                    }
                }
            }
        }

        // Workflow grid
        Text("⚡ Research Workflow", fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            steps.chunked(2).forEachIndexed { rowIdx, row ->
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    row.forEachIndexed { colIdx, step ->
                        val idx = rowIdx * 2 + colIdx
                        val color = colors.getOrElse(idx) { MaterialTheme.colorScheme.primary }
                        val key = step[0] as String
                        val label = step[1] as String
                        val desc = step[2] as String
                        val done = step[3] as Boolean
                        val active = step[4] as Boolean

                        PMCard(modifier = Modifier.weight(1f), onClick = { onNavigate(key) }) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(color.copy(alpha = 0.18f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        if (done) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                        contentDescription = null,
                                        tint = if (done || active) color else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(label, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Text(desc, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                if (active) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    PMBadge("Active", color = color)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// RESEARCH SUB PAGES
// ═══════════════════════════════════════════════════════════════

@Composable
fun ResearchSubmission(onBack: () -> Unit, onSubmit: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var files by remember { mutableStateOf(setOf<String>()) }
    val fileItems = listOf(
        Triple("pdf", "Project Report (PDF)", "Upload PDF"),
        Triple("ppt", "Presentation (PPT)", "Upload PPT"),
        Triple("zip", "Project Source (ZIP)", "Upload Zip")
    )
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 20.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, modifier = Modifier.clickable { onBack() }.padding(end = 12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Project Submission", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        }
        Text("Upload Documents", fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp))
        fileItems.forEach { (key, label, sub) ->
            val isSelected = key in files
            PMCard(modifier = Modifier.padding(bottom = 10.dp), onClick = { 
                files = if (isSelected) files - key else files + key 
            }) {
                Row(modifier = Modifier.padding(14.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Description, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(label, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Text(sub, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Box(modifier = Modifier.size(28.dp).clip(RoundedCornerShape(8.dp)).background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent).border(1.5.dp, if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                        if (isSelected) Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(14.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Project Title", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
        PMInput("e.g. AI Chatbot for College Website", value = title, onValueChange = { title = it })
        Spacer(modifier = Modifier.height(16.dp))
        Text("Description", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
        PMInput("Briefly describe your project...", value = desc, onValueChange = { desc = it }, singleLine = false)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Upload Files", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
        var uploadedFile by remember { mutableStateOf("") }
        PMCard(onClick = { uploadedFile = "project_files.zip" }, modifier = Modifier.padding(bottom = 8.dp).border(1.5.dp, if (uploadedFile.isNotEmpty()) Success else MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))) {
            Row(modifier = Modifier.padding(14.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(10.dp)).background(if (uploadedFile.isNotEmpty()) Success.copy(0.15f) else MaterialTheme.colorScheme.surfaceVariant), contentAlignment = Alignment.Center) {
                    Icon(if (uploadedFile.isNotEmpty()) Icons.Default.CheckCircle else Icons.Default.CloudUpload, null, tint = if (uploadedFile.isNotEmpty()) Success else MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(if (uploadedFile.isNotEmpty()) uploadedFile else "Tap to upload project files", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    Text(if (uploadedFile.isNotEmpty()) "File uploaded ✓" else "ZIP, PDF, PPT supported", fontSize = 11.sp, color = if (uploadedFile.isNotEmpty()) Success else MaterialTheme.colorScheme.onSurfaceVariant)
                }
                if (uploadedFile.isEmpty()) PMBadge("Upload", color = MaterialTheme.colorScheme.primary)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        PMButton("Submit Project", onClick = onSubmit, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun ResearchSubmissionStatus(onBack: () -> Unit, onViewProgress: () -> Unit) {
    val docs = listOf(
        Pair("ProjectReport.pdf", "2.4 MB"),
        Pair("Presentation.pptx", "3.1 MB"),
        Pair("Source Code.zip", "12.6 MB")
    )
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 24.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, modifier = Modifier.clickable { onBack() }.padding(end = 12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Submission Status", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        }
        Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)).border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), RoundedCornerShape(20.dp)).padding(vertical = 24.dp, horizontal = 16.dp), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.size(64.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(30.dp))
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text("Submitted Successfully", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(6.dp))
                PMBadge("On Time", color = MaterialTheme.colorScheme.primary)
                Text("Submitted on 25 Apr, 2025 at 10:30 AM", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 8.dp))
                Text("Deadline: 25 Apr, 2025, 11:59 PM", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 6.dp))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Submission Details", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.padding(bottom = 10.dp))
                docs.forEachIndexed { index, (name, size) ->
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Description, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(15.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(name, fontSize = 13.sp, modifier = Modifier.weight(1f))
                        Text(size, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    if (index < docs.size - 1) Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(MaterialTheme.colorScheme.outline))
                }
            }
        }
        PMButton("View Progress", onClick = onViewProgress, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun ResearchFacultyReview(onBack: () -> Unit, onProceed: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 20.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, modifier = Modifier.clickable { onBack() }.padding(end = 12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Faculty Review", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        }
        PMCard(modifier = Modifier.padding(bottom = 14.dp).border(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), RoundedCornerShape(12.dp))) {
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)).padding(16.dp).fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 6.dp)) {
                    Box(modifier = Modifier.size(28.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(14.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Approved", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.primary)
                }
                Text("Great work! You can proceed to the next phase.", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Feedback", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.padding(bottom = 8.dp))
                Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.surfaceVariant).padding(12.dp)) {
                    Text("\"Well structured project with good implementation. Keep up the good work!\"", fontSize = 13.sp)
                }
            }
        }
        PMCard(modifier = Modifier.padding(bottom = 16.dp)) {
            Row(modifier = Modifier.padding(14.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                PMAvatar("Dr. Lisa Wong", size = 40, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Dr. Lisa Wong", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("Reviewed on 25 Apr, 2025", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
        PMButton("Proceed", onClick = onProceed, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun ResearchProgressTracking(onBack: () -> Unit, onViewEval: () -> Unit) {
    val milestones = listOf(
        Triple("Idea", "Completed on 10 Apr, 2025", true),
        Triple("Proposal", "Completed on 18 Apr, 2025", true),
        Triple("Mid Review", "In Progress", false), // active
        Triple("Final Submission", "Pending", false)
    )
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 20.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, modifier = Modifier.clickable { onBack() }.padding(end = 12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Progress Tracking", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        }
        PMCard(modifier = Modifier.padding(bottom = 20.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Overall Progress", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("75%", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                }
                PMProgressBar(75, MaterialTheme.colorScheme.primary)
            }
        }
        Text("Milestones", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(bottom = 14.dp))
        Column {
            milestones.forEachIndexed { index, (label, sub, done) ->
                val active = !done && index == milestones.indexOfFirst { !it.third }
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(40.dp)) {
                        Box(
                            modifier = Modifier.size(32.dp).clip(CircleShape).background(if (done) MaterialTheme.colorScheme.primary else if (active) Color.White else MaterialTheme.colorScheme.surfaceVariant).border(2.dp, if (done || active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            if (done) Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(14.dp))
                            else Text("${index + 1}", fontWeight = FontWeight.ExtraBold, fontSize = 12.sp, color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        if (index < milestones.size - 1) {
                            Box(modifier = Modifier.width(2.dp).height(36.dp).background(if (done) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline))
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.padding(top = 4.dp).padding(bottom = if (index < milestones.size - 1) 36.dp else 0.dp)) {
                        Text(label, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(sub, fontSize = 12.sp, color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 2.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        PMButton("View Evaluation Results", onClick = onViewEval, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun ResearchEvaluation(onBack: () -> Unit) {
    val reviews = listOf(
        Pair("06 May, 2025", 85),
        Pair("17 May, 2025", 90)
    )
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 20.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, modifier = Modifier.clickable { onBack() }.padding(end = 12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Evaluation Results", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        }
        reviews.forEachIndexed { i, (date, score) ->
            PMCard(modifier = Modifier.padding(bottom = 12.dp)) {
                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Review ${i + 1}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Date: $date", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text("$score", fontWeight = FontWeight.ExtraBold, fontSize = 26.sp, color = MaterialTheme.colorScheme.primary)
                        Text("/100", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 4.dp))
                    }
                }
            }
        }
        PMCard(modifier = Modifier.padding(bottom = 14.dp).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)).border(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))) {
            Column(modifier = Modifier.padding(20.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🏆", fontSize = 36.sp, modifier = Modifier.padding(bottom = 4.dp))
                Text("Final Score", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(verticalAlignment = Alignment.Bottom) {
                    Text("87", fontWeight = FontWeight.ExtraBold, fontSize = 46.sp, color = MaterialTheme.colorScheme.primary, lineHeight = 46.sp)
                    Text("/100", fontSize = 18.sp, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f), modifier = Modifier.padding(bottom = 8.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                PMBadge("Great Job!", color = MaterialTheme.colorScheme.primary)
            }
        }
        PMCard(modifier = Modifier.padding(bottom = 10.dp)) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Feedback", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.padding(bottom = 6.dp))
                Text("Excellent work: The project is well researched and implemented.", fontSize = 13.sp)
            }
        }
        PMCard {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Comments", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.padding(bottom = 6.dp))
                Text("Presentation can be improved a little.", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
