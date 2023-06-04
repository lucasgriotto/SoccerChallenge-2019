package com.soccerchallenge.domain.usecase

import com.soccerchallenge.domain.repository.UserPreferencesRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SaveSelectedCompetitionsFilterIdsUseCaseTest {

    private lateinit var useCase: SaveSelectedCompetitionFilterIdsUseCase

    @MockK
    private lateinit var repository: UserPreferencesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        useCase = SaveSelectedCompetitionFilterIdsUseCase(repository)
    }

    @Test
    fun `should execute get selected competition filters ids`() = runTest {
        val ids = setOf(1, 2, 3)
        useCase.execute(ids)
        coVerify { repository.saveSelectedCompetitionsFilterIds(ids) }
    }

}
