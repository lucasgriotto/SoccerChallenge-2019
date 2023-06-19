package com.lucas.soccerchallenge.ui.home.match

import com.lucas.soccerchallenge.ui.home.matchfilter.model.MatchDisplayModel
import com.lucas.soccerchallenge.utils.errorfactory.AppError

sealed class MatchScreenState {

    object Loading : MatchScreenState()

    data class Success(val data: List<MatchDisplayModel>) : MatchScreenState()

    data class Error(val error: AppError) : MatchScreenState()

}
