package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Send
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
import com.example.myapplication.ui.theme.*

// ─── MENTOR CHAT PANEL ───────────────────────────────────────────────────────
@Composable
fun ChatPanel(onClose: () -> Unit) {
    var messageText by remember { mutableStateOf("") }
    var messages by remember {
        mutableStateOf(listOf(
            Triple("Dr. Anand Kumar", "Please submit your Review 1 PPT by tomorrow.", false),
            Triple("Me", "Sure, I'll submit it tonight!", true),
            Triple("Dr. Anand Kumar", "Great! Make sure to follow the template.", false)
        ))
    }
    PMCard(modifier = Modifier.fillMaxWidth().height(420.dp).padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary).padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    PMAvatar(name = "A", size = 36, color = Color.White)
                    Column {
                        Text("Dr. Anand Kumar", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                        Text("Mentor · Private Chat", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
                    }
                }
                IconButton(onClick = onClose, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
            }
            LazyColumn(
                modifier = Modifier.weight(1f).padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                reverseLayout = true
            ) {
                items(messages.reversed()) { (sender, text, isMe) ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start) {
                        Box(
                            modifier = Modifier.widthIn(max = 240.dp)
                                .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp, bottomStart = if (isMe) 14.dp else 4.dp, bottomEnd = if (isMe) 4.dp else 14.dp))
                                .background(if (isMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Column {
                                Text(text, color = if (isMe) Color.White else MaterialTheme.colorScheme.onSurface, fontSize = 13.sp)
                                Text("now", color = if (isMe) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp, modifier = Modifier.align(Alignment.End).padding(top = 4.dp))
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)).padding(10.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = messageText, onValueChange = { messageText = it },
                    placeholder = { Text("Message mentor...", fontSize = 13.sp) },
                    modifier = Modifier.weight(1f).height(50.dp), shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant, focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant, unfocusedBorderColor = Color.Transparent, focusedBorderColor = Color.Transparent),
                    singleLine = true
                )
                IconButton(
                    onClick = { if (messageText.isNotBlank()) { messages = messages + Triple("Me", messageText, true); messageText = "" } },
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

// ─── GROUP CHAT PANEL ────────────────────────────────────────────────────────
@Composable
fun GroupChatPanel(onClose: () -> Unit) {
    var messageText by remember { mutableStateOf("") }
    var messages by remember {
        mutableStateOf(listOf(
            Triple("Rahul Verma", "Has everyone started on their sections?", false),
            Triple("Sneha Patel", "Yes, frontend mockups are ready!", false),
            Triple("Me", "Working on the backend API now.", true),
            Triple("Anita Singh", "I'll start the documentation today.", false),
            Triple("Dr. Anand Kumar", "📌 Reminder: Review 1 PPT due tomorrow!", false)
        ))
    }
    PMCard(modifier = Modifier.fillMaxWidth().height(460.dp).padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth().background(Color(0xFF6366F1)).padding(14.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(Color.White.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Groups, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                    Column {
                        Text("Team 6BCA_01", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                        Text("Group · 4 members", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
                    }
                }
                IconButton(onClick = onClose, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
            }
            LazyColumn(
                modifier = Modifier.weight(1f).padding(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp), reverseLayout = true
            ) {
                items(messages.reversed()) { (sender, text, isMe) ->
                    val isMentor = sender == "Dr. Anand Kumar"
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = if (isMe) Alignment.End else Alignment.Start) {
                        if (!isMe) {
                            Text(sender, fontSize = 10.sp, fontWeight = FontWeight.SemiBold,
                                color = if (isMentor) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(start = 4.dp, bottom = 2.dp))
                        }
                        Box(
                            modifier = Modifier.widthIn(max = 240.dp)
                                .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp, bottomStart = if (isMe) 14.dp else 4.dp, bottomEnd = if (isMe) 4.dp else 14.dp))
                                .background(when { isMe -> MaterialTheme.colorScheme.primary; isMentor -> MaterialTheme.colorScheme.primary.copy(alpha = 0.12f); else -> MaterialTheme.colorScheme.surfaceVariant })
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(text, color = if (isMe) Color.White else MaterialTheme.colorScheme.onSurface, fontSize = 13.sp)
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)).padding(10.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = messageText, onValueChange = { messageText = it },
                    placeholder = { Text("Message team...", fontSize = 13.sp) },
                    modifier = Modifier.weight(1f).height(50.dp), shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant, focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant, unfocusedBorderColor = Color.Transparent, focusedBorderColor = Color.Transparent),
                    singleLine = true
                )
                IconButton(
                    onClick = { if (messageText.isNotBlank()) { messages = messages + Triple("Me", messageText, true); messageText = "" } },
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFF6366F1))
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

// ─── NOTIFICATION PANEL (overlay) ────────────────────────────────────────────
@Composable
fun NotificationPanel(onClose: () -> Unit) {
    val notifs = listOf(
        Triple("New submission from Vijay Shah", "2m ago", MaterialTheme.colorScheme.primary to Icons.Default.Description),
        Triple("Review deadline: AI-Based Medical Diagnosis", "1h ago", Warning to Icons.Default.Notifications),
        Triple("Admin approved your account", "2h ago", Success to Icons.AutoMirrored.Filled.Assignment),
        Triple("Guide request from Priya Agarwal", "3h ago", Accent to Icons.AutoMirrored.Filled.Assignment)
    )
    PMCard(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Column {
            Row(modifier = Modifier.fillMaxWidth().padding(14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Notifications", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PMBadge("4 new", color = MaterialTheme.colorScheme.primary)
                    IconButton(onClick = onClose, modifier = Modifier.size(20.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                    }
                }
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            notifs.forEach { (text, time, pair) ->
                val (color, icon) = pair
                Row(modifier = Modifier.fillMaxWidth().clickable {}.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(color.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Text(time, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 2.dp))
                    }
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            }
            Box(modifier = Modifier.fillMaxWidth().clickable {}.padding(12.dp), contentAlignment = Alignment.Center) {
                Text("View all notifications", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }
    }
}

// ─── FULL NOTIFICATIONS PAGE ─────────────────────────────────────────────────
@Composable
fun NotificationsPage() {
    data class Notif(val id: Int, val title: String, val body: String, val time: String, val type: String, val isRead: Boolean)
    var activeFilter by remember { mutableStateOf("all") }
    var notifs by remember {
        mutableStateOf(listOf(
            Notif(1, "Review Feedback Received", "Dr. Anand Kumar submitted Review 1 marks: 22/25", "2m ago", "review", false),
            Notif(2, "Submission Deadline", "Final report for AI Medical Diagnosis due in 3 days", "1h ago", "deadline", false),
            Notif(3, "Team Update", "Rahul Verma updated task: Build Backend → Completed", "2h ago", "team", false),
            Notif(4, "Guide Request Accepted", "Dr. Lisa Wong accepted your mentorship request", "3h ago", "guide", true),
            Notif(5, "New Announcement", "Faculty: Review 2 template uploaded in Resources", "5h ago", "announcement", true),
            Notif(6, "Admin Notice", "Submission window opens June 1, 2026", "1d ago", "admin", true),
            Notif(7, "Project Approved", "Your team project has been officially registered", "2d ago", "project", true)
        ))
    }
    val filters = listOf("all", "review", "team", "deadline", "announcement")
    val filtered = if (activeFilter == "all") notifs else notifs.filter { it.type == activeFilter }
    val unreadCount = notifs.count { !it.isRead }

    val typeColor: (String) -> Color = { type ->
        when (type) {
            "review" -> Success
            "deadline" -> Danger
            "team" -> Color(0xFF6366F1)
            "guide" -> Accent
            "announcement" -> Warning
            "admin" -> Warning
            else -> MaterialTheme.colorScheme.primary
        }
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp).padding(bottom = 90.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("Notifications", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                if (unreadCount > 0) Text("$unreadCount unread", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
            }
            if (unreadCount > 0) {
                Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)).clickable {
                    notifs = notifs.map { it.copy(isRead = true) }
                }.padding(horizontal = 12.dp, vertical = 6.dp)) {
                    Text("Mark all read", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        // Filter chips
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            filters.forEach { f ->
                val isActive = activeFilter == f
                Box(modifier = Modifier.clip(RoundedCornerShape(20.dp))
                    .background(if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { activeFilter = f }.padding(horizontal = 14.dp, vertical = 7.dp)) {
                    Text(f.replaceFirstChar { it.uppercase() }, fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                        color = if (isActive) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            filtered.forEach { notif ->
                val color = typeColor(notif.type)
                PMCard {
                    Row(modifier = Modifier.padding(14.dp).fillMaxWidth(), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(modifier = Modifier.size(42.dp).clip(RoundedCornerShape(12.dp)).background(color.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                            Icon(
                                when (notif.type) {
                                    "review" -> Icons.Default.Star
                                    "deadline" -> Icons.Default.Schedule
                                    "team" -> Icons.Default.Groups
                                    "guide" -> Icons.Default.Person
                                    "announcement" -> Icons.Default.Campaign
                                    else -> Icons.Default.Notifications
                                }, contentDescription = null, tint = color, modifier = Modifier.size(20.dp)
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text(notif.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1f))
                                if (!notif.isRead) Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary))
                            }
                            Text(notif.body, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 3.dp))
                            Text(notif.time, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f), modifier = Modifier.padding(top = 4.dp))
                        }
                    }
                }
            }
        }
    }
}
