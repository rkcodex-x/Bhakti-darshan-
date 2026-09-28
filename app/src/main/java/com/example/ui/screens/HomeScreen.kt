package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Deity
import com.example.data.model.Suvichar
import com.example.ui.Screen
import com.example.ui.components.RadiantAuraBackground
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamCardBorder
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.CreamSurfaceVariant
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SaffronLight
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.TempleBrown
import com.example.ui.theme.TempleTextDark
import com.example.ui.theme.TempleTextSecondary
import com.example.ui.theme.VermilionKumkum

data class HomeGridItem(
    val titleHindi: String,
    val titleEn: String,
    val iconEmoji: String,
    val screen: Screen,
    val subtitleHindi: String,
    val subtitleEn: String,
    val testTag: String
)

@Composable
fun HomeScreen(
    isHindi: Boolean,
    todayDeity: Deity,
    dailyQuote: Suvichar,
    todayDate: String,
    onNavigateToScreen: (Screen) -> Unit,
    onSelectDeity: (Deity) -> Unit,
    onStartPujaClick: () -> Unit,
    onShareQuote: (String) -> Unit
) {
    val context = LocalContext.current

    val gridItems = listOf(
        HomeGridItem("भजन", "Bhajan", "🎵", Screen.BhajanList, "मधुर अमृतवाणी", "Devotional Hymns", "grid_bhajan"),
        HomeGridItem("आज का राशिफल", "Rashifal", "♈", Screen.Rashifal, "12 राशियां", "Daily Horoscope", "grid_rashifal"),
        HomeGridItem("आज का पंचांग", "Panchang", "📅", Screen.Panchang, "शुभ मुहूर्त व तिथि", "Daily Panchang", "grid_panchang"),
        HomeGridItem("भगवान के दर्शन", "Darshan", "🛕", Screen.DarshanGrid, "12 पावन रूप", "Sacred Deities", "grid_darshan"),
        HomeGridItem("सुबह की पूजा", "Subah Ki Puja", "🪔", Screen.SubahKiPuja, "10-20 मिनट में पूर्ण", "Guided Worship", "grid_puja"),
        HomeGridItem("सुविचार", "Suvichar", "💡", Screen.Suvichar, "दैनिक प्रेरक विचार", "Divine Wisdom", "grid_suvichar")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .testTag("home_screen_list"),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp,
            bottom = 96.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Top Greeting Card: शुभ प्रभात 🙏 & Today's Date
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("morning_greeting_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, CreamCardBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (isHindi) "शुभ प्रभात" else "Shubh Prabhat",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = SaffronPrimary
                            )
                            Text(
                                text = " 🙏",
                                fontSize = 20.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = todayDate,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = TempleTextSecondary
                        )
                    }

                    // Sacred Diya mini badge
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(GoldLight, SaffronOrange)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🪔",
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }

        // 2. Large “आज के दर्शन” Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .clickable { onSelectDeity(todayDeity) }
                    .testTag("aaj_ke_darshan_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldPrimary.copy(alpha = 0.6f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    // Subtle golden aura background inside card
                    RadiantAuraBackground(
                        modifier = Modifier.matchParentSize(),
                        baseColor = Color(todayDeity.primaryColor)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Badge: "आज के दर्शन"
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(SaffronPrimary)
                                .padding(horizontal = 14.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = if (isHindi) "आज के दर्शन 🛕" else "Today's Sacred Darshan 🛕",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = PureWhite
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Large Deity Avatar Icon with radiant ring
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        listOf(GoldLight, Color(todayDeity.primaryColor))
                                    )
                                )
                                .border(3.dp, GoldPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = todayDeity.symbol,
                                fontSize = 48.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Deity Name
                        Text(
                            text = if (isHindi) todayDeity.name else todayDeity.nameEn,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = TempleBrown
                        )

                        Text(
                            text = if (isHindi) todayDeity.title else todayDeity.titleEn,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = SaffronPrimary
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Mantra
                        Text(
                            text = todayDeity.mantra,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = VermilionKumkum,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // “दर्शन करें →” button
                        Button(
                            onClick = { onSelectDeity(todayDeity) },
                            colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.75f)
                                .height(46.dp)
                                .testTag("darshan_kare_button")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = if (isHindi) "दर्शन करें" else "Take Darshan",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PureWhite
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = PureWhite,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. Quick Subah Ki Puja Guided Banner CTA
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onStartPujaClick() }
                    .testTag("subah_ki_puja_banner"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SaffronPrimary),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(SaffronPrimary, SaffronOrange)
                            )
                        )
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "🪔 ",
                                fontSize = 20.sp
                            )
                            Text(
                                text = if (isHindi) "सुबह की पावन पूजा" else "Morning Guided Worship",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = PureWhite
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (isHindi) "सिर्फ 10–20 मिनट में अपनी सुबह की पूजा पूरी करें।" else "Complete your sacred morning puja in 10-20 mins.",
                            style = MaterialTheme.typography.bodySmall,
                            color = GoldLight
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(GoldPrimary)
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = if (isHindi) "शुरू करें →" else "Start →",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TempleBrown
                        )
                    }
                }
            }
        }

        // 4. Section: 2-Column Grid of 6 Key Features
        item {
            Text(
                text = if (isHindi) "भक्ति सेवाएँ" else "Devotional Services",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TempleBrown,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
        }

        // 2-column grid rows
        val chunked = gridItems.chunked(2)
        items(chunked.size) { index ->
            val pair = chunked[index]
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                pair.forEach { item ->
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(115.dp)
                            .clickable { onNavigateToScreen(item.screen) }
                            .testTag(item.testTag),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = CreamSurface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CreamCardBorder),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(SaffronPrimary.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item.iconEmoji,
                                    fontSize = 20.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (isHindi) item.titleHindi else item.titleEn,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = TempleTextDark
                            )
                            Text(
                                text = if (isHindi) item.subtitleHindi else item.subtitleEn,
                                style = MaterialTheme.typography.labelSmall,
                                color = TempleTextSecondary,
                                maxLines = 1
                            )
                        }
                    }
                }
                if (pair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        // 5. Section: "आज का भक्ति संदेश" (Spiritual Quote Card)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("aaj_ka_bhakti_sandesh_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldLight),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "💛 ", fontSize = 18.sp)
                            Text(
                                text = if (isHindi) "आज का भक्ति संदेश" else "Today's Devotional Wisdom",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TempleBrown
                            )
                        }

                        IconButton(
                            onClick = {
                                val quoteText = "${dailyQuote.quote}\n- ${dailyQuote.authorOrSource}\n\n🙏 भक्ति दर्शन ऐप पर आज के दर्शन करें।"
                                onShareQuote(quoteText)
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = SaffronPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "“${if (isHindi) dailyQuote.quote else dailyQuote.quoteEn}”",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = TempleTextDark,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "— ${if (isHindi) dailyQuote.authorOrSource else dailyQuote.authorOrSourceEn}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = SaffronPrimary,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }

        // 6. Peaceful Footer Verse: “हर सुबह भगवान के नाम से शुरू करें।”
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isHindi) "“हर सुबह भगवान के नाम से शुरू करें।” 🙏" else "“Begin every morning with the Name of God.” 🙏",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TempleTextSecondary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
