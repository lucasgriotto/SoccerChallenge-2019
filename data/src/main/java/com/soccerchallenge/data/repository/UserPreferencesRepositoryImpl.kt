package com.soccerchallenge.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.soccerchallenge.data.repository.UserPreferencesRepositoryImpl.PreferencesKeys.SELECTED_COMPETITIONS_IDS
import com.soccerchallenge.data.util.CompetitionFilters
import com.soccerchallenge.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferencesRepository {

    private object PreferencesKeys {
        val SELECTED_COMPETITIONS_IDS = stringPreferencesKey("selected_competitions_ids")
    }

    override suspend fun saveSelectedCompetitionsFilterIds(ids: Set<Int>) {
        val stringIds = ids.joinToString(DELIMITER)
        dataStore.edit { preferences ->
            preferences[SELECTED_COMPETITIONS_IDS] = stringIds
        }
    }

    override suspend fun getSelectedCompetitionsFilterIds(): Set<Int> {
        val stringIds = dataStore.data.first()[SELECTED_COMPETITIONS_IDS] ?: EMPTY_STRING
        return if (stringIds.isNotEmpty()) {
            stringIds.split(DELIMITER).map { it.toInt() }.toSet()
        } else {
            CompetitionFilters.defaultSelectedCompetitionIds
        }
    }

    companion object {
        private const val DELIMITER = ","
        private const val EMPTY_STRING = ""
    }

}