package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.ui.components.InteractiveAartiThali
import com.example.ui.components.RadiantAuraBackground
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DeityDetailScreen(
    deity: Deity,
    isHindi: Boolean,
    isFav: Boolean,
    onBackClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onPlayAudio: () -> Unit,
    onTempleBell: () -> Unit,
    onShankh: () -> Unit,
    onJaapTapped: () -> Unit,
    onShare: (String) -> Unit
) {
    var jaapCount by remember { mutableIntStateOf(0) }
    var showFlowerShower by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .testTag("deity_detail_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // 1. Top Sacred Sanctuary Banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(340.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(deity.primaryColor),
                                    SaffronPrimary,
                                    CreamBackground
                                )
                            )
                        )
                ) {
                    // Animated radiant aura background
                    RadiantAuraBackground(
                        modifier = Modifier.fillMaxSize(),
                        baseColor = Color(deity.accentColor)
                    )

                    // Top navigation bar icons (Back, Favorite, Share)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(PureWhite.copy(alpha = 0.25f))
                                .testTag("deity_detail_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = PureWhite
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            IconButton(
                                onClick = onToggleFavorite,
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(PureWhite.copy(alpha = 0.25f))
                                    .testTag("deity_detail_favorite_button")
                            ) {
                                Icon(
                                    imageVector = if (isFav) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = if (isFav) VermilionKumkum else PureWhite
                                )
                            }

                            IconButton(
                                onClick = {
                                    val shareText = "🙏 भगवान ${deity.name} के दिव्य दर्शन एवं मंत्र: “${deity.mantra}”\n\nभक्ति दर्शन ऐप पर आज ही दर्शन करें।"
                                    onShare(shareText)
                                },
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(PureWhite.copy(alpha = 0.25f))
                                    .testTag("deity_detail_share_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Share",
                                    tint = PureWhite
                                )
                            }
                        }
                    }

                    // Large Deity Deity Symbol Avatar (Emblem of Sanctum)
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(130.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        listOf(GoldLight, PureWhite, Color(deity.primaryColor))
                                    )
                                )
                                .border(4.dp, GoldPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = deity.symbol,
                                fontSize = 66.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = if (isHindi) deity.name else deity.nameEn,
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = PureWhite
                        )

                        Text(
                            text = if (isHindi) "${deity.title} • ${deity.templeLocation}" else "${deity.titleEn} • ${deity.templeLocationEn}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = GoldLight
                        )
                    }
                }
            }

            // 2. Action row: Play Devotional Audio, Bell Chime, Shankh Sound, Flower shower
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Play Devotional Audio
                    Button(
                        onClick = onPlayAudio,
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1.3f)
                            .height(48.dp)
                            .testTag("deity_play_audio_button")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = PureWhite
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isHindi) "भजन / मंत्र सुनें" else "Play Chant",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = PureWhite
                            )
                        }
                    }

                    // Bell sound
                    Button(
                        onClick = onTempleBell,
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(0.9f)
                            .height(48.dp)
                            .testTag("deity_bell_button")
                    ) {
                        Text(text = "🔔 घंटी", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TempleBrown)
                    }

                    // Flower Shower
                    Button(
                        onClick = {
                            showFlowerShower = true
                            scope.launch {
                                delay(2500)
                                showFlowerShower = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CreamSurfaceVariant),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("deity_flower_shower_button")
                    ) {
                        Text(text = "🌸 पुष्प", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SaffronPrimary)
                    }
                }
            }

            // 3. Flower shower celebratory banner
            item {
                AnimatedVisibility(
                    visible = showFlowerShower,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 6.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(GoldLight.copy(alpha = 0.5f))
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isHindi) "🌸 भगवान के पावन चरणों में पुष्प वर्षा अर्पित की गई 🙏" else "🌸 Flowers offered at the divine feet 🙏",
                            fontWeight = FontWeight.Bold,
                            color = TempleBrown,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // 4. Sacred Mantra & Meaning Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldPrimary.copy(alpha = 0.7f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isHindi) "॥ पावन मूल मंत्र ॥" else "॥ Sacred Mool Mantra ॥",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SaffronPrimary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Large Mantra Text
                        Text(
                            text = deity.mantra,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = VermilionKumkum,
                            textAlign = TextAlign.Center,
                            lineHeight = 30.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (isHindi) deity.mantraMeaning else deity.mantraMeaningEn,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TempleTextDark,
                            textAlign = TextAlign.Center,
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            // 5. Interactive “ॐ” Jaap Counter (Requirement: “ॐ” button)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurfaceVariant),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CreamCardBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = if (isHindi) "दैनिक मंत्र जप माला 📿" else "Daily Mantra Mala 📿",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TempleBrown
                                )
                                Text(
                                    text = if (isHindi) "ॐ बटन दबाकर जप करें" else "Tap Om button to chant",
                                    fontSize = 12.sp,
                                    color = TempleTextSecondary
                                )
                            }

                            // Reset button
                            IconButton(
                                onClick = { jaapCount = 0 },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Reset",
                                    tint = TempleTextSecondary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Counter display
                        Text(
                            text = "$jaapCount",
                            fontSize = 44.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = SaffronPrimary
                        )

                        Text(
                            text = if (isHindi) "जप पूर्ण" else "Chants Completed",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TempleTextSecondary
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Large Interactive “ॐ” Button with haptic touch
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        listOf(GoldLight, GoldPrimary, SaffronPrimary)
                                    )
                                )
                                .border(3.dp, PureWhite, CircleShape)
                                .clickable {
                                    jaapCount++
                                    onJaapTapped()
                                }
                                .testTag("om_jaap_counter_button"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "ॐ",
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                color = TempleBrown
                            )
                        }
                    }
                }
            }

            // 6. Interactive Aarti Thali Section
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CreamCardBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isHindi) "पावन आरती थाली 🪔" else "Sacred Aarti Thali 🪔",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TempleBrown
                        )
                        Text(
                            text = if (isHindi) "थाली को स्पर्श कर घुमाएं और आरती करें" else "Touch and rotate the thali for live virtual aarti",
                            fontSize = 12.sp,
                            color = TempleTextSecondary,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        InteractiveAartiThali(
                            modifier = Modifier.size(200.dp),
                            onRotate = {
                                onTempleBell()
                            }
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Aarti Verse Snippet
                        Text(
                            text = deity.aartiSnippet,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = TempleTextDark,
                            textAlign = TextAlign.Center,
                            lineHeight = 22.sp
                        )
                    }
                }
            }
        }
    }
}
