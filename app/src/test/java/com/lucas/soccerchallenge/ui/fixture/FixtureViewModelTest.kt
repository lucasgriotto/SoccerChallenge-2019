package com.lucas.soccerchallenge.ui.fixture

import androidx.lifecycle.viewModelScope
import com.lucas.soccerchallenge.ui.fixture.usecase.FetchFixtureUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class FixtureViewModelTest {

    private lateinit var viewModel: FixtureViewModel

    @MockK(relaxed = true)
    private lateinit var fetchFixtureUseCase: FetchFixtureUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = FixtureViewModel(fetchFixtureUseCase)
    }

    @Test
    fun `should fetch fixture from local source when view model is created`() {
        verify { fetchFixtureUseCase.execute(viewModel.viewModelScope, FetchFixtureUseCase.Params(false)) }
    }

}