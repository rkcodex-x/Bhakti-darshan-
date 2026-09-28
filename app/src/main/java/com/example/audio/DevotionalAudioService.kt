package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Handler
import android.os.Looper
import com.example.data.model.Bhajan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin

data class PlayerState(
    val currentBhajan: Bhajan? = null,
    val isPlaying: Boolean = false,
    val currentPositionSeconds: Int = 0,
    val totalDurationSeconds: Int = 0,
    val isRepeatEnabled: Boolean = false,
    val isShuffleEnabled: Boolean = false
)

object DevotionalAudioService {

    private val _playerState = MutableStateFlow(PlayerState())
    val playerState: StateFlow<PlayerState> = _playerState.asStateFlow()

    private var playbackJob: Job? = null
    private var droneJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default)

    private val sampleRate = 44100
    private var isDroneRunning = false

    fun playBhajan(bhajan: Bhajan) {
        val currentState = _playerState.value
        if (currentState.currentBhajan?.id == bhajan.id && currentState.isPlaying) {
            pause()
            return
        }

        _playerState.value = _playerState.value.copy(
            currentBhajan = bhajan,
            isPlaying = true,
            currentPositionSeconds = 0,
            totalDurationSeconds = bhajan.durationSeconds
        )

        startPlaybackTicker()
        startDevotionalDrone(bhajan.baseFrequencyHz)
    }

    fun togglePlayPause() {
        val state = _playerState.value
        if (state.currentBhajan == null) return

        if (state.isPlaying) {
            pause()
        } else {
            resume()
        }
    }

    fun pause() {
        _playerState.value = _playerState.value.copy(isPlaying = false)
        playbackJob?.cancel()
        isDroneRunning = false
    }

    fun resume() {
        val bhajan = _playerState.value.currentBhajan ?: return
        _playerState.value = _playerState.value.copy(isPlaying = true)
        startPlaybackTicker()
        startDevotionalDrone(bhajan.baseFrequencyHz)
    }

    fun seekTo(seconds: Int) {
        val duration = _playerState.value.totalDurationSeconds
        val clamped = seconds.coerceIn(0, duration)
        _playerState.value = _playerState.value.copy(currentPositionSeconds = clamped)
    }

    fun toggleRepeat() {
        val newRepeat = !_playerState.value.isRepeatEnabled
        _playerState.value = _playerState.value.copy(isRepeatEnabled = newRepeat)
    }

    fun toggleShuffle() {
        val newShuffle = !_playerState.value.isShuffleEnabled
        _playerState.value = _playerState.value.copy(isShuffleEnabled = newShuffle)
    }

    private fun startPlaybackTicker() {
        playbackJob?.cancel()
        playbackJob = scope.launch {
            while (isActive && _playerState.value.isPlaying) {
                delay(1000)
                val current = _playerState.value.currentPositionSeconds
                val total = _playerState.value.totalDurationSeconds
                if (current < total) {
                    _playerState.value = _playerState.value.copy(currentPositionSeconds = current + 1)
                } else {
                    if (_playerState.value.isRepeatEnabled) {
                        _playerState.value = _playerState.value.copy(currentPositionSeconds = 0)
                    } else {
                        pause()
                    }
                }
            }
        }
    }

    /**
     * Synthesizes warm, meditative Tanpura / ambient drone chord (root Sa and Pa)
     */
    private fun startDevotionalDrone(rootFreq: Float) {
        isDroneRunning = true
        droneJob?.cancel()
        droneJob = scope.launch(Dispatchers.IO) {
            val bufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            ).coerceAtLeast(4096)

            val audioTrack = try {
                AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(bufferSize)
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .build()
            } catch (e: Exception) {
                return@launch
            }

            try {
                audioTrack.play()
                val numSamples = bufferSize / 2
                val buffer = ShortArray(numSamples)
                var phase1 = 0.0
                var phase2 = 0.0
                var phase3 = 0.0

                val freq1 = rootFreq.toDouble()
                val freq2 = rootFreq * 1.5 // Pa (Fifth harmonic)
                val freq3 = rootFreq * 2.0 // Upper Sa

                while (isActive && isDroneRunning && _playerState.value.isPlaying) {
                    for (i in 0 until numSamples) {
                        phase1 += 2.0 * PI * freq1 / sampleRate
                        phase2 += 2.0 * PI * freq2 / sampleRate
                        phase3 += 2.0 * PI * freq3 / sampleRate

                        if (phase1 > 2.0 * PI) phase1 -= 2.0 * PI
                        if (phase2 > 2.0 * PI) phase2 -= 2.0 * PI
                        if (phase3 > 2.0 * PI) phase3 -= 2.0 * PI

                        // Gentle harmonic mix with soft warmth
                        val s1 = sin(phase1) * 0.45
                        val s2 = sin(phase2) * 0.30
                        val s3 = sin(phase3) * 0.15
                        val sample = ((s1 + s2 + s3) * 0.45 * Short.MAX_VALUE).toInt().toShort()
                        buffer[i] = sample
                    }
                    audioTrack.write(buffer, 0, numSamples)
                }
            } catch (e: Exception) {
                // Ignore audio write interrupts
            } finally {
                try {
                    audioTrack.stop()
                    audioTrack.release()
                } catch (e: Exception) {
                    // Ignore
                }
            }
        }
    }

    /**
     * Plays a rich temple bell chime (घंटी) with natural acoustic decay
     */
    fun playTempleBell() {
        scope.launch(Dispatchers.IO) {
            val durationSeconds = 2.0
            val totalSamples = (sampleRate * durationSeconds).toInt()
            val buffer = ShortArray(totalSamples)

            val bellFreq1 = 1046.5 // C6
            val bellFreq2 = 1318.5 // E6
            val bellFreq3 = 1567.9 // G6
            val bellStrike = 2093.0 // High chime strike

            for (i in 0 until totalSamples) {
                val t = i.toDouble() / sampleRate
                val envelope = exp(-2.8 * t) // Natural acoustic bell exponential decay
                val strikeEnv = exp(-12.0 * t)

                val val1 = sin(2.0 * PI * bellFreq1 * t) * 0.5
                val val2 = sin(2.0 * PI * bellFreq2 * t) * 0.3
                val val3 = sin(2.0 * PI * bellFreq3 * t) * 0.2
                val strike = sin(2.0 * PI * bellStrike * t) * strikeEnv * 0.4

                val sample = ((val1 + val2 + val3 + strike) * envelope * 0.7 * Short.MAX_VALUE).toInt()
                buffer[i] = sample.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
            }

            try {
                val track = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(totalSamples * 2)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                track.write(buffer, 0, totalSamples)
                track.play()
                delay((durationSeconds * 1000).toLong() + 200)
                track.release()
            } catch (e: Exception) {
                // Ignore
            }
        }
    }

    /**
     * Plays a sacred Shankh resonance (शंखनाद)
     */
    fun playShankhSound() {
        scope.launch(Dispatchers.IO) {
            val durationSeconds = 3.2
            val totalSamples = (sampleRate * durationSeconds).toInt()
            val buffer = ShortArray(totalSamples)

            val baseFreq = 180.0

            for (i in 0 until totalSamples) {
                val t = i.toDouble() / sampleRate
                // Attack and release envelope
                val attack = (t / 0.6).coerceAtMost(1.0)
                val release = ((durationSeconds - t) / 0.8).coerceIn(0.0, 1.0)
                val envelope = attack * release

                // Slight frequency swell of shankh air pressure
                val pitchSwell = baseFreq * (1.0 + 0.04 * sin(2.0 * PI * 1.5 * t))
                val wave = sin(2.0 * PI * pitchSwell * t) * 0.6 +
                        sin(2.0 * PI * pitchSwell * 2.0 * t) * 0.3 +
                        sin(2.0 * PI * pitchSwell * 3.0 * t) * 0.15

                val sample = (wave * envelope * 0.7 * Short.MAX_VALUE).toInt()
                buffer[i] = sample.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
            }

            try {
                val track = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(totalSamples * 2)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                track.write(buffer, 0, totalSamples)
                track.play()
                delay((durationSeconds * 1000).toLong() + 200)
                track.release()
            } catch (e: Exception) {
                // Ignore
            }
        }
    }
}
