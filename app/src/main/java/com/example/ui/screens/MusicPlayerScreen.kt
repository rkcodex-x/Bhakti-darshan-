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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.PlayerState
import com.example.ui.components.RadiantAuraBackground
import com.example.ui.theme.CreamBackground
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

@Composable
fun MusicPlayerScreen(
    playerState: PlayerState,
    isHindi: Boolean,
    isFav: Boolean,
    onBackClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPrevClick: () -> Unit,
    onSeekTo: (Int) -> Unit,
    onToggleRepeat: () -> Unit,
    onToggleShuffle: () -> Unit,
    onToggleFavorite: () -> Unit,
    onTempleBell: () -> Unit,
    onShankh: () -> Unit,
    onShare: (String) -> Unit
) {
    val bhajan = playerState.currentBhajan
    var showLyrics by remember { mutableStateOf(false) }
    var isDownloaded by remember { mutableStateOf(false) }

    fun formatTime(seconds: Int): String {
        val m = seconds / 60
        val s = seconds % 60
        return "%02d:%02d".format(m, s)
    }

    if (bhajan == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CreamBackground),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "कोई भजन चयनित नहीं है।", color = TempleTextSecondary)
        }
        return
    }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            SaffronPrimary,
            SaffronOrange,
            TempleBrown
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("music_player_screen")
    ) {
        // Subtle animated spiritual aura
        RadiantAuraBackground(
            modifier = Modifier.fillMaxSize(),
            baseColor = GoldPrimary
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // 1. Top Header with Back, Title, Share
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(PureWhite.copy(alpha = 0.2f))
                        .testTag("player_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = PureWhite
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (isHindi) "भक्ति संगीत" else "Devotional Player",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                    Text(
                        text = if (isHindi) bhajan.category.hindiName else bhajan.category.englishName,
                        style = MaterialTheme.typography.labelSmall,
                        color = GoldLight
                    )
                }

                IconButton(
                    onClick = {
                        val shareText = "🙏 भक्ति दर्शन पर सुनें पावन भजन: “${bhajan.title}”\nहर सुबह भगवान के साथ।"
                        onShare(shareText)
                    },
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(PureWhite.copy(alpha = 0.2f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = PureWhite
                    )
                }
            }

            // 2. Center: Large Devotional Artwork / Rotating Om Diya Aura
            if (!showLyrics) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(GoldLight, SaffronOrange, TempleBrown)
                                )
                            )
                            .border(4.dp, GoldPrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🪈",
                            fontSize = 80.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = if (isHindi) bhajan.title else bhajan.titleEn,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = PureWhite,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${if (isHindi) bhajan.deity else bhajan.deityEn} • ${bhajan.artistOrTradition}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = GoldLight,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                // Expanded Devanagari Lyrics Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite.copy(alpha = 0.92f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "॥ बोल (Lyrics) ॥",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SaffronPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = bhajan.lyrics,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = TempleTextDark,
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp
                        )
                    }
                }
            }

            // 3. Audio Progress Bar Slider & Timers
            Column(modifier = Modifier.fillMaxWidth()) {
                val current = playerState.currentPositionSeconds.toFloat()
                val total = playerState.totalDurationSeconds.toFloat().coerceAtLeast(1f)

                Slider(
                    value = current,
                    onValueChange = { onSeekTo(it.toInt()) },
                    valueRange = 0f..total,
                    colors = SliderDefaults.colors(
                        thumbColor = GoldPrimary,
                        activeTrackColor = GoldPrimary,
                        inactiveTrackColor = PureWhite.copy(alpha = 0.35f)
                    ),
                    modifier = Modifier.testTag("audio_slider")
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formatTime(playerState.currentPositionSeconds),
                        fontSize = 12.sp,
                        color = PureWhite
                    )
                    Text(
                        text = bhajan.durationText,
                        fontSize = 12.sp,
                        color = GoldLight
                    )
                }
            }

            // 4. Main Playback Controls: Shuffle, Prev, Play/Pause, Next, Repeat
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onToggleShuffle,
                    modifier = Modifier.size(42.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Shuffle,
                        contentDescription = "Shuffle",
                        tint = if (playerState.isShuffleEnabled) GoldAccent else PureWhite.copy(alpha = 0.6f)
                    )
                }

                IconButton(
                    onClick = onPrevClick,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Previous",
                        tint = PureWhite,
                        modifier = Modifier.size(34.dp)
                    )
                }

                // Center Grand Play/Pause Button
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                        .background(GoldPrimary)
                        .clickable { onPlayPauseClick() }
                        .testTag("player_play_pause_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (playerState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (playerState.isPlaying) "Pause" else "Play",
                        tint = TempleBrown,
                        modifier = Modifier.size(38.dp)
                    )
                }

                IconButton(
                    onClick = onNextClick,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Next",
                        tint = PureWhite,
                        modifier = Modifier.size(34.dp)
                    )
                }

                IconButton(
                    onClick = onToggleRepeat,
                    modifier = Modifier.size(42.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Repeat,
                        contentDescription = "Repeat",
                        tint = if (playerState.isRepeatEnabled) GoldAccent else PureWhite.copy(alpha = 0.6f)
                    )
                }
            }

            // 5. Additional Devotional & Feature Buttons: Favorite, Bell, Shankh, Download, Lyrics
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Favorite
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        imageVector = if (isFav) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFav) VermilionKumkum else PureWhite
                    )
                }

                // Temple Bell sound
                IconButton(onClick = onTempleBell) {
                    Text(text = "🔔", fontSize = 22.sp)
                }

                // Shankh sound
                IconButton(onClick = onShankh) {
                    Text(text = "🐚", fontSize = 22.sp)
                }

                // Download (Offline toggle)
                IconButton(onClick = { isDownloaded = !isDownloaded }) {
                    Icon(
                        imageVector = if (isDownloaded) Icons.Default.DownloadDone else Icons.Default.Download,
                        contentDescription = "Download",
                        tint = if (isDownloaded) GoldAccent else PureWhite
                    )
                }

                // Lyrics toggle
                IconButton(onClick = { showLyrics = !showLyrics }) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = "Lyrics",
                        tint = if (showLyrics) GoldAccent else PureWhite
                    )
                }
            }
        }
    }
}
