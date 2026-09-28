package com.example

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.FavoriteType
import com.example.ui.BhaktiViewModel
import com.example.ui.Screen
import com.example.ui.components.BottomNavBar
import com.example.ui.components.MiniPlayerBar
import com.example.ui.components.TempleHeader
import com.example.ui.screens.BhajanScreen
import com.example.ui.screens.DarshanScreen
import com.example.ui.screens.DeityDetailScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MoreScreen
import com.example.ui.screens.MusicPlayerScreen
import com.example.ui.screens.PanchangScreen
import com.example.ui.screens.RashifalScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.SubahKiPujaScreen
import com.example.ui.screens.SuvicharScreen
import com.example.ui.theme.BhaktiDarshanTheme
import com.example.ui.theme.CreamBackground

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BhaktiDarshanTheme {
                BhaktiApp()
            }
        }
    }
}

@Composable
fun BhaktiApp(viewModel: BhaktiViewModel = viewModel()) {
    val context = LocalContext.current
    val currentScreen by viewModel.currentScreen.collectAsState()
    val isHindi by viewModel.isHindi.collectAsState()
    val playerState by viewModel.playerState.collectAsState()
    val selectedDeity by viewModel.selectedDeity.collectAsState()
    val selectedRashi by viewModel.selectedRashi.collectAsState()
    val selectedBhajanCat by viewModel.selectedBhajanCategory.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val pujaStepIndex by viewModel.pujaStepIndex.collectAsState()
    val jaapCounter by viewModel.jaapCounter.collectAsState()
    val isPujaCompleted by viewModel.isPujaCompleted.collectAsState()
    val suvicharIndex by viewModel.suvicharIndex.collectAsState()
    val reminderTime by viewModel.reminderTime.collectAsState()
    val isReminderEnabled by viewModel.isReminderEnabled.collectAsState()

    // Notification Permission Launcher (Android 13+)
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.testReminderNotification()
        }
    }

    fun requestNotificationAndTrigger() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                viewModel.testReminderNotification()
            } else {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            viewModel.testReminderNotification()
        }
    }

    // Handle Back Press
    BackHandler(enabled = currentScreen != Screen.Home && currentScreen != Screen.Splash) {
        viewModel.navigateBack()
    }

    // Full Screen Splash Screen
    if (currentScreen == Screen.Splash) {
        SplashScreen(
            isHindi = isHindi,
            onNavigateToHome = { viewModel.navigateTo(Screen.Home) }
        )
        return
    }

    // Full Screen Music Player Screen
    if (currentScreen == Screen.MusicPlayer) {
        val currentBhajan = playerState.currentBhajan
        val isFav = currentBhajan != null && viewModel.isFavorite(currentBhajan.id)

        MusicPlayerScreen(
            playerState = playerState,
            isHindi = isHindi,
            isFav = isFav,
            onBackClick = { viewModel.navigateBack() },
            onPlayPauseClick = { viewModel.togglePlayPause() },
            onNextClick = { viewModel.playNextBhajan() },
            onPrevClick = { viewModel.playPrevBhajan() },
            onSeekTo = { viewModel.seekTo(it) },
            onToggleRepeat = { viewModel.toggleRepeat() },
            onToggleShuffle = { viewModel.toggleShuffle() },
            onToggleFavorite = {
                currentBhajan?.let {
                    viewModel.toggleFavorite(it.id, FavoriteType.BHAJAN, it.title, it.deity)
                }
            },
            onTempleBell = { viewModel.playTempleBell() },
            onShankh = { viewModel.playShankh() },
            onShare = { text -> viewModel.shareContent(context, text) }
        )
        return
    }

    val showBottomBar = currentScreen in listOf(
        Screen.Home,
        Screen.BhajanList,
        Screen.DarshanGrid,
        Screen.Rashifal,
        Screen.More,
        Screen.Panchang,
        Screen.Suvichar
    )

    val showHeader = currentScreen in listOf(
        Screen.Home,
        Screen.BhajanList,
        Screen.DarshanGrid,
        Screen.Rashifal,
        Screen.Panchang,
        Screen.Suvichar,
        Screen.More
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = CreamBackground,
        topBar = {
            if (showHeader) {
                TempleHeader(
                    isHindi = isHindi,
                    onSearchClick = { viewModel.navigateTo(Screen.Search) },
                    onNotificationClick = {
                        requestNotificationAndTrigger()
                    },
                    onLanguageToggle = { viewModel.toggleLanguage() }
                )
            }
        },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Mini Player Bar (shown whenever music is loaded/playing and not on full screen player)
                if (playerState.currentBhajan != null) {
                    MiniPlayerBar(
                        playerState = playerState,
                        isHindi = isHindi,
                        onBarClick = { viewModel.navigateTo(Screen.MusicPlayer) },
                        onPlayPauseClick = { viewModel.togglePlayPause() },
                        onBellClick = { viewModel.playTempleBell() }
                    )
                }

                if (showBottomBar) {
                    BottomNavBar(
                        currentScreen = currentScreen,
                        isHindi = isHindi,
                        onTabSelected = { screen -> viewModel.navigateTo(screen) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(CreamBackground)
        ) {
            when (currentScreen) {
                Screen.Home -> {
                    val allDeities = viewModel.getAllDeities()
                    val allQuotes = viewModel.getAllSuvichars()
                    val panchang = viewModel.getTodayPanchang()

                    HomeScreen(
                        isHindi = isHindi,
                        todayDeity = allDeities[1], // Shri Krishna Ji
                        dailyQuote = allQuotes[0],
                        todayDate = if (isHindi) panchang.dateDisplay else panchang.dateDisplayEn,
                        onNavigateToScreen = { screen -> viewModel.navigateTo(screen) },
                        onSelectDeity = { deity -> viewModel.selectDeity(deity) },
                        onStartPujaClick = { viewModel.startPuja() },
                        onShareQuote = { text -> viewModel.shareContent(context, text) }
                    )
                }

                Screen.DarshanGrid -> {
                    DarshanScreen(
                        deities = viewModel.getAllDeities(),
                        isHindi = isHindi,
                        isFavorite = { id -> viewModel.isFavorite(id) },
                        onToggleFavorite = { deity ->
                            viewModel.toggleFavorite(deity.id, FavoriteType.DEITY, deity.name, deity.mantra)
                        },
                        onSelectDeity = { deity -> viewModel.selectDeity(deity) }
                    )
                }

                Screen.DeityDetail -> {
                    val isFav = viewModel.isFavorite(selectedDeity.id)
                    DeityDetailScreen(
                        deity = selectedDeity,
                        isHindi = isHindi,
                        isFav = isFav,
                        onBackClick = { viewModel.navigateBack() },
                        onToggleFavorite = {
                            viewModel.toggleFavorite(selectedDeity.id, FavoriteType.DEITY, selectedDeity.name, selectedDeity.mantra)
                        },
                        onPlayAudio = {
                            // Find corresponding bhajan or start devotional sound
                            val matchingBhajan = viewModel.getAllBhajans().find { it.deity.contains(selectedDeity.name.take(4)) }
                                ?: viewModel.getAllBhajans()[0]
                            viewModel.playBhajan(matchingBhajan)
                        },
                        onTempleBell = { viewModel.playTempleBell() },
                        onShankh = { viewModel.playShankh() },
                        onJaapTapped = { viewModel.triggerHaptic(50) },
                        onShare = { text -> viewModel.shareContent(context, text) }
                    )
                }

                Screen.BhajanList -> {
                    BhajanScreen(
                        bhajans = viewModel.getAllBhajans(),
                        selectedCategory = selectedBhajanCat,
                        isHindi = isHindi,
                        isFavorite = { id -> viewModel.isFavorite(id) },
                        onCategorySelected = { cat -> viewModel.selectBhajanCategory(cat) },
                        onPlayBhajan = { bhajan ->
                            viewModel.playBhajan(bhajan)
                            viewModel.navigateTo(Screen.MusicPlayer)
                        },
                        onToggleFavorite = { bhajan ->
                            viewModel.toggleFavorite(bhajan.id, FavoriteType.BHAJAN, bhajan.title, bhajan.deity)
                        }
                    )
                }

                Screen.Rashifal -> {
                    RashifalScreen(
                        rashifalList = viewModel.getRashifalList(),
                        selectedRashi = selectedRashi,
                        isHindi = isHindi,
                        onRashiSelected = { rashi -> viewModel.selectRashi(rashi) }
                    )
                }

                Screen.Panchang -> {
                    PanchangScreen(
                        panchang = viewModel.getTodayPanchang(),
                        isHindi = isHindi
                    )
                }

                Screen.SubahKiPuja -> {
                    SubahKiPujaScreen(
                        steps = viewModel.getPujaSteps(),
                        currentStepIndex = pujaStepIndex,
                        jaapCount = jaapCounter,
                        isCompleted = isPujaCompleted,
                        isHindi = isHindi,
                        onNextStep = { viewModel.nextPujaStep() },
                        onPrevStep = { viewModel.prevPujaStep() },
                        onIncrementJaap = { viewModel.incrementJaap() },
                        onResetPuja = { viewModel.startPuja() },
                        onTempleBell = { viewModel.playTempleBell() },
                        onShankh = { viewModel.playShankh() },
                        onBack = { viewModel.navigateBack() },
                        onShareBlessing = { text -> viewModel.shareContent(context, text) }
                    )
                }

                Screen.Suvichar -> {
                    val allSuvichars = viewModel.getAllSuvichars()
                    val currentSuvichar = allSuvichars.getOrNull(suvicharIndex) ?: allSuvichars[0]
                    val isFav = viewModel.isFavorite(currentSuvichar.id)

                    SuvicharScreen(
                        suvichars = allSuvichars,
                        currentIndex = suvicharIndex,
                        isHindi = isHindi,
                        isFav = isFav,
                        onNextSuvichar = { viewModel.nextSuvichar() },
                        onToggleFavorite = { item ->
                            viewModel.toggleFavorite(item.id, FavoriteType.SUVICHAR, item.quote.take(40) + "...", item.authorOrSource)
                        },
                        onShare = { text -> viewModel.shareContent(context, text) }
                    )
                }

                Screen.Favorites -> {
                    FavoritesScreen(
                        favorites = favorites,
                        isHindi = isHindi,
                        onBack = { viewModel.navigateBack() },
                        onRemoveFavorite = { id ->
                            viewModel.toggleFavorite(id, FavoriteType.BHAJAN, "")
                        },
                        onItemClick = { item ->
                            when (item.type) {
                                "DEITY" -> {
                                    val deity = viewModel.getAllDeities().find { it.id == item.id }
                                    if (deity != null) viewModel.selectDeity(deity)
                                }
                                "BHAJAN" -> {
                                    val bhajan = viewModel.getAllBhajans().find { it.id == item.id }
                                    if (bhajan != null) {
                                        viewModel.playBhajan(bhajan)
                                        viewModel.navigateTo(Screen.MusicPlayer)
                                    }
                                }
                                "SUVICHAR" -> viewModel.navigateTo(Screen.Suvichar)
                            }
                        }
                    )
                }

                Screen.Search -> {
                    SearchScreen(
                        query = searchQuery,
                        searchResults = searchResults,
                        isHindi = isHindi,
                        onQueryChange = { q -> viewModel.onSearchQueryChanged(q) },
                        onBack = { viewModel.navigateBack() },
                        onSelectDeity = { deity -> viewModel.selectDeity(deity) },
                        onPlayBhajan = { bhajan ->
                            viewModel.playBhajan(bhajan)
                            viewModel.navigateTo(Screen.MusicPlayer)
                        }
                    )
                }

                Screen.More -> {
                    MoreScreen(
                        isHindi = isHindi,
                        reminderTime = reminderTime,
                        isReminderEnabled = isReminderEnabled,
                        onNavigateToFavorites = { viewModel.navigateTo(Screen.Favorites) },
                        onToggleLanguage = { viewModel.toggleLanguage() },
                        onSetReminderTime = { time -> viewModel.setReminderTime(time) },
                        onToggleReminder = { enabled -> viewModel.toggleReminder(enabled) },
                        onTestNotification = { requestNotificationAndTrigger() },
                        onShareApp = {
                            val shareAppText = "🙏 “भक्ति दर्शन” (Bhakti Darshan) ऐप - हर सुबह भगवान के साथ।\nभगवान के पावन दर्शन, भजन, आरती, पंचांग व दैनिक राशिफल प्राप्त करें।"
                            viewModel.shareContent(context, shareAppText)
                        }
                    )
                }

                else -> {}
            }
        }
    }
}
