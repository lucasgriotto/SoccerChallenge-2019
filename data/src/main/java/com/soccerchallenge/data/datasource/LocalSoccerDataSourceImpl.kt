package com.soccerchallenge.data.datasource

import com.soccerchallenge.data.database.dao.MatchDao
import com.soccerchallenge.data.database.entity.MatchEntity
import com.soccerchallenge.domain.model.MatchType
import javax.inject.Inject

class LocalSoccerDataSourceImpl @Inject constructor(
        private val matchDao: MatchDao
) : LocalSoccerDataSource {

    override suspend fun fetchFixtureFromLocal() = matchDao.getMatches(MatchType.FIXTURE)

    override suspend fun fetchMatchResultsFromLocal() = matchDao.getMatches(MatchType.RESULT)

    override suspend fun insertMatches(matches: List<MatchEntity>) {
        matchDao.insertMatches(matches)
    }

    override suspend fun deleteMatches(matchType: MatchType) {
        matchDao.deleteMatches(matchType)
    }

    override suspend fun fetchMatchFromLocal(matchId: Int) = matchDao.getMatch(matchId)

}