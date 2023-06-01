package com.lucas.soccerchallenge.core.data.repository

interface UserPreferencesRepository {

    suspend fun saveSelectedCompetitionsFilterIds(ids: Set<Int>)

    suspend fun getSelectedCompetitionsFilterIds(): Set<Int>

}