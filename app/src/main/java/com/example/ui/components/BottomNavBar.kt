package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.Screen
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamCardBorder
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.TempleBrown
import com.example.ui.theme.TempleTextDark
import com.example.ui.theme.TempleTextSecondary

data class NavItem(
    val screen: Screen,
    val hindiTitle: String,
    val englishTitle: String,
    val emoji: String,
    val testTag: String
)

@Composable
fun BottomNavBar(
    currentScreen: Screen,
    isHindi: Boolean,
    onTabSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItem(Screen.Home, "होम", "Home", "🏠", "tab_home"),
        NavItem(Screen.BhajanList, "भजन", "Bhajan", "🎵", "tab_bhajan"),
        NavItem(Screen.DarshanGrid, "दर्शन", "Darshan", "🛕", "tab_darshan"),
        NavItem(Screen.Rashifal, "राशिफल", "Rashifal", "♈", "tab_rashifal"),
        NavItem(Screen.More, "और", "More", "☰", "tab_more")
    )

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = CreamBackground,
        shadowElevation = 12.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, CreamCardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = when (item.screen) {
                    Screen.Home -> currentScreen == Screen.Home
                    Screen.BhajanList -> currentScreen == Screen.BhajanList
                    Screen.DarshanGrid -> currentScreen == Screen.DarshanGrid || currentScreen == Screen.DeityDetail
                    Screen.Rashifal -> currentScreen == Screen.Rashifal
                    Screen.More -> currentScreen == Screen.More || currentScreen == Screen.Favorites || currentScreen == Screen.Panchang || currentScreen == Screen.Suvichar
                    else -> false
                }

                val pillBg by animateColorAsState(
                    targetValue = if (isSelected) SaffronPrimary.copy(alpha = 0.14f) else Color.Transparent,
                    label = "pillBg"
                )

                val contentColor by animateColorAsState(
                    targetValue = if (isSelected) SaffronPrimary else TempleTextSecondary,
                    label = "contentColor"
                )

                val interactionSource = remember { MutableInteractionSource() }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(pillBg)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onTabSelected(item.screen)
                        }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                        .testTag(item.testTag),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = item.emoji,
                            fontSize = if (isSelected) 22.sp else 19.sp
                        )
                        Text(
                            text = if (isHindi) item.hindiTitle else item.englishTitle,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = contentColor
                        )
                    }
                }
            }
        }
    }
}
