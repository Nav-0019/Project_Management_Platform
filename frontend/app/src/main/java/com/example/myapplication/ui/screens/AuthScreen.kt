package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.PMButton
import com.example.myapplication.ui.components.PMInput

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.myapplication.data.PreferencesManager

@Composable
fun AuthScreen(prefs: PreferencesManager, onLogin: (String) -> Unit) {
    var tab by remember { mutableStateOf("signup") }
    var role by remember { mutableStateOf("student") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var empId by remember { mutableStateOf("") }
    var dept by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }
    var agreed by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }
    var adminCode by remember { mutableStateOf("") }

    val roles = listOf(
        Triple("student", "Student", "📚"),
        Triple("faculty", "Faculty", "👨\u200d🏫"),
        Triple("admin", "Admin", "🛡️")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(listOf(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.surfaceVariant))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(bottom = 24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 8.dp)) {
                    Box(
                        modifier = Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.foundation.Image(
                            painter = androidx.compose.ui.res.painterResource(id = com.example.myapplication.R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier.fillMaxSize().padding(4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("ProManage", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                }
                Text("Academic Project Management Platform", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            // Card
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    // Login / Sign Up tabs
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
                        listOf("login" to "Login", "signup" to "Sign Up").forEach { (t, label) ->
                            val isSelected = tab == t
                            Box(
                                modifier = Modifier.weight(1f).clickable { tab = t }.padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(label, fontWeight = FontWeight.Bold, color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
                                    if (isSelected) {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Box(modifier = Modifier.width(32.dp).height(2.dp).background(MaterialTheme.colorScheme.primary, RoundedCornerShape(1.dp)))
                                    }
                                }
                            }
                        }
                    }
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(MaterialTheme.colorScheme.outline))

                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(if (tab == "login") "Welcome Back 👋" else "Create Account", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                        Text(if (tab == "login") "Login to your account" else "Sign up to get started", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 20.dp))

                        // Role picker
                        Text("REGISTER AS", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 8.dp))
                        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            roles.forEach { (r, label, emoji) ->
                                val isSelected = role == r
                                Column(
                                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(12.dp))
                                        .border(2.dp, if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
                                        .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant)
                                        .clickable { role = r }.padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(emoji, fontSize = 22.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }

                        // Fields
                        if (tab == "signup") {
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(bottom = 12.dp)) {
                                PMInput("Full Name *", value = name, onValueChange = { name = it }, modifier = Modifier.weight(1f))
                                PMInput(if (role == "student") "Roll Number *" else if (role == "admin") "Admin ID *" else "Employee ID *", value = empId, onValueChange = { empId = it }, modifier = Modifier.weight(1f))
                            }
                            Column(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                                PMInput("Department *", value = dept, onValueChange = { dept = it })
                            }
                        }

                        PMInput(if (role == "student") "Official Email *" else "Email *", value = email, onValueChange = { email = it }, icon = Icons.Default.Email)
                        Spacer(modifier = Modifier.height(12.dp))

                        if (tab == "login" && role == "admin") {
                            Spacer(modifier = Modifier.height(12.dp))
                            PMInput("Admin Access Code *", value = adminCode, onValueChange = { adminCode = it }, icon = Icons.Default.Shield, isPassword = true)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("🔒 Admin accounts require institutional approval", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        PMInput(if (tab == "signup") "Create a strong password" else "Enter password", value = password, onValueChange = { password = it }, icon = Icons.Default.Lock, isPassword = true)

                        if (tab == "signup") {
                            Spacer(modifier = Modifier.height(12.dp))
                            PMInput("Confirm Password *", value = confirmPass, onValueChange = { confirmPass = it }, icon = Icons.Default.Lock, isPassword = true)
                            Spacer(modifier = Modifier.height(12.dp))
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(verticalAlignment = Alignment.Top, modifier = Modifier.padding(top = 4.dp)) {
                                Checkbox(checked = agreed, onCheckedChange = { agreed = it }, colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary))
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)) { append("I agree to the ") }
                                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)) { append("Terms of Service") }
                                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)) { append(" and ") }
                                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)) { append("Privacy Policy") }
                                    },
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(top = 12.dp)
                                )
                            }
                        }

                        if (errorMsg.isNotEmpty()) {
                            Text(errorMsg, color = com.example.myapplication.ui.theme.Danger, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 8.dp).fillMaxWidth(), textAlign = TextAlign.Center)
                        }

                        if (tab == "login") {
                            Text("Forgot Password?", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        val scope = rememberCoroutineScope()

                        PMButton(if (tab == "login") "Login" else "Create Account", onClick = {
                            errorMsg = ""
                            if (tab == "signup") {
                                if (role == "admin") {
                                    errorMsg = "Admin accounts cannot self-register. Contact your institution."
                                } else if (name.isBlank() || email.isBlank() || password.isBlank() || empId.isBlank() || dept.isBlank()) {
                                    errorMsg = "Please fill all required fields"
                                } else if (password != confirmPass) {
                                    errorMsg = "Passwords do not match"
                                } else if (!agreed) {
                                    errorMsg = "You must agree to the terms"
                                } else {
                                    scope.launch {
                                        try {
                                            val map = mapOf("name" to name, "email" to email, "password" to password, "role" to role, "idNumber" to empId, "department" to dept)
                                            val response = com.example.myapplication.network.ApiClient.apiService.register(map)
                                            if (response.isSuccessful) {
                                                prefs.saveAccount(email, password, role, name, empId, dept)
                                                prefs.saveActiveUser(email, name, empId, dept)
                                                onLogin(role)
                                            } else {
                                                errorMsg = "Registration failed: ${response.message()}"
                                            }
                                        } catch (e: Exception) {
                                            // Fallback: Save locally if API is not running
                                            prefs.saveAccount(email, password, role, name, empId, dept)
                                            prefs.saveActiveUser(email, name, empId, dept)
                                            onLogin(role)
                                        }
                                    }
                                }
                            } else {
                                if (email.isBlank() || password.isBlank()) {
                                    errorMsg = "Please enter email and password"
                                } else if (role == "admin" && adminCode.isBlank()) {
                                    errorMsg = "Admin Access Code is required"
                                } else {
                                    scope.launch {
                                        try {
                                            val req = com.example.myapplication.network.AuthRequest(email, password)
                                            val response = com.example.myapplication.network.ApiClient.apiService.login(req)
                                            if (response.isSuccessful) {
                                                val body = response.body()
                                                if (body != null) {
                                                    val u = body.user
                                                    prefs.saveActiveUser(u.email, u.name, u.idNumber, u.department)
                                                    onLogin(u.role)
                                                }
                                            } else {
                                                errorMsg = "Invalid email or password."
                                            }
                                        } catch (e: Exception) {
                                            // Fallback to local storage if API is down
                                            val verifiedRole = prefs.verifyAccount(email, password)
                                            if (verifiedRole != null) {
                                                prefs.saveActiveUser(email, prefs.getAccountName(email), prefs.getAccountId(email), prefs.getAccountDept(email))
                                                onLogin(verifiedRole)
                                            } else {
                                                errorMsg = "Network error & no local account found."
                                            }
                                        }
                                    }
                                }
                            }
                        }, modifier = Modifier.fillMaxWidth())

                        if (tab == "login") {
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.weight(1f).height(1.dp).background(MaterialTheme.colorScheme.outline))
                                Text("  OR  ", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Box(modifier = Modifier.weight(1f).height(1.dp).background(MaterialTheme.colorScheme.outline))
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            PMButton("G  Continue with Google", onClick = {}, variant = "outline", modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }
    }
}
