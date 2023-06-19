package com.lucas.soccerchallenge.ui.match

import com.lucas.soccerchallenge.ui.fixture.adapter.FixtureDisplayModel
import com.lucas.soccerchallenge.ui.results.adapter.ResultDisplayModel

sealed class MatchDetailScreenState {

    object Idle : MatchDetailScreenState()

    data class FixtureData(val data: FixtureDisplayModel) : MatchDetailScreenState()

    data class ResultData(val data: ResultDisplayModel) : MatchDetailScreenState()

}
