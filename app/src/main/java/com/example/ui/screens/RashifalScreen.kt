package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Rashi
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamCardBorder
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.CreamSurfaceVariant
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.TempleBrown
import com.example.ui.theme.TempleTextDark
import com.example.ui.theme.TempleTextSecondary
import com.example.ui.theme.VermilionKumkum

@Composable
fun RashifalScreen(
    rashifalList: List<Rashi>,
    selectedRashi: Rashi,
    isHindi: Boolean,
    onRashiSelected: (Rashi) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .testTag("rashifal_screen")
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(SaffronPrimary.copy(alpha = 0.15f), CreamBackground)
                    )
                )
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Column {
                Text(
                    text = if (isHindi) "आज का राशिफल ♈" else "Today's Rashifal ♈",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TempleBrown
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if (isHindi) "अपनी राशि चुनें और जानें आज का दिन, शुभ रंग व मंत्र" else "Select your Zodiac sign to see daily astrological guidance",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TempleTextSecondary
                )
            }
        }

        // Horizontal 12 Rashi Selector Carousel
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(rashifalList, key = { it.id }) { rashi ->
                val isSelected = rashi.id == selectedRashi.id

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onRashiSelected(rashi) }
                        .background(if (isSelected) SaffronPrimary else CreamSurface)
                        .border(1.dp, if (isSelected) SaffronPrimary else CreamCardBorder, RoundedCornerShape(16.dp))
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                        .testTag("rashi_chip_${rashi.id}")
                ) {
                    Text(
                        text = rashi.symbol,
                        fontSize = 26.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (isHindi) rashi.name else rashi.nameEn,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) PureWhite else TempleTextDark
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Selected Rashi Detailed Report
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Selected Sign Hero Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldPrimary.copy(alpha = 0.7f)),
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
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        listOf(GoldLight, SaffronOrange)
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = selectedRashi.symbol,
                                fontSize = 36.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = if (isHindi) "राशि: ${selectedRashi.name} (${selectedRashi.nameEn})" else "Rashi: ${selectedRashi.nameEn} (${selectedRashi.name})",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = TempleBrown
                            )
                            Text(
                                text = if (isHindi) "स्वामी ग्रह: ${selectedRashi.planet}" else "Ruling Planet: ${selectedRashi.planetEn}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = SaffronPrimary
                            )
                        }
                    }
                }
            }

            // Overview Section
            item {
                RashifalSectionCard(
                    title = if (isHindi) "आज का दिन 🌟" else "Today's Day 🌟",
                    content = if (isHindi) selectedRashi.overview else selectedRashi.overviewEn
                )
            }

            // 4 Pillars: Love, Career, Finance, Health
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    RashifalSectionCard(
                        title = if (isHindi) "प्रेम एवं संबंध ❤️" else "Love & Relationships ❤️",
                        content = selectedRashi.love
                    )
                    RashifalSectionCard(
                        title = if (isHindi) "करियर एवं व्यापार 💼" else "Career & Business 💼",
                        content = selectedRashi.career
                    )
                    RashifalSectionCard(
                        title = if (isHindi) "धन एवं आर्थिक स्थिति 💰" else "Finance & Wealth 💰",
                        content = selectedRashi.finance
                    )
                    RashifalSectionCard(
                        title = if (isHindi) "स्वास्थ्य एवं ऊर्जा 🌿" else "Health & Wellness 🌿",
                        content = selectedRashi.health
                    )
                }
            }

            // Lucky Highlights Grid
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurfaceVariant),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldLight)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = if (isHindi) "शुभ संकेत एवं अंक 🔮" else "Auspicious Attributes 🔮",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SaffronPrimary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = if (isHindi) "शुभ रंग:" else "Lucky Color:", style = MaterialTheme.typography.labelMedium, color = TempleTextSecondary)
                                Text(text = selectedRashi.luckyColor, fontWeight = FontWeight.Bold, color = TempleTextDark)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = if (isHindi) "शुभ अंक:" else "Lucky Number:", style = MaterialTheme.typography.labelMedium, color = TempleTextSecondary)
                                Text(text = selectedRashi.luckyNumber, fontWeight = FontWeight.Bold, color = TempleTextDark)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = if (isHindi) "शुभ दिशा:" else "Lucky Direction:", style = MaterialTheme.typography.labelMedium, color = TempleTextSecondary)
                                Text(text = selectedRashi.luckyDirection, fontWeight = FontWeight.Bold, color = TempleTextDark)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = if (isHindi) "आज का पावन मंत्र:" else "Today's Mantra:", style = MaterialTheme.typography.labelMedium, color = TempleTextSecondary)
                                Text(text = selectedRashi.dailyMantra, fontWeight = FontWeight.Bold, color = VermilionKumkum)
                            }
                        }
                    }
                }
            }

            // Disclaimer
            item {
                Text(
                    text = if (isHindi) "सूचना: यह राशिफल ज्योतिषीय गणना एवं आध्यात्मिक मार्गदर्शन के उद्देश्य से प्रस्तुत है।" else "Note: This horoscope is provided for spiritual guidance and entertainment.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TempleTextSecondary.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun RashifalSectionCard(
    title: String,
    content: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CreamSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, CreamCardBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = TempleBrown
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = TempleTextDark,
                lineHeight = 22.sp
            )
        }
    }
}
