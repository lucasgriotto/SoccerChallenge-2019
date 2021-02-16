package com.lucas.soccerchallenge.features.fixture

import com.lucas.soccerchallenge.features.fixture.usecase.FetchFixtureUseCase
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
    fun `test fetchFixtureUseCase observed and executed`() {
        verify { fetchFixtureUseCase.observe() }
        verify { fetchFixtureUseCase.execute(any()) }
    }

}