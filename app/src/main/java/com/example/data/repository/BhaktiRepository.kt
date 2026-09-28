package com.example.data.repository

import com.example.data.local.BhaktiDao
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
import com.example.data.model.GuidedPujaSteps
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class BhaktiRepository(private val dao: BhaktiDao) {

    val allFavorites: Flow<List<FavoriteEntity>> = dao.getAllFavorites()

    fun getFavoritesByType(type: FavoriteType): Flow<List<FavoriteEntity>> =
        dao.getFavoritesByType(type.name)

    fun isFavorite(id: String): Flow<Boolean> = dao.isFavorite(id)

    suspend fun toggleFavorite(id: String, type: FavoriteType, title: String, subtitle: String = "") {
        val current = dao.isFavorite(id).firstOrNull() ?: false
        if (current) {
            dao.deleteFavoriteById(id)
        } else {
            dao.insertFavorite(
                FavoriteEntity(
                    id = id,
                    type = type.name,
                    title = title,
                    subtitle = subtitle
                )
            )
        }
    }

    suspend fun removeFavorite(id: String) {
        dao.deleteFavoriteById(id)
    }

    fun getAllDeities(): List<Deity> = SampleDeities

    fun getDeityById(id: String): Deity? = SampleDeities.find { it.id == id }

    fun getAllBhajans(): List<Bhajan> = SampleBhajans

    fun getBhajansByCategory(category: BhajanCategory): List<Bhajan> {
        return if (category == BhajanCategory.ALL) {
            SampleBhajans
        } else {
            SampleBhajans.filter { it.category == category }
        }
    }

    fun getBhajanById(id: String): Bhajan? = SampleBhajans.find { it.id == id }

    fun getRashifalList(): List<Rashi> = SampleRashifalList

    fun getRashiById(id: String): Rashi? = SampleRashifalList.find { it.id == id }

    fun getTodayPanchang(): Panchang = SampleTodayPanchang

    fun getAllSuvichars(): List<Suvichar> = SampleSuvichars

    fun getPujaSteps(): List<PujaStep> = GuidedPujaSteps

    /**
     * Search across Deities, Bhajans, Mantras, and Suvichars.
     */
    fun search(query: String): SearchResults {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return SearchResults()

        val matchingDeities = SampleDeities.filter {
            it.name.lowercase().contains(q) ||
            it.nameEn.lowercase().contains(q) ||
            it.mantra.lowercase().contains(q)
        }

        val matchingBhajans = SampleBhajans.filter {
            it.title.lowercase().contains(q) ||
            it.titleEn.lowercase().contains(q) ||
            it.deity.lowercase().contains(q) ||
            it.lyrics.lowercase().contains(q)
        }

        val matchingSuvichars = SampleSuvichars.filter {
            it.quote.lowercase().contains(q) ||
            it.authorOrSource.lowercase().contains(q)
        }

        return SearchResults(
            deities = matchingDeities,
            bhajans = matchingBhajans,
            suvichars = matchingSuvichars
        )
    }
}

data class SearchResults(
    val deities: List<Deity> = emptyList(),
    val bhajans: List<Bhajan> = emptyList(),
    val suvichars: List<Suvichar> = emptyList()
)
