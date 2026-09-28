package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Panchang
import com.example.data.model.ShubhMuhurat
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamCardBorder
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.CreamSurfaceVariant
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.HolyTulsiGreen
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.TempleBrown
import com.example.ui.theme.TempleTextDark
import com.example.ui.theme.TempleTextSecondary
import com.example.ui.theme.VermilionKumkum

@Composable
fun PanchangScreen(
    panchang: Panchang,
    isHindi: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .testTag("panchang_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Header Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldPrimary.copy(alpha = 0.6f)),
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
                        Column {
                            Text(
                                text = if (isHindi) "आज का पंचांग 📅" else "Today's Panchang 📅",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = TempleBrown
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (isHindi) panchang.dateDisplay else panchang.dateDisplayEn,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = SaffronPrimary
                            )
                        }

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
                            Text(text = "🕉️", fontSize = 24.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "${if (isHindi) "विक्रम संवत" else "Vikram Samvat"}: ${panchang.vikramSamvat} • ${panchang.ritu}",
                        fontSize = 12.sp,
                        color = TempleTextSecondary
                    )
                }
            }
        }

        // 2. Solar & Lunar Timings (सूर्योदय, सूर्यास्त, चंद्रोदय)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldLight)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "☀️", fontSize = 26.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = if (isHindi) "सूर्योदय" else "Sunrise", fontSize = 11.sp, color = TempleTextSecondary)
                        Text(text = panchang.sunrise, fontWeight = FontWeight.Bold, color = TempleTextDark)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "🌇", fontSize = 26.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = if (isHindi) "सूर्यास्त" else "Sunset", fontSize = 11.sp, color = TempleTextSecondary)
                        Text(text = panchang.sunset, fontWeight = FontWeight.Bold, color = TempleTextDark)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "🌙", fontSize = 26.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = if (isHindi) "चंद्रोदय" else "Moonrise", fontSize = 11.sp, color = TempleTextSecondary)
                        Text(text = panchang.moonrise, fontWeight = FontWeight.Bold, color = TempleTextDark)
                    }
                }
            }
        }

        // 3. Core Panchang Anga Elements (तिथि, वार, नक्षत्र, योग, करण)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, CreamCardBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = if (isHindi) "पंचांग के मुख्य अंग" else "Core Panchang Elements",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TempleBrown
                    )

                    PanchangRowItem(if (isHindi) "तिथि" else "Tithi", if (isHindi) panchang.tithi else panchang.tithiEn)
                    PanchangRowItem(if (isHindi) "पक्ष" else "Paksha", if (isHindi) panchang.paksha else panchang.pakshaEn)
                    PanchangRowItem(if (isHindi) "वार" else "Day", if (isHindi) panchang.varDay else panchang.varDayEn)
                    PanchangRowItem(if (isHindi) "नक्षत्र" else "Nakshatra", if (isHindi) panchang.nakshatra else panchang.nakshatraEn)
                    PanchangRowItem(if (isHindi) "योग" else "Yoga", if (isHindi) panchang.yoga else panchang.yogaEn)
                    PanchangRowItem(if (isHindi) "करण" else "Karana", if (isHindi) panchang.karana else panchang.karanaEn)
                    PanchangRowItem(if (isHindi) "चंद्र राशि" else "Moon Sign", if (isHindi) panchang.moonSign else panchang.moonSignEn)
                    PanchangRowItem(if (isHindi) "सूर्य राशि" else "Sun Sign", if (isHindi) panchang.sunSign else panchang.sunSignEn)
                }
            }
        }

        // 4. Section: आज के शुभ मुहूर्त
        item {
            Text(
                text = if (isHindi) "आज के शुभ एवं पावन मुहूर्त 🪔" else "Auspicious Muhurats Today 🪔",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TempleBrown,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // Muhurat Cards
        items(panchang.muhurats) { muhurat ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (muhurat.isAuspicious) CreamSurface else Color(0xFFFFEBEE)
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (muhurat.isAuspicious) CreamCardBorder else Color(0xFFFFCDD2)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (muhurat.isAuspicious) "🟢 " else "🔴 ",
                                fontSize = 12.sp
                            )
                            Text(
                                text = if (isHindi) muhurat.name else muhurat.nameEn,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (muhurat.isAuspicious) TempleBrown else VermilionKumkum
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (isHindi) muhurat.description else muhurat.descriptionEn,
                            style = MaterialTheme.typography.bodySmall,
                            color = TempleTextSecondary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (muhurat.isAuspicious) SaffronPrimary.copy(alpha = 0.12f) else VermilionKumkum.copy(alpha = 0.12f))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = muhurat.timeRange,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (muhurat.isAuspicious) SaffronPrimary else VermilionKumkum
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PanchangRowItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TempleTextSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = TempleTextDark
        )
    }
}
