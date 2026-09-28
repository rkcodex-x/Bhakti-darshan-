package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.Screen
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamCardBorder
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.TempleBrown
import com.example.ui.theme.TempleTextDark
import com.example.ui.theme.TempleTextSecondary

@Composable
fun MoreScreen(
    isHindi: Boolean,
    reminderTime: String,
    isReminderEnabled: Boolean,
    onNavigateToFavorites: () -> Unit,
    onToggleLanguage: () -> Unit,
    onSetReminderTime: (String) -> Unit,
    onToggleReminder: (Boolean) -> Unit,
    onTestNotification: () -> Unit,
    onShareApp: () -> Unit
) {
    var showReminderDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showPolicyDialog by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }

    val reminderOptions = listOf("05:00 AM", "05:30 AM", "06:00 AM", "06:30 AM", "07:00 AM")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .testTag("more_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. User Devotional Profile Header Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldPrimary.copy(alpha = 0.6f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(listOf(GoldLight, SaffronPrimary))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "👤", fontSize = 28.sp)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = if (isHindi) "भक्त दर्शन परिवार" else "Devotee Profile",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TempleBrown
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (isHindi) "“हर सुबह भगवान के साथ” 🙏" else "“Every morning with the Divine” 🙏",
                            style = MaterialTheme.typography.bodySmall,
                            color = SaffronPrimary
                        )
                    }
                }
            }
        }

        // 2. Devotional Options Group
        item {
            Text(
                text = if (isHindi) "आध्यात्मिक सुविधाएं" else "Spiritual Features",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = TempleBrown,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, CreamCardBorder)
            ) {
                Column {
                    MoreMenuItem(
                        emoji = "❤️",
                        title = if (isHindi) "मेरे पसंदीदा" else "My Favorites",
                        subtitle = if (isHindi) "सहेजे गए भजन, दर्शन एवं विचार" else "Saved bhajans, deities & quotes",
                        onClick = onNavigateToFavorites
                    )
                    MoreDivider()
                    MoreMenuItem(
                        emoji = "🔔",
                        title = if (isHindi) "सुबह की भक्ति याद दिलाएं (पूजा रिमाइंडर)" else "Morning Puja Reminder",
                        subtitle = if (isHindi) "समय: $reminderTime • प्रतिदिन" else "Time: $reminderTime • Daily",
                        onClick = { showReminderDialog = true }
                    )
                    MoreDivider()
                    MoreMenuItem(
                        emoji = "🌐",
                        title = if (isHindi) "भाषा (Language)" else "Language (भाषा)",
                        subtitle = if (isHindi) "वर्तमान: हिन्दी (Tap to switch)" else "Current: English (टैप करें)",
                        onClick = onToggleLanguage
                    )
                }
            }
        }

        // 3. App Settings & Support
        item {
            Text(
                text = if (isHindi) "ऐप एवं सहायता" else "App & Support",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = TempleBrown,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, CreamCardBorder)
            ) {
                Column {
                    MoreMenuItem(
                        emoji = "📤",
                        title = if (isHindi) "भक्ति दर्शन ऐप शेयर करें" else "Share Bhakti Darshan App",
                        subtitle = if (isHindi) "मित्रों व परिजनों को भक्ति से जोड़ें" else "Share with family and friends",
                        onClick = onShareApp
                    )
                    MoreDivider()
                    MoreMenuItem(
                        emoji = "ℹ️",
                        title = if (isHindi) "हमारे बारे में" else "About Us",
                        subtitle = if (isHindi) "भक्ति दर्शन का उद्देश्य" else "Mission of Bhakti Darshan",
                        onClick = { showAboutDialog = true }
                    )
                    MoreDivider()
                    MoreMenuItem(
                        emoji = "🔒",
                        title = if (isHindi) "गोपनीयता नीति (Privacy Policy)" else "Privacy Policy",
                        subtitle = if (isHindi) "डेटा सुरक्षा व नियम" else "Data safety and policies",
                        onClick = { showPolicyDialog = true }
                    )
                    MoreDivider()
                    MoreMenuItem(
                        emoji = "📄",
                        title = if (isHindi) "नियम एवं शर्तें (Terms & Conditions)" else "Terms & Conditions",
                        subtitle = if (isHindi) "सेवा शर्तें" else "Terms of service",
                        onClick = { showTermsDialog = true }
                    )
                }
            }
        }

        // App Version
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "भक्ति दर्शन संस्करण 1.0 • हर सुबह भगवान के साथ 🙏",
                    style = MaterialTheme.typography.labelSmall,
                    color = TempleTextSecondary
                )
            }
        }
    }

    // Reminder Time Dialog
    if (showReminderDialog) {
        AlertDialog(
            onDismissRequest = { showReminderDialog = false },
            title = {
                Text(
                    text = if (isHindi) "सुबह की भक्ति याद दिलाएं 🔔" else "Morning Reminder 🔔",
                    fontWeight = FontWeight.Bold,
                    color = TempleBrown
                )
            },
            text = {
                Column {
                    Text(
                        text = if (isHindi) "प्रतिदिन सुबह पूजा और दर्शन के लिए समय चुनें:" else "Select daily morning reminder time:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TempleTextDark
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    reminderOptions.forEach { time ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    onSetReminderTime(time)
                                }
                                .padding(vertical = 8.dp, horizontal = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = time,
                                fontWeight = if (time == reminderTime) FontWeight.Bold else FontWeight.Normal,
                                color = if (time == reminderTime) SaffronPrimary else TempleTextDark
                            )
                            if (time == reminderTime) {
                                Text(text = "✓", color = SaffronPrimary, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            onTestNotification()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (isHindi) "अभी टेस्ट नोटिफिकेशन भेजें 🔔" else "Send Test Notification 🔔",
                            color = TempleBrown,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showReminderDialog = false }) {
                    Text(text = if (isHindi) "पूर्ण" else "Done", fontWeight = FontWeight.Bold, color = SaffronPrimary)
                }
            }
        )
    }

    // About Dialog
    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            title = {
                Text(text = "भक्ति दर्शन (Bhakti Darshan) 🙏", fontWeight = FontWeight.Bold, color = TempleBrown)
            },
            text = {
                Text(
                    text = "“भक्ति दर्शन” एक पावन आध्यात्मिक एप्लिकेशन है, जिसे प्रत्येक श्रद्धालु के प्रातःकाल को भगवान के दर्शन, भजन, आरती, पंचांग, राशिफल और सुविचार से मंगलमय बनाने के लिए बनाया गया है।\n\nटैगलाइन: हर सुबह भगवान के साथ\nनिर्माता: AI Studio Build Team",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TempleTextDark,
                    lineHeight = 22.sp
                )
            },
            confirmButton = {
                TextButton(onClick = { showAboutDialog = false }) {
                    Text(text = if (isHindi) "ठीक है" else "OK", fontWeight = FontWeight.Bold, color = SaffronPrimary)
                }
            }
        )
    }

    // Privacy Policy Dialog
    if (showPolicyDialog) {
        AlertDialog(
            onDismissRequest = { showPolicyDialog = false },
            title = {
                Text(text = "गोपनीयता नीति (Privacy Policy)", fontWeight = FontWeight.Bold, color = TempleBrown)
            },
            text = {
                Text(
                    text = "भक्ति दर्शन आपकी निजता का पूर्ण सम्मान करता है। आपकी पसंदीदा सूची एवं सेटिंग्स केवल आपके डिवाइस के स्थानीय स्टोरेज (Room Database) में सुरक्षित रहती हैं। कोई भी व्यक्तिगत डेटा बिना सहमति एकत्र नहीं किया जाता।",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TempleTextDark,
                    lineHeight = 22.sp
                )
            },
            confirmButton = {
                TextButton(onClick = { showPolicyDialog = false }) {
                    Text(text = "स्वीकार", fontWeight = FontWeight.Bold, color = SaffronPrimary)
                }
            }
        )
    }

    // Terms Dialog
    if (showTermsDialog) {
        AlertDialog(
            onDismissRequest = { showTermsDialog = false },
            title = {
                Text(text = "नियम एवं शर्तें (Terms & Conditions)", fontWeight = FontWeight.Bold, color = TempleBrown)
            },
            text = {
                Text(
                    text = "यह ऐप आध्यात्मिक साधना, ईश्वर वंदना, पंचांग एवं ज्योतिषीय मनोरंजन हेतु निशुल्क उपलब्ध कराई गई है। ऐप में प्रयुक्त समस्त मंत्र, श्लोक व भजन सार्वजनिक डोमेन एवं पारंपरिक भक्ति साहित्य पर आधारित हैं।",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TempleTextDark,
                    lineHeight = 22.sp
                )
            },
            confirmButton = {
                TextButton(onClick = { showTermsDialog = false }) {
                    Text(text = "स्वीकार", fontWeight = FontWeight.Bold, color = SaffronPrimary)
                }
            }
        )
    }
}

@Composable
private fun MoreMenuItem(
    emoji: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = emoji, fontSize = 22.sp)
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TempleTextDark
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TempleTextSecondary
                )
            }
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = TempleTextSecondary.copy(alpha = 0.5f),
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun MoreDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(CreamCardBorder.copy(alpha = 0.6f))
    )
}
