package com.soccerchallenge.domain.repository

interface UserPreferencesRepository {

    suspend fun saveSelectedCompetitionsFilterIds(ids: Set<Int>)

    suspend fun getSelectedCompetitionsFilterIds(): Set<Int>

}