package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PujaStep
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

@Composable
fun SubahKiPujaScreen(
    steps: List<PujaStep>,
    currentStepIndex: Int,
    jaapCount: Int,
    isCompleted: Boolean,
    isHindi: Boolean,
    onNextStep: () -> Unit,
    onPrevStep: () -> Unit,
    onIncrementJaap: () -> Unit,
    onResetPuja: () -> Unit,
    onTempleBell: () -> Unit,
    onShankh: () -> Unit,
    onBack: () -> Unit,
    onShareBlessing: (String) -> Unit
) {
    val currentStep = steps.getOrNull(currentStepIndex) ?: steps[0]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .testTag("subah_ki_puja_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 96.dp)
        ) {
            // Top Navigation & Title Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(SaffronPrimary.copy(alpha = 0.2f), CreamBackground)
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.size(38.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TempleBrown
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = if (isHindi) "सुबह की पूजा 🪔" else "Morning Puja 🪔",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = TempleBrown
                        )
                        Text(
                            text = if (isHindi) "सिर्फ 10–20 मिनट में अपनी सुबह की पूजा पूरी करें" else "Complete your sacred morning worship in 10-20 mins",
                            style = MaterialTheme.typography.bodySmall,
                            color = TempleTextSecondary
                        )
                    }
                }

                // Bell sound quick trigger
                IconButton(
                    onClick = onTempleBell,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(GoldPrimary)
                ) {
                    Text(text = "🔔", fontSize = 18.sp)
                }
            }

            if (!isCompleted) {
                // Progress Indicator Bar: 1 → 2 → 3 → 4 → 5
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    steps.forEachIndexed { idx, step ->
                        val isCurrent = idx == currentStepIndex
                        val isDone = idx < currentStepIndex

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            isDone -> GoldPrimary
                                            isCurrent -> SaffronPrimary
                                            else -> CreamSurfaceVariant
                                        }
                                    )
                                    .border(
                                        1.5.dp,
                                        if (isCurrent) SaffronOrange else CreamCardBorder,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isDone) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = PureWhite,
                                        modifier = Modifier.size(18.dp)
                                    )
                                } else {
                                    Text(
                                        text = "${idx + 1}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isCurrent) PureWhite else TempleTextSecondary
                                    )
                                }
                            }

                            Text(
                                text = step.iconEmoji,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }

                        if (idx < steps.size - 1) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(2.dp)
                                    .padding(horizontal = 4.dp)
                                    .background(if (idx < currentStepIndex) GoldPrimary else CreamCardBorder)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Active Step Card
                AnimatedContent(
                    targetState = currentStepIndex,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "stepAnimation"
                ) { targetIdx ->
                    val step = steps[targetIdx]

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = CreamSurface),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldPrimary.copy(alpha = 0.7f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Step Badge
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SaffronPrimary.copy(alpha = 0.12f))
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = if (isHindi) "चरण ${step.stepNumber} / 5" else "Step ${step.stepNumber} of 5",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SaffronPrimary
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "${step.iconEmoji} ${if (isHindi) step.title else step.titleEn}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = TempleBrown,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = if (isHindi) step.subtitle else step.subtitleEn,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = SaffronOrange,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = if (isHindi) step.instruction else step.instructionEn,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TempleTextDark,
                                textAlign = TextAlign.Center,
                                lineHeight = 22.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Step specific interactive component:
                            when (step.stepNumber) {
                                1 -> {
                                    // Step 1: Darshan & Meditation
                                    Box(
                                        modifier = Modifier
                                            .size(120.dp)
                                            .clip(CircleShape)
                                            .background(
                                                Brush.radialGradient(listOf(GoldLight, SaffronOrange))
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "🕉️", fontSize = 54.sp)
                                    }
                                }
                                2 -> {
                                    // Step 2: Sacred Mantra Jaap with Mala Counter
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "$jaapCount / 11",
                                            fontSize = 36.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = SaffronPrimary
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Box(
                                            modifier = Modifier
                                                .size(80.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    Brush.radialGradient(listOf(GoldLight, GoldPrimary))
                                                )
                                                .border(2.dp, SaffronPrimary, CircleShape)
                                                .clickable { onIncrementJaap() }
                                                .testTag("puja_om_jaap_button"),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(text = "ॐ", fontSize = 38.sp, fontWeight = FontWeight.Bold, color = TempleBrown)
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "स्पर्श करें (Tap to count)", fontSize = 11.sp, color = TempleTextSecondary)
                                    }
                                }
                                3 -> {
                                    // Step 3: Bhajan listening
                                    Box(
                                        modifier = Modifier
                                            .size(100.dp)
                                            .clip(CircleShape)
                                            .background(
                                                Brush.radialGradient(listOf(GoldLight, SaffronPrimary))
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "🪈", fontSize = 50.sp)
                                    }
                                }
                                4 -> {
                                    // Step 4: Interactive Aarti Thali
                                    InteractiveAartiThali(
                                        modifier = Modifier.size(180.dp),
                                        onRotate = { onTempleBell() }
                                    )
                                }
                                5 -> {
                                    // Step 5: Prayer & Sankalpa
                                    Box(
                                        modifier = Modifier
                                            .size(100.dp)
                                            .clip(CircleShape)
                                            .background(
                                                Brush.radialGradient(listOf(GoldLight, GoldPrimary))
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "🙏", fontSize = 50.sp)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Sacred verse / Mantra display
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = CreamSurfaceVariant)
                            ) {
                                Text(
                                    text = step.mantraOrText,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = VermilionKumkum,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 22.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Bottom Nav Buttons: Previous, Sound actions, Next (अगला)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (currentStepIndex > 0) {
                        OutlinedButton(
                            onClick = onPrevStep,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                        ) {
                            Text(text = if (isHindi) "पिछला" else "Previous", fontWeight = FontWeight.Bold, color = TempleBrown)
                        }
                    }

                    Button(
                        onClick = onNextStep,
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1.5f)
                            .height(48.dp)
                            .testTag("puja_next_step_button")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (currentStepIndex == steps.size - 1) {
                                    if (isHindi) "पूजा पूर्ण करें 🙏" else "Complete Puja 🙏"
                                } else {
                                    if (isHindi) "अगला चरण →" else "Next Step →"
                                },
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = PureWhite
                            )
                        }
                    }
                }
            } else {
                // Completion Screen: आपकी सुबह की पूजा पूर्ण हुई 🙏
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .testTag("puja_completion_card"),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamSurface),
                    border = androidx.compose.foundation.BorderStroke(2.dp, GoldPrimary),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(listOf(GoldLight, GoldPrimary))
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🌺", fontSize = 48.sp)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = if (isHindi) "आपकी सुबह की पूजा पूर्ण हुई 🙏" else "Your Morning Puja is Completed 🙏",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = SaffronPrimary,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (isHindi) "भगवान की असीम कृपा और आशीर्वाद आज आपके और आपके परिवार के साथ रहे।" else "May divine grace, peace and prosperity be with you and your family today.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TempleTextDark,
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "॥ ॐ शान्तिः शान्तिः शान्तिः ॥",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = VermilionKumkum
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    val blessing = "🙏 आज मेरी सुबह की पूजा पूर्ण हुई। भगवान की कृपा आप पर सदैव बनी रहे। (भक्ति दर्शन ऐप)"
                                    onShareBlessing(blessing)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = TempleBrown)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = if (isHindi) "शुभकामनाएं साझा करें" else "Share", color = TempleBrown, fontWeight = FontWeight.Bold)
                                }
                            }

                            Button(
                                onClick = onResetPuja,
                                colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                            ) {
                                Text(text = if (isHindi) "पुनः पूजा करें" else "Start Again", color = PureWhite, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
