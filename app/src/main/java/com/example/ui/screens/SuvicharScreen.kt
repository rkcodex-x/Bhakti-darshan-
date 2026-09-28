package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Suvichar
import com.example.ui.components.RadiantAuraBackground
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
fun SuvicharScreen(
    suvichars: List<Suvichar>,
    currentIndex: Int,
    isHindi: Boolean,
    isFav: Boolean,
    onNextSuvichar: () -> Unit,
    onToggleFavorite: (Suvichar) -> Unit,
    onShare: (String) -> Unit
) {
    val suvichar = suvichars.getOrNull(currentIndex) ?: suvichars[0]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .testTag("suvichar_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 96.dp)
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
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Column {
                    Text(
                        text = if (isHindi) "आज का सुविचार 💛" else "Daily Suvichar 💛",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = TempleBrown
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (isHindi) "श्रीमद्भगवद्गीता, संतों एवं महापुरुषों के पावन उपदेश" else "Sacred words of Gita, saints and sages",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TempleTextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Quote Card
            AnimatedContent(
                targetState = currentIndex,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "suvicharAnim",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 20.dp)
            ) { _ ->
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    border = androidx.compose.foundation.BorderStroke(2.dp, GoldPrimary.copy(alpha = 0.7f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        RadiantAuraBackground(
                            modifier = Modifier.matchParentSize(),
                            baseColor = GoldAccent
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Top Tag & Number
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(SaffronPrimary)
                                        .padding(horizontal = 12.dp, vertical = 5.dp)
                                ) {
                                    Text(
                                        text = suvichar.contextTag,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PureWhite
                                    )
                                }

                                Text(
                                    text = "${currentIndex + 1} / ${suvichars.size}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TempleTextSecondary
                                )
                            }

                            // Center Spiritual Quote
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "❝",
                                    fontSize = 48.sp,
                                    color = SaffronOrange,
                                    lineHeight = 40.sp
                                )

                                Text(
                                    text = if (isHindi) suvichar.quote else suvichar.quoteEn,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TempleTextDark,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 32.sp
                                )

                                Spacer(modifier = Modifier.height(18.dp))

                                Text(
                                    text = "— ${if (isHindi) suvichar.authorOrSource else suvichar.authorOrSourceEn}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = SaffronPrimary,
                                    textAlign = TextAlign.Center
                                )
                            }

                            // Bottom Sub-caption
                            Text(
                                text = if (isHindi) "“सद्भाव और भक्ति ही जीवन का सच्चा प्रकाश है”" else "“Righteousness & devotion illuminate the soul”",
                                style = MaterialTheme.typography.labelMedium,
                                color = TempleTextSecondary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Action Buttons: Favorite, Share, Next Suvichar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Favorite
                OutlinedButton(
                    onClick = { onToggleFavorite(suvichar) },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Icon(
                        imageVector = if (isFav) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFav) VermilionKumkum else SaffronPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = if (isHindi) "पसंद" else "Favorite", color = TempleBrown, fontWeight = FontWeight.Bold)
                }

                // Share
                OutlinedButton(
                    onClick = {
                        val shareText = "“${suvichar.quote}”\n— ${suvichar.authorOrSource}\n\n🙏 भक्ति दर्शन ऐप पर आज का सुविचार पढ़ें।"
                        onShare(shareText)
                    },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = SaffronPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = if (isHindi) "शेयर" else "Share", color = TempleBrown, fontWeight = FontWeight.Bold)
                }

                // Next Quote (अगला सुविचार)
                Button(
                    onClick = onNextSuvichar,
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .weight(1.3f)
                        .height(48.dp)
                        .testTag("next_suvichar_button")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isHindi) "अगला" else "Next",
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Spacer(modifier = Modifier.width(4.dp))
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
