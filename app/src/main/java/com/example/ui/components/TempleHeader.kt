package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.TempleBrown

@Composable
fun TempleHeader(
    isHindi: Boolean,
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onLanguageToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val headerGradient = Brush.verticalGradient(
        colors = listOf(
            SaffronPrimary,
            SaffronOrange
        )
    )

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = SaffronPrimary,
        shadowElevation = 6.dp
    ) {
        Column(
            modifier = Modifier
                .background(headerGradient)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Brand Logo & Title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Sacred Om Emblem Badge
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(GoldLight, GoldPrimary)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "ॐ",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = TempleBrown
                        )
                    }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (isHindi) "भक्ति दर्शन" else "Bhakti Darshan",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = PureWhite,
                                modifier = Modifier.testTag("app_title_text")
                            )
                            Text(
                                text = " 🙏",
                                fontSize = 18.sp
                            )
                        }
                        Text(
                            text = if (isHindi) "“हर सुबह भगवान के साथ”" else "“Every morning with the Divine”",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = GoldLight
                        )
                    }
                }

                // Header Actions: Language switch, Reminder bell, Search
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Language Switch Pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(PureWhite.copy(alpha = 0.2f))
                            .clickable { onLanguageToggle() }
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isHindi) "EN" else "हिन्दी",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                    }

                    IconButton(
                        onClick = onSearchClick,
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("header_search_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = if (isHindi) "खोजें" else "Search",
                            tint = PureWhite
                        )
                    }

                    IconButton(
                        onClick = onNotificationClick,
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("header_notification_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = if (isHindi) "पूजा रिमाइंडर" else "Puja Reminder",
                            tint = GoldAccent
                        )
                    }
                }
            }
        }
    }
}
