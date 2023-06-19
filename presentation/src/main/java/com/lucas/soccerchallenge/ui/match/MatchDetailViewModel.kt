package com.lucas.soccerchallenge.ui.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucas.soccerchallenge.ui.fixture.adapter.FixtureDisplayModel
import com.lucas.soccerchallenge.ui.fixture.adapter.toFixtureDisplayModel
import com.lucas.soccerchallenge.ui.results.adapter.ResultDisplayModel
import com.lucas.soccerchallenge.ui.results.adapter.toResultDisplayModel
import com.lucas.soccerchallenge.utils.Resource
import com.soccerchallenge.domain.model.MatchType
import com.soccerchallenge.domain.usecase.FetchMatchUseCase
import com.soccerchallenge.domain.util.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MatchDetailViewModel @Inject constructor(
    private val fetchMatchUseCase: FetchMatchUseCase
) : ViewModel() {

    private val _fixture = MutableStateFlow<Resource<FixtureDisplayModel>>(Resource.Initialize())
    val fixture = _fixture.asStateFlow()

    private val _result = MutableStateFlow<Resource<ResultDisplayModel>>(Resource.Initialize())
    val result = _result.asStateFlow()

    fun fetchMatch(matchId: Int) {
        viewModelScope.launch {
            val response = fetchMatchUseCase.execute(matchId)
            if (response is Response.Success) {
                val match = response.data
                when (match.type) {
                    MatchType.FIXTURE -> {
                        _fixture.value = Resource.Success(match.toFixtureDisplayModel())
                    }

                    MatchType.RESULT -> {
                        _result.value = Resource.Success(match.toResultDisplayModel())
                    }
                }
            }
        }
    }

}
