package com.example.ui

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.DevotionalAudioService
import com.example.audio.PlayerState
import com.example.data.local.BhaktiDatabase
import com.example.data.model.Bhajan
import com.example.data.model.BhajanCategory
import com.example.data.model.Deity
import com.example.data.model.FavoriteEntity
import com.example.data.model.FavoriteType
import com.example.data.model.Panchang
import com.example.data.model.PujaStep
import com.example.data.model.Rashi
import com.example.data.model.SampleBhajans
import com.example.data.model.SampleDeities
import com.example.data.model.SampleRashifalList
import com.example.data.model.SampleSuvichars
import com.example.data.model.SampleTodayPanchang
import com.example.data.model.Suvichar
import com.example.data.repository.BhaktiRepository
import com.example.data.repository.SearchResults
import com.example.notifications.MorningReminderManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object BhajanList : Screen("bhajan_list")
    data object DarshanGrid : Screen("darshan_grid")
    data object DeityDetail : Screen("deity_detail")
    data object MusicPlayer : Screen("music_player")
    data object Rashifal : Screen("rashifal")
    data object Panchang : Screen("panchang")
    data object SubahKiPuja : Screen("subah_ki_puja")
    data object Suvichar : Screen("suvichar")
    data object Favorites : Screen("favorites")
    data object Search : Screen("search")
    data object More : Screen("more")
}

class BhaktiViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BhaktiRepository
    private val vibrator: Vibrator?

    init {
        val database = BhaktiDatabase.getDatabase(application)
        repository = BhaktiRepository(database.bhaktiDao())

        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = application.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vibratorManager?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            application.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }

        MorningReminderManager.createNotificationChannel(application)
    }

    // Navigation & Screen Stack
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Splash)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val screenStack = mutableListOf<Screen>()

    // Language Toggle: true = Hindi, false = English
    private val _isHindi = MutableStateFlow(true)
    val isHindi: StateFlow<Boolean> = _isHindi.asStateFlow()

    // Player State
    val playerState: StateFlow<PlayerState> = DevotionalAudioService.playerState

    // Favorites from Room Database
    val favorites: StateFlow<List<FavoriteEntity>> = repository.allFavorites
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Selected Entities
    private val _selectedDeity = MutableStateFlow<Deity>(SampleDeities[1]) // Default Krishna Ji
    val selectedDeity: StateFlow<Deity> = _selectedDeity.asStateFlow()

    private val _selectedRashi = MutableStateFlow<Rashi>(SampleRashifalList[0]) // Default Mesh
    val selectedRashi: StateFlow<Rashi> = _selectedRashi.asStateFlow()

    private val _selectedBhajanCategory = MutableStateFlow(BhajanCategory.ALL)
    val selectedBhajanCategory: StateFlow<BhajanCategory> = _selectedBhajanCategory.asStateFlow()

    // Search
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow(SearchResults())
    val searchResults: StateFlow<SearchResults> = _searchResults.asStateFlow()

    // Guided Subah Ki Puja State
    private val _pujaStepIndex = MutableStateFlow(0) // 0..4
    val pujaStepIndex: StateFlow<Int> = _pujaStepIndex.asStateFlow()

    private val _jaapCounter = MutableStateFlow(0)
    val jaapCounter: StateFlow<Int> = _jaapCounter.asStateFlow()

    private val _isPujaCompleted = MutableStateFlow(false)
    val isPujaCompleted: StateFlow<Boolean> = _isPujaCompleted.asStateFlow()

    // Suvichar index
    private val _suvicharIndex = MutableStateFlow(0)
    val suvicharIndex: StateFlow<Int> = _suvicharIndex.asStateFlow()

    // Morning reminder time
    private val _reminderTime = MutableStateFlow("06:00 AM")
    val reminderTime: StateFlow<String> = _reminderTime.asStateFlow()

    private val _isReminderEnabled = MutableStateFlow(true)
    val isReminderEnabled: StateFlow<Boolean> = _isReminderEnabled.asStateFlow()

    // Navigation Methods
    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            screenStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (screenStack.isNotEmpty()) {
            _currentScreen.value = screenStack.removeAt(screenStack.size - 1)
            return true
        }
        if (_currentScreen.value != Screen.Home) {
            _currentScreen.value = Screen.Home
            return true
        }
        return false
    }

    fun setLanguage(hindi: Boolean) {
        _isHindi.value = hindi
    }

    fun toggleLanguage() {
        _isHindi.value = !_isHindi.value
    }

    fun selectDeity(deity: Deity) {
        _selectedDeity.value = deity
        navigateTo(Screen.DeityDetail)
    }

    fun selectRashi(rashi: Rashi) {
        _selectedRashi.value = rashi
    }

    fun selectBhajanCategory(category: BhajanCategory) {
        _selectedBhajanCategory.value = category
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        _searchResults.value = repository.search(query)
    }

    // Audio Player Actions
    fun playBhajan(bhajan: Bhajan) {
        DevotionalAudioService.playBhajan(bhajan)
    }

    fun togglePlayPause() {
        DevotionalAudioService.togglePlayPause()
    }

    fun seekTo(seconds: Int) {
        DevotionalAudioService.seekTo(seconds)
    }

    fun toggleRepeat() {
        DevotionalAudioService.toggleRepeat()
    }

    fun toggleShuffle() {
        DevotionalAudioService.toggleShuffle()
    }

    fun playNextBhajan() {
        val current = playerState.value.currentBhajan ?: return
        val all = repository.getAllBhajans()
        val index = all.indexOfFirst { it.id == current.id }
        if (index != -1 && index + 1 < all.size) {
            playBhajan(all[index + 1])
        } else if (all.isNotEmpty()) {
            playBhajan(all[0])
        }
    }

    fun playPrevBhajan() {
        val current = playerState.value.currentBhajan ?: return
        val all = repository.getAllBhajans()
        val index = all.indexOfFirst { it.id == current.id }
        if (index > 0) {
            playBhajan(all[index - 1])
        } else if (all.isNotEmpty()) {
            playBhajan(all[all.size - 1])
        }
    }

    fun playTempleBell() {
        DevotionalAudioService.playTempleBell()
        triggerHaptic(50)
    }

    fun playShankh() {
        DevotionalAudioService.playShankhSound()
        triggerHaptic(120)
    }

    // Favorites
    fun toggleFavorite(id: String, type: FavoriteType, title: String, subtitle: String = "") {
        viewModelScope.launch {
            repository.toggleFavorite(id, type, title, subtitle)
        }
    }

    fun isFavorite(id: String): Boolean {
        return favorites.value.any { it.id == id }
    }

    // Puja Flow
    fun startPuja() {
        _pujaStepIndex.value = 0
        _jaapCounter.value = 0
        _isPujaCompleted.value = false
        navigateTo(Screen.SubahKiPuja)
    }

    fun nextPujaStep() {
        if (_pujaStepIndex.value < repository.getPujaSteps().size - 1) {
            _pujaStepIndex.value += 1
            playTempleBell()
        } else {
            _isPujaCompleted.value = true
            playShankh()
        }
    }

    fun prevPujaStep() {
        if (_pujaStepIndex.value > 0) {
            _pujaStepIndex.value -= 1
        }
    }

    fun incrementJaap() {
        _jaapCounter.value += 1
        triggerHaptic(40)
    }

    fun resetJaap() {
        _jaapCounter.value = 0
    }

    // Suvichar
    fun nextSuvichar() {
        val all = repository.getAllSuvichars()
        _suvicharIndex.value = (_suvicharIndex.value + 1) % all.size
    }

    fun prevSuvichar() {
        val all = repository.getAllSuvichars()
        _suvicharIndex.value = if (_suvicharIndex.value == 0) all.size - 1 else _suvicharIndex.value - 1
    }

    // Reminder
    fun setReminderTime(time: String) {
        _reminderTime.value = time
    }

    fun toggleReminder(enabled: Boolean) {
        _isReminderEnabled.value = enabled
    }

    fun testReminderNotification() {
        MorningReminderManager.triggerTestNotification(getApplication(), _isHindi.value)
    }

    // Haptic Feedback for Jaap Bead / Bells
    fun triggerHaptic(durationMs: Long = 50) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(durationMs)
            }
        } catch (e: Exception) {
            // Ignore if vibration unavailable
        }
    }

    // Share Content Intent
    fun shareContent(context: Context, text: String, title: String = "भक्ति दर्शन") {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, title).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(shareIntent)
    }

    // Repository accessors
    fun getAllDeities(): List<Deity> = repository.getAllDeities()
    fun getAllBhajans(): List<Bhajan> = repository.getAllBhajans()
    fun getBhajansByCategory(category: BhajanCategory): List<Bhajan> = repository.getBhajansByCategory(category)
    fun getRashifalList(): List<Rashi> = repository.getRashifalList()
    fun getTodayPanchang(): Panchang = repository.getTodayPanchang()
    fun getAllSuvichars(): List<Suvichar> = repository.getAllSuvichars()
    fun getPujaSteps(): List<PujaStep> = repository.getPujaSteps()
}
