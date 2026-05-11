package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.*
import com.example.myapplication.ui.theme.*

// ─── SCORECARD ───────────────────────────────────────────────────────────────
@Composable
fun StudentScorecard() {
    var activeTab by remember { mutableStateOf("marks") }
    val reviews = listOf(
        mapOf("no" to "1", "date" to "20 Apr 2026", "max" to 25, "obtained" to 22, "faculty" to "Dr. Anand Kumar", "remarks" to "Good progress. Work on the UI design."),
        mapOf("no" to "2", "date" to "5 May 2026", "max" to 25, "obtained" to 23, "faculty" to "Dr. Lisa Wong", "remarks" to "Excellent data analysis. Improve documentation."),
        mapOf("no" to "Final", "date" to "5 Jun 2026", "max" to 50, "obtained" to null, "faculty" to "Panel", "remarks" to "Upcoming")
    )

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Text("Faculty Scorecard", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(bottom = 16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 16.dp)) {
            listOf("marks", "reviews", "feedback").forEach { t ->
                val isActive = activeTab == t
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { activeTab = t }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(t.replaceFirstChar { it.uppercase() }, color = if (isActive) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                }
            }
        }

        when (activeTab) {
            "marks" -> {
                PMCard(modifier = Modifier.padding(bottom = 16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Current Score", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text("45", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                            Text("/50", fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
                        }
                        PMProgressBar(90, MaterialTheme.colorScheme.primary)
                        Text("90% — Excellent", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 8.dp))
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    reviews.forEach { r ->
                        PMCard {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Text("Review ${r["no"]}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    val obtained = r["obtained"]
                                    if (obtained != null) {
                                        Row(verticalAlignment = Alignment.Bottom) {
                                            Text("$obtained", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                                            Text("/${r["max"]}", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                                        }
                                    } else {
                                        PMBadge("Upcoming", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                                Text("${r["date"]} · ${r["faculty"]}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                val obtained = r["obtained"]
                                if (obtained != null) {
                                    val pct = ((obtained as Int).toFloat() / (r["max"] as Int).toFloat() * 100).toInt()
                                    Spacer(modifier = Modifier.height(8.dp))
                                    PMProgressBar(pct, Success)
                                }
                            }
                        }
                    }
                }
            }
            "reviews" -> {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    reviews.filter { it["obtained"] != null }.forEach { r ->
                        PMCard {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text("Review ${r["no"]}", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(bottom = 4.dp))
                                Text("${r["faculty"]} · ${r["date"]}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 8.dp))
                                Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.surfaceVariant).padding(12.dp)) {
                                    Text("\"${r["remarks"]}\"", fontSize = 13.sp)
                                }
                            }
                        }
                    }
                }
            }
            else -> {
                PMCard {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Overall Feedback", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(bottom = 12.dp))
                        listOf(Pair("Research Quality", 85), Pair("Documentation", 72), Pair("Presentation", 90), Pair("Innovation", 78)).forEach { f ->
                            Column(modifier = Modifier.padding(bottom = 10.dp)) {
                                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(f.first, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("${f.second}%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                }
                                PMProgressBar(f.second, MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ─── RESOURCES ───────────────────────────────────────────────────────────────
@Composable
fun StudentResources() {
    var search by remember { mutableStateOf("") }
    val allResources = listOf(
        mapOf("name" to "Project Guidelines PDF", "size" to "1.2 MB", "type" to "pdf", "category" to "Circulars"),
        mapOf("name" to "Research Paper Format", "size" to "320 KB", "type" to "docx", "category" to "Circulars"),
        mapOf("name" to "Review 1 Template PPT", "size" to "570 KB", "type" to "pptx", "category" to "Templates"),
        mapOf("name" to "Review 2 Template PPT", "size" to "640 KB", "type" to "pptx", "category" to "Templates"),
        mapOf("name" to "Review 1 Submission PPT", "size" to "2.2 MB", "type" to "pptx", "category" to "Submissions"),
        mapOf("name" to "Review 2 Submission PPT", "size" to "5.1 MB", "type" to "pptx", "category" to "Submissions")
    )
    val filtered = allResources.filter { (it["name"] as String).contains(search, true) }
    val categories = filtered.map { it["category"] as String }.distinct()

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Text("Resources", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(bottom = 16.dp))
        PMInput("Search resources...", value = search, onValueChange = { search = it })
        Spacer(modifier = Modifier.height(16.dp))
        categories.forEach { cat ->
            Text(cat.uppercase(), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 1.sp, modifier = Modifier.padding(bottom = 8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 20.dp)) {
                filtered.filter { it["category"] == cat }.forEach { r ->
                    val type = r["type"] as String
                    val color = when (type) { "pdf" -> Danger; "pptx" -> Warning; else -> MaterialTheme.colorScheme.primary }
                    PMCard {
                        Row(modifier = Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Box(modifier = Modifier.size(38.dp).clip(RoundedCornerShape(8.dp)).background(color.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.Description, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
                                }
                                Column {
                                    Text(r["name"] as String, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                                    Text(r["size"] as String, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                            var downloaded by remember { mutableStateOf(false) }
                            Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(if(downloaded) com.example.myapplication.ui.theme.Success.copy(alpha = 0.1f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)).clickable { downloaded = true }.padding(8.dp)) {
                                Icon(if(downloaded) Icons.Default.CheckCircle else Icons.Default.Download, contentDescription = null, tint = if(downloaded) com.example.myapplication.ui.theme.Success else MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

// ─── PROFILE ─────────────────────────────────────────────────────────────────
@Composable
fun StudentProfile(prefs: com.example.myapplication.data.PreferencesManager, themeMode: String, onThemeChange: (String) -> Unit, onLogout: () -> Unit) {
    val userName = prefs.getActiveName()
    val userEmail = prefs.getActiveEmail()
    val rollNo = prefs.getActiveId()
    val dept = prefs.getActiveDept()
    var editMode by remember { mutableStateOf(false) }
    var headline by remember { mutableStateOf("Passionate developer | AI enthusiast") }
    var aboutMe by remember { mutableStateOf("Final year MCA student focused on AI/ML and full-stack development.") }
    var cgpa by remember { mutableStateOf("8.7") }
    var github by remember { mutableStateOf("github.com/user") }
    var linkedin by remember { mutableStateOf("linkedin.com/in/user") }
    var lookingForTeam by remember { mutableStateOf(true) }
    var openToJoin by remember { mutableStateOf(true) }
    var openMentorship by remember { mutableStateOf(false) }
    var internship by remember { mutableStateOf(true) }
    var hackathons by remember { mutableStateOf(true) }
    val skills = listOf("Python", "Kotlin", "TensorFlow", "React", "Node.js", "SQL")

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        // Header
        Column(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            PMAvatar(userName, size = 84, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(12.dp))
            Text(userName, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            if (!editMode) Text(headline, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
            else { Spacer(Modifier.height(4.dp)); PMInput("Professional headline", value = headline, onValueChange = { headline = it }) }
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PMBadge("Student", color = MaterialTheme.colorScheme.primary)
                PMBadge(dept, color = com.example.myapplication.ui.theme.Accent)
            }
        }
        // Section 1 — Personal
        Text("PERSONAL INFORMATION", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 1.sp, modifier = Modifier.padding(bottom = 8.dp))
        PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf(Triple("Email", userEmail, Icons.Default.Email), Triple("Roll No", rollNo, Icons.Default.Badge), Triple("Department", dept, Icons.Default.School)).forEach { (label, value, icon) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(34.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.primaryContainer), contentAlignment = Alignment.Center) { Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp)) }
                        Spacer(Modifier.width(10.dp))
                        Column { Text(label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant); Text(value, fontSize = 13.sp, fontWeight = FontWeight.Medium) }
                    }
                }
                Column {
                    Text("About Me", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 4.dp))
                    if (!editMode) Text(aboutMe, fontSize = 13.sp)
                    else PMInput("About you...", value = aboutMe, onValueChange = { aboutMe = it }, singleLine = false)
                }
            }
        }
        // Section 2 — Academic
        Text("ACADEMIC PROFILE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 1.sp, modifier = Modifier.padding(bottom = 8.dp))
        PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf(Triple("University", "State University of Technology", Icons.Default.AccountBalance), Triple("Degree", "MCA — Master of Computer Applications", Icons.Default.School), Triple("Year / Semester", "3rd Year, 6th Semester", Icons.Default.CalendarToday)).forEach { (label, value, icon) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(34.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.primaryContainer), contentAlignment = Alignment.Center) { Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp)) }
                        Spacer(Modifier.width(10.dp))
                        Column { Text(label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant); Text(value, fontSize = 13.sp, fontWeight = FontWeight.Medium) }
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(34.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.primaryContainer), contentAlignment = Alignment.Center) { Icon(Icons.Default.Star, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp)) }
                    Spacer(Modifier.width(10.dp))
                    Column(Modifier.weight(1f)) {
                        Text("CGPA", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        if (!editMode) Text(cgpa, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        else PMInput("Your CGPA", value = cgpa, onValueChange = { cgpa = it })
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(34.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.primaryContainer), contentAlignment = Alignment.Center) { Icon(Icons.Default.Person, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp)) }
                    Spacer(Modifier.width(10.dp))
                    Column { Text("Faculty Mentor", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant); Text("Dr. Anand Kumar (Assigned)", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary) }
                }
            }
        }
        // Section 3 — Skills
        Text("SKILLS & TECHNICAL PROFILE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 1.sp, modifier = Modifier.padding(bottom = 8.dp))
        PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Skills & Technologies", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) { skills.take(3).forEach { PMBadge(it, color = MaterialTheme.colorScheme.primary) } }
                Spacer(Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) { skills.drop(3).forEach { PMBadge(it, color = MaterialTheme.colorScheme.primary) } }
                Spacer(Modifier.height(10.dp))
                Text("Team Roles", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) { listOf("Frontend Dev", "AI Engineer", "PM").forEach { PMBadge(it, color = com.example.myapplication.ui.theme.Accent) } }
            }
        }
        // Section 4 — Social Links
        Text("SOCIAL LINKS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 1.sp, modifier = Modifier.padding(bottom = 8.dp))
        PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                if (!editMode) {
                    listOf("GitHub" to github, "LinkedIn" to linkedin, "Portfolio" to "portfolio.dev/user").forEach { (label, url) ->
                        Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Link, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp)); Spacer(Modifier.width(8.dp)); Column { Text(label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant); Text(url, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary) } }
                    }
                } else {
                    Column { Text("GitHub URL", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 4.dp)); PMInput("github.com/username", value = github, onValueChange = { github = it }) }
                    Column { Text("LinkedIn URL", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 4.dp)); PMInput("linkedin.com/in/username", value = linkedin, onValueChange = { linkedin = it }) }
                }
            }
        }
        // Section 5 — Collaboration Settings
        Text("COLLABORATION SETTINGS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 1.sp, modifier = Modifier.padding(bottom = 8.dp))
        PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
            Column(modifier = Modifier.padding(14.dp)) {
                listOf("Looking for Teammates" to lookingForTeam, "Open to Joining Projects" to openToJoin, "Open for Mentorship" to openMentorship, "Looking for Internship" to internship, "Interested in Hackathons" to hackathons).forEachIndexed { i, (label, checked) ->
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(label, fontSize = 13.sp, modifier = Modifier.weight(1f))
                        Switch(checked = checked, onCheckedChange = { v -> when(i){ 0->lookingForTeam=v; 1->openToJoin=v; 2->openMentorship=v; 3->internship=v; else->hackathons=v } })
                    }
                }
            }
        }
        PMThemeToggle(themeMode = themeMode, onThemeChange = onThemeChange)
        Spacer(Modifier.height(8.dp))
        PMButton(if (editMode) "Save Profile" else "Edit Profile", onClick = { editMode = !editMode }, variant = if (editMode) "success" else "outline", modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        PMButton("Logout", onClick = onLogout, variant = "danger", modifier = Modifier.fillMaxWidth())
    }
}

// ─── FIND GUIDE (Browse Faculty) ─────────────────────────────────────────────
@Composable
fun StudentFindGuide(onBack: () -> Unit) {
    var search by remember { mutableStateOf("") }
    var activeTag by remember { mutableStateOf("All") }
    var selectedFaculty by remember { mutableStateOf<Map<String, Any>?>(null) }
    val tags = listOf("All", "AI/ML", "Web Dev", "Data Science", "Cyber Security")
    val facultyList = listOf(
        mapOf<String, Any>("name" to "Dr. Lisa Wong", "dept" to "AI/ML", "exp" to "8 Years", "rating" to "4.8", "tags" to listOf("AI/ML", "Data Science"), "slots" to 2),
        mapOf<String, Any>("name" to "Dr. Aman Kumar", "dept" to "Web Development", "exp" to "7 Years", "rating" to "4.7", "tags" to listOf("Web Dev"), "slots" to 1),
        mapOf<String, Any>("name" to "Dr. Anita Sharma", "dept" to "Data Science", "exp" to "6 Years", "rating" to "4.6", "tags" to listOf("Data Science"), "slots" to 3),
        mapOf<String, Any>("name" to "Dr. Rahul Singh", "dept" to "Cyber Security", "exp" to "5 Years", "rating" to "4.5", "tags" to listOf("Cyber Security"), "slots" to 2)
    )

    if (selectedFaculty != null) {
        FacultyDetailView(faculty = selectedFaculty!!, onBack = { selectedFaculty = null })
        return
    }

    val filtered = facultyList.filter { f ->
        val fTags = f["tags"] as List<*>
        (activeTag == "All" || fTags.contains(activeTag)) && (f["name"] as String).contains(search, true)
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 16.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, modifier = Modifier.clickable { onBack() }.padding(end = 12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Browse Faculty", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.FilterList, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        PMInput("Search faculty by name or domain", value = search, onValueChange = { search = it }, icon = Icons.Default.Search)
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.horizontalScroll(rememberScrollState()).padding(bottom = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            tags.forEach { t ->
                val isActive = activeTag == t
                Box(modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant).clickable { activeTag = t }.padding(horizontal = 14.dp, vertical = 6.dp)) {
                    Text(t, color = if (isActive) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            filtered.forEach { f ->
                PMCard {
                    Row(modifier = Modifier.padding(14.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        PMAvatar(f["name"] as String, size = 44, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(f["name"] as String, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("${f["dept"]} · ⭐ ${f["rating"]} · ${f["exp"]}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${f["slots"]} slots available", fontSize = 11.sp, color = Success, modifier = Modifier.padding(top = 2.dp))
                        }
                        PMButton("View", onClick = { selectedFaculty = f }, modifier = Modifier.width(70.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun FacultyDetailView(faculty: Map<String, Any>, onBack: () -> Unit) {
    var requested by remember { mutableStateOf(false) }
    val pubs = listOf("AI based Student Support System — 2024", "Web Optimization Techniques — 2023", "Deep Learning Approaches — 2022")

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 20.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, modifier = Modifier.clickable { onBack() }.padding(end = 12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Faculty Profile", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        }
        PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
            Column(modifier = Modifier.padding(18.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                PMAvatar(faculty["name"] as String, size = 64, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(10.dp))
                Text(faculty["name"] as String, fontWeight = FontWeight.ExtraBold, fontSize = 17.sp)
                Text(faculty["dept"] as String, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                    PMBadge("⭐ ${faculty["rating"]}", color = Warning)
                    PMBadge(faculty["exp"] as String, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
        PMCard(modifier = Modifier.padding(bottom = 12.dp)) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Research Interests", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.padding(bottom = 10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Machine Learning", "Deep Learning", "NLP").forEach { PMBadge(it, color = MaterialTheme.colorScheme.primary) }
                }
            }
        }
        PMCard(modifier = Modifier.padding(bottom = 14.dp)) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Publications", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.padding(bottom = 10.dp))
                pubs.forEachIndexed { i, p ->
                    Row(modifier = Modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Description, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp).padding(end = 6.dp))
                        Text(p, fontSize = 12.sp, modifier = Modifier.weight(1f))
                    }
                    if (i < pubs.lastIndex) Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(MaterialTheme.colorScheme.outline))
                }
            }
        }
        if (!requested) {
            PMButton("Send Guide Request", onClick = { requested = true }, modifier = Modifier.fillMaxWidth())
        } else {
            PMCard {
                Column(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("✅ Guide Request Sent!", fontWeight = FontWeight.Bold, color = Success, fontSize = 15.sp)
                    Text("Awaiting faculty approval", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp))
                }
            }
        }
    }
}

// ─── NOTIFICATIONS PAGE ──────────────────────────────────────────────
@Composable
fun StudentNotificationsPage() {
    com.example.myapplication.ui.components.NotificationsPage()
}
