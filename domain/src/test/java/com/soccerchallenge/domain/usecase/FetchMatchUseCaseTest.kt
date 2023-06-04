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
class FetchMatchUseCaseTest {

    private lateinit var useCase: FetchMatchUseCase

    @MockK
    private lateinit var repository: SoccerRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        useCase = FetchMatchUseCase(repository)
    }

    @Test
    fun `should execute fetch match`() = runTest {
        val matchId = 1
        useCase.execute(matchId)
        coVerify { repository.fetchMatch(matchId) }
    }

}
