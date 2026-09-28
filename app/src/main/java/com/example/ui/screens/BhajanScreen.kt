package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Bhajan
import com.example.data.model.BhajanCategory
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
fun BhajanScreen(
    bhajans: List<Bhajan>,
    selectedCategory: BhajanCategory,
    isHindi: Boolean,
    isFavorite: (String) -> Boolean,
    onCategorySelected: (BhajanCategory) -> Unit,
    onPlayBhajan: (Bhajan) -> Unit,
    onToggleFavorite: (Bhajan) -> Unit
) {
    val categories = BhajanCategory.values()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .testTag("bhajan_screen")
    ) {
        // Title Header
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
                    text = if (isHindi) "भजन / कीर्तन 🎵" else "Bhajan / Kirtan 🎵",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TempleBrown
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if (isHindi) "शांतिदायक अमृतवाणी, आरती एवं पावन मंत्र" else "Peaceful devotional hymns, aarti and mantras",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TempleTextSecondary
                )
            }
        }

        // Horizontal Category Tabs
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                val isSelected = selectedCategory == category

                FilterChip(
                    selected = isSelected,
                    onClick = { onCategorySelected(category) },
                    label = {
                        Text(
                            text = if (isHindi) category.hindiName else category.englishName,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SaffronPrimary,
                        selectedLabelColor = PureWhite,
                        containerColor = CreamSurface,
                        labelColor = TempleTextDark
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = if (isSelected) SaffronPrimary else CreamCardBorder,
                        enabled = true,
                        selected = isSelected
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.testTag("category_chip_${category.name}")
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Bhajan List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val filteredList = if (selectedCategory == BhajanCategory.ALL) {
                bhajans
            } else if (selectedCategory == BhajanCategory.FAVORITES) {
                bhajans.filter { isFavorite(it.id) }
            } else {
                bhajans.filter { it.category == selectedCategory }
            }

            if (filteredList.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isHindi) "इस श्रेणी में कोई भजन नहीं मिला।" else "No hymns found in this category.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TempleTextSecondary
                        )
                    }
                }
            } else {
                items(filteredList, key = { it.id }) { bhajan ->
                    val fav = isFavorite(bhajan.id)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onPlayBhajan(bhajan) }
                            .testTag("bhajan_item_${bhajan.id}"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CreamSurface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CreamCardBorder),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Left Thumbnail & Info
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Thumbnail
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            Brush.radialGradient(
                                                listOf(GoldLight, SaffronPrimary)
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = when (bhajan.category) {
                                            BhajanCategory.AARTI -> "🪔"
                                            BhajanCategory.MANTRA -> "📿"
                                            BhajanCategory.KIRTAN -> "🥁"
                                            else -> "🎵"
                                        },
                                        fontSize = 24.sp
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = if (isHindi) bhajan.title else bhajan.titleEn,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = TempleTextDark,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "${if (isHindi) bhajan.deity else bhajan.deityEn} • ${bhajan.durationText}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TempleTextSecondary
                                    )
                                }
                            }

                            // Right Action Buttons (Favorite + Play)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                IconButton(
                                    onClick = { onToggleFavorite(bhajan) },
                                    modifier = Modifier.size(38.dp)
                                ) {
                                    Icon(
                                        imageVector = if (fav) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Favorite",
                                        tint = if (fav) VermilionKumkum else TempleTextSecondary.copy(alpha = 0.5f),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(SaffronPrimary)
                                        .clickable { onPlayBhajan(bhajan) }
                                        .testTag("play_button_${bhajan.id}"),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Play",
                                        tint = PureWhite,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
