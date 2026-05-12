package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
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
fun NotificationPanel(onClose: () -> Unit, onViewAll: () -> Unit) {
    val notifs = emptyList<Triple<String, String, Pair<androidx.compose.ui.graphics.Color, androidx.compose.ui.graphics.vector.ImageVector>>>()
    PMCard(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Column {
            Row(modifier = Modifier.fillMaxWidth().padding(14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Notifications", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (notifs.isNotEmpty()) {
                        PMBadge("${notifs.size} new", color = MaterialTheme.colorScheme.primary)
                    }
                    IconButton(onClick = onClose, modifier = Modifier.size(20.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                    }
                }
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            if (notifs.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    Text("No new notifications", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                for (notif in notifs) {
                    val (text, time, pair) = notif
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
            }
            Box(modifier = Modifier.fillMaxWidth().clickable { onViewAll() }.padding(12.dp), contentAlignment = Alignment.Center) {
                Text("View all notifications", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }
    }
}

// ─── FULL NOTIFICATIONS PAGE ─────────────────────────────────────────────────
@Composable
fun NotificationsPage() {
    data class Notif(val id: Int, val title: String, val body: String, val time: String, val type: String, val isRead: Boolean)
    data class FilterDef(val id: String, val label: String, val emoji: String, val types: List<String>)

    var activeFilter by remember { mutableStateOf("all") }
    var notifs by remember {
        mutableStateOf(emptyList<Notif>())
    }

    val filters = listOf(
        FilterDef("all",          "All",           "🔔", emptyList()),
        FilterDef("unread",       "Unread",         "●",  emptyList()),
        FilterDef("mention",      "Mentions",       "@",  listOf("mention")),
        FilterDef("task",         "Tasks",          "📋", listOf("task")),
        FilterDef("announcement", "Announcements",  "📢", listOf("announcement")),
        FilterDef("review",       "Reviews",        "⭐", listOf("review")),
        FilterDef("team",         "Team",           "👥", listOf("team"))
    )

    val filtered = when (activeFilter) {
        "all"    -> notifs
        "unread" -> notifs.filter { !it.isRead }
        else     -> notifs.filter { n -> filters.find { it.id == activeFilter }?.types?.contains(n.type) == true }
    }
    val unreadCount = notifs.count { !it.isRead }

    Column(modifier = Modifier.fillMaxSize().padding(bottom = 90.dp)) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Notifications", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                if (unreadCount > 0)
                    Text("$unreadCount unread", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                else
                    Text("All caught up!", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                if (unreadCount > 0) {
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                            .clickable { notifs = notifs.map { it.copy(isRead = true) } }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) { Text("Mark all read", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold) }
                }
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(8.dp))
                        .background(Danger.copy(alpha = 0.1f))
                        .clickable { notifs = emptyList() }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) { Text("Clear all", fontSize = 11.sp, color = Danger, fontWeight = FontWeight.SemiBold) }
            }
        }

        // Scrollable filter chips — no overflow
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filters) { f ->
                val isActive = activeFilter == f.id
                val count = when (f.id) {
                    "unread" -> notifs.count { !it.isRead }
                    "all"    -> notifs.size
                    else     -> notifs.count { n -> f.types.contains(n.type) }
                }
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { activeFilter = f.id }
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(f.emoji, fontSize = 12.sp)
                    Text(f.label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                        color = if (isActive) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                    if (count > 0 && !isActive) {
                        Box(modifier = Modifier.clip(CircleShape).background(MaterialTheme.colorScheme.primary).padding(horizontal = 5.dp, vertical = 1.dp)) {
                            Text("$count", fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // List or empty state
        if (filtered.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                PMEmptyState(
                    emoji = "🔔",
                    title = "No notifications",
                    subtitle = when (activeFilter) {
                        "unread"       -> "You're all caught up!"
                        "mention"      -> "No one has mentioned you yet."
                        "task"         -> "No task or deadline notifications."
                        "announcement" -> "No announcements at this time."
                        "review"       -> "No review notifications yet."
                        "team"         -> "No team activity yet."
                        else           -> "Your notifications will appear here."
                    }
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filtered, key = { it.id }) { notif ->
                    val color = when (notif.type) {
                        "review"       -> Success
                        "task"         -> Danger
                        "team"         -> Color(0xFF6366F1)
                        "mention"      -> Accent
                        "announcement" -> Warning
                        else           -> MaterialTheme.colorScheme.primary
                    }
                    val icon = when (notif.type) {
                        "review"       -> Icons.Default.Star
                        "task"         -> Icons.Default.Schedule
                        "team"         -> Icons.Default.Groups
                        "mention"      -> Icons.Default.AlternateEmail
                        "announcement" -> Icons.Default.Campaign
                        else           -> Icons.Default.Notifications
                    }
                    PMCard(
                        modifier = if (!notif.isRead)
                            Modifier.border(1.dp, MaterialTheme.colorScheme.primary.copy(0.25f), RoundedCornerShape(14.dp))
                        else Modifier
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp).fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier.size(42.dp).clip(RoundedCornerShape(12.dp))
                                    .background(color.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) { Icon(icon, null, tint = color, modifier = Modifier.size(20.dp)) }
                            Column(modifier = Modifier.weight(1f)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Text(notif.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1f))
                                    if (!notif.isRead) Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary))
                                }
                                Text(notif.body, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 3.dp))
                                Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Text(notif.time, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f))
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        if (!notif.isRead) {
                                            Box(
                                                modifier = Modifier.clip(RoundedCornerShape(6.dp))
                                                    .background(MaterialTheme.colorScheme.primary.copy(0.1f))
                                                    .clickable { notifs = notifs.map { if (it.id == notif.id) it.copy(isRead = true) else it } }
                                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                                            ) { Text("Mark read", fontSize = 10.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold) }
                                        }
                                        Box(
                                            modifier = Modifier.clip(RoundedCornerShape(6.dp))
                                                .background(Danger.copy(0.08f))
                                                .clickable { notifs = notifs.filter { it.id != notif.id } }
                                                .padding(horizontal = 8.dp, vertical = 3.dp)
                                        ) { Text("Remove", fontSize = 10.sp, color = Danger, fontWeight = FontWeight.SemiBold) }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
// --- CHAT SCREEN -------------------------------------------------------------
@Composable
fun ChatScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var activeChat by remember { mutableStateOf<String?>(null) }

    if (activeChat != null) {
        FullScreenChat(userName = activeChat!!, onClose = { activeChat = null })
        return
    }

    val recentChats = listOf(
        Pair("Dr. Anand Kumar", "Mentor"),
        Pair("Rahul Verma", "Student � 6BCA_01")
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).padding(bottom = 90.dp)) {
        Text("Chat", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(bottom = 16.dp))
        
        PMInput(
            label = "Search by College ID or Username...",
            value = searchQuery,
            onValueChange = { searchQuery = it },
            icon = Icons.Default.Search
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (searchQuery.isNotBlank()) {
            Text("Search Results", fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp))
            PMCard(onClick = { activeChat = searchQuery }) {
                Row(modifier = Modifier.padding(14.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    PMAvatar(name = searchQuery, size = 40, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(searchQuery, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Tap to start chatting", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        } else {
            Text("Recent Chats", fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp))
            if (recentChats.isEmpty()) {
                PMEmptyState(
                    emoji = "??",
                    title = "No recent chats",
                    subtitle = "Search for a user by their College ID to start a conversation."
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    recentChats.forEach { (name, desc) ->
                        PMCard(onClick = { activeChat = name }) {
                            Row(modifier = Modifier.padding(14.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                PMAvatar(name = name, size = 40, color = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(desc, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FullScreenChat(userName: String, onClose: () -> Unit) {
    var messageText by remember { mutableStateOf("") }
    var messages by remember {
        mutableStateOf(listOf(
            Triple(userName, "Hello! How can I help you?", false)
        ))
    }
    
    Column(modifier = Modifier.fillMaxSize().padding(bottom = 90.dp)) {
        // Chat Header
        Row(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary).padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.clickable { onClose() })
                PMAvatar(name = userName, size = 36, color = Color.White)
                Text(userName, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
            }
        }

        // Messages
        LazyColumn(
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            reverseLayout = true
        ) {
            items(messages.reversed()) { (sender, text, isMe) ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start) {
                    Box(
                        modifier = Modifier.widthIn(max = 260.dp)
                            .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp, bottomStart = if (isMe) 14.dp else 4.dp, bottomEnd = if (isMe) 4.dp else 14.dp))
                            .background(if (isMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        Text(text, color = if (isMe) Color.White else MaterialTheme.colorScheme.onSurface, fontSize = 14.sp)
                    }
                }
            }
        }

        // Input Box
        Row(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(12.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = messageText, onValueChange = { messageText = it },
                placeholder = { Text("Type a message...", fontSize = 14.sp) },
                modifier = Modifier.weight(1f).height(50.dp), shape = RoundedCornerShape(25.dp),
                colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant, focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant, unfocusedBorderColor = Color.Transparent, focusedBorderColor = Color.Transparent),
                singleLine = true
            )
            IconButton(
                onClick = { if (messageText.isNotBlank()) { messages = messages + Triple("Me", messageText, true); messageText = "" } },
                modifier = Modifier.size(46.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White, modifier = Modifier.size(20.dp))
            }
        }
    }
}

