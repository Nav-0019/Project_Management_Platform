package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.myapplication.ui.components.PMAvatar
import com.example.myapplication.ui.theme.*
import kotlinx.coroutines.launch

data class ChatMessage(
    val id: Int,
    val sender: String,
    val text: String,
    val isMe: Boolean,
    val time: String,
    val isMentor: Boolean = false
)

// ─── FULL-SCREEN TEAM GROUP CHAT ─────────────────────────────────────────────
@Composable
fun TeamGroupChatScreen(onBack: () -> Unit) {
    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    var messages by remember { mutableStateOf(emptyList<ChatMessage>()) }
    val members = emptyList<Triple<String,String,Color>>()

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Surface(shadowElevation = 4.dp, color = Color(0xFF6366F1)) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(onClick = onBack, modifier = Modifier.size(36.dp)) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                    }
                    Box(
                        modifier = Modifier.size(40.dp).clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Groups, null, tint = Color.White, modifier = Modifier.size(22.dp))
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Team Chat", fontWeight = FontWeight.ExtraBold, color = Color.White, fontSize = 16.sp)
                        Text("Group conversation", color = Color.White.copy(0.8f), fontSize = 12.sp)
                    }
                    IconButton(onClick = {}, modifier = Modifier.size(36.dp)) {
                        Icon(Icons.Default.MoreVert, null, tint = Color.White)
                    }
                }
                // Members strip
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    members.take(4).forEach { (name, _, color) ->
                        Box(
                            modifier = Modifier.size(28.dp).clip(CircleShape)
                                .background(color).border(2.dp, Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(name.first().toString(), color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(Modifier.width(4.dp))
                    Text("+Dr. Anand (Mentor)", fontSize = 10.sp, color = Color.White.copy(0.75f))
                }
            }
        }

        // Messages or empty state
        if (messages.isEmpty()) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("💬", fontSize = 40.sp)
                    Text("No messages yet", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Be the first to say something to your team!", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f).padding(horizontal = 14.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    androidx.compose.foundation.layout.Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Box(modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).padding(horizontal = 12.dp, vertical = 4.dp)) {
                            Text("Today", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
                items(messages) { msg -> MessageBubble(msg = msg) }
                item { Spacer(Modifier.height(4.dp)) }
            }
        }

        // Input bar
        Surface(shadowElevation = 4.dp, color = MaterialTheme.colorScheme.surface) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp)
                    .navigationBarsPadding(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    placeholder = { Text("Message the team...", fontSize = 13.sp) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color(0xFF6366F1)
                    ),
                    maxLines = 4
                )
                IconButton(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            val newMsg = ChatMessage(
                                id = messages.size + 1,
                                sender = "Me",
                                text = messageText,
                                isMe = true,
                                time = "now"
                            )
                            messages = messages + newMsg
                            messageText = ""
                            scope.launch { listState.animateScrollToItem(messages.size) }
                        }
                    },
                    modifier = Modifier.size(46.dp).clip(CircleShape).background(Color(0xFF6366F1))
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

@Composable
private fun MessageBubble(msg: ChatMessage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (msg.isMe) Alignment.End else Alignment.Start
    ) {
        if (!msg.isMe) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(start = 4.dp, bottom = 3.dp)
            ) {
                val avatarColor = when {
                    msg.isMentor -> MaterialTheme.colorScheme.primary
                    msg.sender == "Rahul Verma" -> Color(0xFF6366F1)
                    msg.sender == "Sneha Patel" -> MaterialTheme.colorScheme.tertiary
                    else -> Color(0xFFF59E0B)
                }
                PMAvatar(msg.sender, size = 22, color = avatarColor)
                Text(
                    msg.sender,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (msg.isMentor) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (msg.isMentor) {
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(0.12f))
                            .padding(horizontal = 6.dp, vertical = 1.dp)
                    ) {
                        Text("Mentor", fontSize = 9.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        Box(
            modifier = Modifier.widthIn(max = 260.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp, topEnd = 16.dp,
                        bottomStart = if (msg.isMe) 16.dp else 4.dp,
                        bottomEnd = if (msg.isMe) 4.dp else 16.dp
                    )
                )
                .background(
                    when {
                        msg.isMe -> MaterialTheme.colorScheme.primary
                        msg.isMentor -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    }
                )
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Column {
                Text(
                    msg.text,
                    color = if (msg.isMe) Color.White else MaterialTheme.colorScheme.onSurface,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
                Text(
                    msg.time,
                    color = if (msg.isMe) Color.White.copy(0.7f) else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
                )
            }
        }
    }
}

// ─── MENTOR CHAT FULL SCREEN ─────────────────────────────────────────────────
@Composable
fun MentorChatScreen(onBack: () -> Unit) {
    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    var messages by remember { mutableStateOf(emptyList<ChatMessage>()) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Surface(shadowElevation = 4.dp, color = MaterialTheme.colorScheme.primary) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(onClick = onBack, modifier = Modifier.size(36.dp)) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                }
                PMAvatar("Dr. Anand Kumar", size = 40, color = Color.White.copy(0.3f))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Dr. Anand Kumar", fontWeight = FontWeight.ExtraBold, color = Color.White, fontSize = 16.sp)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Box(modifier = Modifier.size(7.dp).clip(CircleShape).background(Color(0xFF4ADE80)))
                        Text("Mentor · Private Chat", color = Color.White.copy(0.8f), fontSize = 12.sp)
                    }
                }
                IconButton(onClick = {}, modifier = Modifier.size(36.dp)) {
                    Icon(Icons.Default.Info, null, tint = Color.White)
                }
            }
        }

        if (messages.isEmpty()) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("💬", fontSize = 40.sp)
                    Text("No messages yet", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Start the conversation with your mentor!", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f).padding(horizontal = 14.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Box(modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).padding(horizontal = 12.dp, vertical = 4.dp)) {
                            Text("Today", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
                items(messages) { msg ->
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = if (msg.isMe) Alignment.End else Alignment.Start) {
                        if (!msg.isMe) Text(msg.sender, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(start = 4.dp, bottom = 3.dp))
                        Box(modifier = Modifier.widthIn(max = 260.dp).clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = if (msg.isMe) 16.dp else 4.dp, bottomEnd = if (msg.isMe) 4.dp else 16.dp))
                            .background(if (msg.isMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant).padding(horizontal = 14.dp, vertical = 10.dp)) {
                            Column {
                                Text(msg.text, color = if (msg.isMe) Color.White else MaterialTheme.colorScheme.onSurface, fontSize = 13.sp, lineHeight = 18.sp)
                                Text(msg.time, color = if (msg.isMe) Color.White.copy(0.7f) else MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp, modifier = Modifier.align(Alignment.End).padding(top = 4.dp))
                            }
                        }
                    }
                }
                item { Spacer(Modifier.height(4.dp)) }
            }
        }

        Surface(shadowElevation = 4.dp, color = MaterialTheme.colorScheme.surface) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp).navigationBarsPadding(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    placeholder = { Text("Message mentor...", fontSize = 13.sp) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    ),
                    maxLines = 4
                )
                IconButton(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            messages = messages + ChatMessage(messages.size + 1, "Me", messageText, true, "now")
                            messageText = ""
                            scope.launch { listState.animateScrollToItem(messages.size) }
                        }
                    },
                    modifier = Modifier.size(46.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}
