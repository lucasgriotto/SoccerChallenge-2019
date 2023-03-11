package com.lucas.soccerchallenge.features.fixture.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lucas.soccerchallenge.core.networking.Resource
import com.lucas.soccerchallenge.data.Match
import com.lucas.soccerchallenge.data.entity.toMatch
import com.lucas.soccerchallenge.repository.SoccerRepository
import com.lucas.soccerchallenge.utils.MainCoroutineRule
import com.lucas.soccerchallenge.utils.ModelCreator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class FetchFixtureUseCaseTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var useCase: FetchFixtureUseCase

    @MockK
    private lateinit var repository: SoccerRepository

    @MockK
    private lateinit var observer: Observer<Resource<List<Match>>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        useCase = FetchFixtureUseCase(repository)
    }

    @Test
    fun `test execute`() {
        val fixture = ModelCreator.fixture.map { it.toMatch() }
        val captors = mutableListOf<Resource<List<Match>>>()
        coEvery { repository.fetchFixture() } returns fixture
        useCase.observe().observeForever(observer)
        mainCoroutineRule.testDispatcher.runBlockingTest {
            useCase.execute(Unit)
            verify(exactly = 2) { observer.onChanged(capture(captors)) }
        }
        assertTrue(captors.first() is Resource.Loading)
        assertTrue(captors.getOrNull(1) is Resource.Success)
        assertEquals(fixture, (captors.getOrNull(1) as Resource.Success).data)
    }

}