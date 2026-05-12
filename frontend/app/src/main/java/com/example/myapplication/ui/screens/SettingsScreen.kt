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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.PMCard
import com.example.myapplication.ui.components.PMThemeToggle
import com.example.myapplication.ui.theme.*

// ─── SETTINGS SCREEN ─────────────────────────────────────────────────────────
@Composable
fun SettingsScreen(
    themeMode: String,
    onThemeChange: (String) -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit = {}
) {
    // Notification preferences
    var pushNotifs by remember { mutableStateOf(true) }
    var emailNotifs by remember { mutableStateOf(true) }
    var teamActivity by remember { mutableStateOf(true) }
    var taskDeadlines by remember { mutableStateOf(true) }
    var mentionsReplies by remember { mutableStateOf(true) }
    var announcements by remember { mutableStateOf(false) }
    var soundVibration by remember { mutableStateOf(true) }

    // Privacy
    var profileVisible by remember { mutableStateOf(true) }
    var activityStatus by remember { mutableStateOf(true) }

    // Workspace
    var compactMode by remember { mutableStateOf(false) }
    var language by remember { mutableStateOf("English") }

    var expandedSection by remember { mutableStateOf<String?>("notifications") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .padding(bottom = 90.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack, null,
                modifier = Modifier.clickable { onBack() }.padding(end = 12.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Column {
                Text("Settings", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                Text("App preferences & configuration", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // APPEARANCE
        SettingsSection(
            icon = Icons.Default.Palette,
            title = "Appearance",
            subtitle = "Theme & display preferences",
            isExpanded = expandedSection == "appearance",
            onToggle = { expandedSection = if (expandedSection == "appearance") null else "appearance" },
            iconColor = Color(0xFF6366F1)
        ) {
            PMThemeToggle(themeMode = themeMode, onThemeChange = onThemeChange)
            Spacer(Modifier.height(10.dp))
            SettingsToggleRow("Compact Mode", "Reduce card sizes for more content", compactMode) { compactMode = it }
        }

        Spacer(Modifier.height(10.dp))

        // NOTIFICATIONS
        SettingsSection(
            icon = Icons.Default.Notifications,
            title = "Notifications",
            subtitle = "Control what you hear from ProManage",
            isExpanded = expandedSection == "notifications",
            onToggle = { expandedSection = if (expandedSection == "notifications") null else "notifications" },
            iconColor = MaterialTheme.colorScheme.primary
        ) {
            SettingsToggleRow("Push Notifications", "Receive in-app alerts", pushNotifs) { pushNotifs = it }
            SettingsDivider()
            SettingsToggleRow("Email Notifications", "Get summaries via email", emailNotifs) { emailNotifs = it }
            SettingsDivider()
            SettingsToggleRow("Team Activity Alerts", "Member joins, updates, completions", teamActivity) { teamActivity = it }
            SettingsDivider()
            SettingsToggleRow("Task Deadline Alerts", "Reminders before deadlines", taskDeadlines) { taskDeadlines = it }
            SettingsDivider()
            SettingsToggleRow("Mentions & Replies", "When someone tags you in chat", mentionsReplies) { mentionsReplies = it }
            SettingsDivider()
            SettingsToggleRow("Announcements", "Faculty & admin broadcasts", announcements) { announcements = it }
            SettingsDivider()
            SettingsToggleRow("Sound & Vibration", "Play sound for notifications", soundVibration) { soundVibration = it }
        }

        Spacer(Modifier.height(10.dp))

        // PRIVACY
        SettingsSection(
            icon = Icons.Default.Lock,
            title = "Privacy",
            subtitle = "Control your visibility",
            isExpanded = expandedSection == "privacy",
            onToggle = { expandedSection = if (expandedSection == "privacy") null else "privacy" },
            iconColor = Danger
        ) {
            SettingsToggleRow("Public Profile", "Allow others to view your profile", profileVisible) { profileVisible = it }
            SettingsDivider()
            SettingsToggleRow("Show Activity Status", "Show when you're online", activityStatus) { activityStatus = it }
        }

        Spacer(Modifier.height(10.dp))

        // WORKSPACE
        SettingsSection(
            icon = Icons.Default.Tune,
            title = "Workspace",
            subtitle = "Language & preferences",
            isExpanded = expandedSection == "workspace",
            onToggle = { expandedSection = if (expandedSection == "workspace") null else "workspace" },
            iconColor = Warning
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Language", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    Text("Interface language", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(language, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }
            SettingsDivider()
            SettingsToggleRow("Compact Mode", "Denser layout for more content", compactMode) { compactMode = it }
        }

        Spacer(Modifier.height(10.dp))

        // SECURITY
        SettingsSection(
            icon = Icons.Default.Security,
            title = "Security",
            subtitle = "Account protection",
            isExpanded = expandedSection == "security",
            onToggle = { expandedSection = if (expandedSection == "security") null else "security" },
            iconColor = Success
        ) {
            SettingsActionRow("Change Password", "Last changed: 30 days ago", Icons.Default.Key)
            SettingsDivider()
            SettingsActionRow("Active Sessions", "Manage logged-in devices", Icons.Default.Devices)
            SettingsDivider()
            SettingsActionRow("Two-Factor Authentication", "Not configured", Icons.Default.PhonelinkLock)
        }

        Spacer(Modifier.height(10.dp))

        // DATA & BACKUP
        SettingsSection(
            icon = Icons.Default.Backup,
            title = "Data & Backup",
            subtitle = "Export or clear your data",
            isExpanded = expandedSection == "data",
            onToggle = { expandedSection = if (expandedSection == "data") null else "data" },
            iconColor = Accent
        ) {
            SettingsActionRow("Export My Data", "Download all your project data", Icons.Default.Download)
            SettingsDivider()
            SettingsActionRow("Clear Cache", "Free up local storage", Icons.Default.CleaningServices)
        }

        Spacer(Modifier.height(10.dp))

        // ACCOUNT / LOGOUT
        SettingsSection(
            icon = Icons.Default.Close,
            title = "Account",
            subtitle = "Manage session",
            isExpanded = expandedSection == "account",
            onToggle = { expandedSection = if (expandedSection == "account") null else "account" },
            iconColor = Danger
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().clickable { onLogout() }.padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(Icons.Default.Close, null, tint = Danger, modifier = Modifier.size(18.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Log Out", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Danger)
                    Text("End your current session", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        Spacer(Modifier.height(14.dp))
        Text(
            "ProManage v1.0.0 — Build 2026.05",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

// ─── SECTION CARD ─────────────────────────────────────────────────────────────
@Composable
private fun SettingsSection(
    icon: ImageVector,
    title: String,
    subtitle: String,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    iconColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    PMCard {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggle() }
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp))
                        .background(iconColor.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, null, tint = iconColor, modifier = Modifier.size(18.dp))
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Icon(
                    if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    null, tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (isExpanded) {
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(MaterialTheme.colorScheme.outline))
                Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                    content()
                }
            }
        }
    }
}

// ─── TOGGLE ROW ──────────────────────────────────────────────────────────────
@Composable
private fun SettingsToggleRow(title: String, subtitle: String, checked: Boolean, onCheck: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
            Text(title, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Text(subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheck,
            modifier = Modifier.height(28.dp)
        )
    }
}

// ─── ACTION ROW ──────────────────────────────────────────────────────────────
@Composable
private fun SettingsActionRow(title: String, subtitle: String, icon: ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable {}.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(18.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Text(subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Icon(Icons.Default.ChevronRight, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
    }
}

@Composable
private fun SettingsDivider() {
    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp).height(1.dp).background(MaterialTheme.colorScheme.outline.copy(0.5f)))
}
