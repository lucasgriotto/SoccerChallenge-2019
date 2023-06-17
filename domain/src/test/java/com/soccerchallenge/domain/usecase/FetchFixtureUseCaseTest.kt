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
class FetchFixtureUseCaseTest {

    private lateinit var useCase: FetchFixtureUseCase

    @MockK
    private lateinit var repository: SoccerRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        useCase = FetchFixtureUseCase(repository)
    }

    @Test
    fun `should execute fetch fixture`() = runTest {
        val forceRefresh = true
        useCase.execute(forceRefresh)
        coVerify { repository.fetchFixture(forceRefresh) }
    }

}
