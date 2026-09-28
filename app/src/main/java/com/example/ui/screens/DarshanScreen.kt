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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Deity
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
import com.example.ui.theme.VermilionKumkum

@Composable
fun DarshanScreen(
    deities: List<Deity>,
    isHindi: Boolean,
    isFavorite: (String) -> Boolean,
    onToggleFavorite: (Deity) -> Unit,
    onSelectDeity: (Deity) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .testTag("darshan_screen")
    ) {
        // Top banner header
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isHindi) "भगवान के दर्शन 🛕" else "Sacred Deity Darshan 🛕",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = TempleBrown
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if (isHindi) "12 पावन देवी-देवताओं के दर्शन, मंत्र एवं आरती" else "Darshan, mantras & aarti of 12 sacred deities",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TempleTextSecondary
                )
            }
        }

        // 2-Column Grid of Deity Cards
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(deities, key = { it.id }) { deity ->
                val fav = isFavorite(deity.id)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onSelectDeity(deity) }
                        .testTag("deity_card_${deity.id}"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, CreamCardBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        // Favorite Heart Button (Top Right)
                        IconButton(
                            onClick = { onToggleFavorite(deity) },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(4.dp)
                                .size(36.dp)
                                .testTag("favorite_button_${deity.id}")
                        ) {
                            Icon(
                                imageVector = if (fav) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (fav) VermilionKumkum else TempleTextSecondary.copy(alpha = 0.6f),
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(6.dp))

                            // Sacred Icon Avatar Circle
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.radialGradient(
                                            listOf(GoldLight, Color(deity.primaryColor))
                                        )
                                    )
                                    .border(2.dp, GoldPrimary, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = deity.symbol,
                                    fontSize = 36.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Name
                            Text(
                                text = if (isHindi) deity.name else deity.nameEn,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TempleBrown,
                                textAlign = TextAlign.Center
                            )

                            // Title
                            Text(
                                text = if (isHindi) deity.title else deity.titleEn,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = SaffronPrimary,
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            // Temple Location
                            Text(
                                text = if (isHindi) deity.templeLocation else deity.templeLocationEn,
                                fontSize = 11.sp,
                                color = TempleTextSecondary,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // “दर्शन करें” Button
                            Button(
                                onClick = { onSelectDeity(deity) },
                                colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(38.dp)
                            ) {
                                Text(
                                    text = if (isHindi) "दर्शन करें 🙏" else "Darshan 🙏",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PureWhite
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
