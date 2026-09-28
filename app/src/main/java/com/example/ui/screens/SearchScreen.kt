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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Bhajan
import com.example.data.model.Deity
import com.example.data.repository.SearchResults
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamCardBorder
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.TempleBrown
import com.example.ui.theme.TempleTextDark
import com.example.ui.theme.TempleTextSecondary

@Composable
fun SearchScreen(
    query: String,
    searchResults: SearchResults,
    isHindi: Boolean,
    onQueryChange: (String) -> Unit,
    onBack: () -> Unit,
    onSelectDeity: (Deity) -> Unit,
    onPlayBhajan: (Bhajan) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .testTag("search_screen")
    ) {
        // Search Input Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CreamSurface)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TempleBrown
                )
            }

            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .testTag("search_text_field"),
                placeholder = {
                    Text(
                        text = if (isHindi) "भगवान, भजन, आरती, मंत्र खोजें..." else "Search deities, bhajans, aarti...",
                        fontSize = 14.sp,
                        color = TempleTextSecondary
                    )
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = SaffronPrimary)
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { onQueryChange("") }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear", tint = TempleTextSecondary)
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SaffronPrimary,
                    unfocusedBorderColor = CreamCardBorder,
                    focusedContainerColor = PureWhite,
                    unfocusedContainerColor = PureWhite
                )
            )
        }

        // Search Results List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (query.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🔍", fontSize = 42.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = if (isHindi) "उदाहरण: “हनुमान”, “कृष्ण”, “आरती”, “मंत्र”" else "Example: “Hanuman”, “Krishna”, “Aarti”",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TempleTextSecondary
                        )
                    }
                }
            } else {
                val totalResults = searchResults.deities.size + searchResults.bhajans.size + searchResults.suvichars.size

                if (totalResults == 0) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (isHindi) "कोई परिणाम नहीं मिला।" else "No matching results found.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TempleTextSecondary
                            )
                        }
                    }
                } else {
                    // Deities
                    if (searchResults.deities.isNotEmpty()) {
                        item {
                            Text(
                                text = if (isHindi) "भगवान के दर्शन 🛕" else "Deities 🛕",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TempleBrown
                            )
                        }
                        items(searchResults.deities) { deity ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .clickable { onSelectDeity(deity) },
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                                border = androidx.compose.foundation.BorderStroke(1.dp, CreamCardBorder)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(42.dp)
                                            .clip(CircleShape)
                                            .background(Brush.radialGradient(listOf(GoldLight, SaffronOrange))),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = deity.symbol, fontSize = 22.sp)
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = if (isHindi) deity.name else deity.nameEn,
                                            fontWeight = FontWeight.Bold,
                                            color = TempleTextDark
                                        )
                                        Text(
                                            text = deity.mantra,
                                            fontSize = 12.sp,
                                            color = SaffronPrimary
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Bhajans
                    if (searchResults.bhajans.isNotEmpty()) {
                        item {
                            Text(
                                text = if (isHindi) "भजन व आरती 🎵" else "Bhajans & Aarti 🎵",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TempleBrown,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }
                        items(searchResults.bhajans) { bhajan ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .clickable { onPlayBhajan(bhajan) },
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                                border = androidx.compose.foundation.BorderStroke(1.dp, CreamCardBorder)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "🎵", fontSize = 22.sp)
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(
                                                text = if (isHindi) bhajan.title else bhajan.titleEn,
                                                fontWeight = FontWeight.Bold,
                                                color = TempleTextDark
                                            )
                                            Text(
                                                text = "${if (isHindi) bhajan.deity else bhajan.deityEn} • ${bhajan.durationText}",
                                                fontSize = 11.sp,
                                                color = TempleTextSecondary
                                            )
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(SaffronPrimary),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play", tint = PureWhite, modifier = Modifier.size(20.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
