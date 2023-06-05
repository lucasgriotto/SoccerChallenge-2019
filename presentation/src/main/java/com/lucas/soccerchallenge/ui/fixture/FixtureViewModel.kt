package com.lucas.soccerchallenge.ui.fixture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucas.soccerchallenge.ui.fixture.adapter.FixtureDisplayModel
import com.lucas.soccerchallenge.ui.fixture.adapter.toFixtureDisplayModel
import com.lucas.soccerchallenge.utils.Resource
import com.lucas.soccerchallenge.utils.errorfactory.ErrorFactory
import com.soccerchallenge.domain.usecase.FetchFixtureUseCase
import com.soccerchallenge.domain.util.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FixtureViewModel @Inject constructor(
    private val fetchFixtureUseCase: FetchFixtureUseCase
) : ViewModel() {

    private val _fixture = MutableStateFlow<Resource<List<FixtureDisplayModel>>>(Resource.Initialize())
    val fixture = _fixture.asStateFlow()

    fun fetchFixture(forceRefresh: Boolean) {
        _fixture.value = Resource.Loading()
        viewModelScope.launch {
            when (val response = fetchFixtureUseCase.execute(forceRefresh)) {
                is Response.Success -> {
                    val fixtureDisplayModels = response.data.map { it.toFixtureDisplayModel() }
                    _fixture.value = Resource.Success(fixtureDisplayModels)
                }
                is Response.Error -> _fixture.value = Resource.Error(ErrorFactory.getError(response.error))
            }
        }
    }

}
