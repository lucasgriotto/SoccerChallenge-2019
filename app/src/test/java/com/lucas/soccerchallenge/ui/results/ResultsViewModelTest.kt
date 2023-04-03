package com.lucas.soccerchallenge.ui.results

import com.lucas.soccerchallenge.ui.results.usecase.FetchMatchResultsUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ResultsViewModelTest {

    private lateinit var viewModel: ResultsViewModel

    @MockK(relaxed = true)
    private lateinit var fetchMatchResultsUseCase: FetchMatchResultsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ResultsViewModel(fetchMatchResultsUseCase)
    }

    @Test
    fun `should fetch results when view model is created`() {
        verify { fetchMatchResultsUseCase.execute(any(), Unit) }
    }

}