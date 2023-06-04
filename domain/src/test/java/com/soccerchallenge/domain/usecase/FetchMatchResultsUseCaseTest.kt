package com.soccerchallenge.domain.usecase

import com.soccerchallenge.domain.repository.SoccerRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchMatchResultsUseCaseTest {

    private lateinit var useCase: FetchMatchResultsUseCase

    @MockK
    private lateinit var repository: SoccerRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        useCase = FetchMatchResultsUseCase(repository)
    }

    @Test
    fun `should execute fetch match results`() = runTest {
        useCase.execute(true)
        coVerify { repository.fetchMatchResults(true) }
    }

}

