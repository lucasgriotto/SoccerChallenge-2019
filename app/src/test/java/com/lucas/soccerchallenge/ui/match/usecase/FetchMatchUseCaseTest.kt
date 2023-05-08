package com.lucas.soccerchallenge.ui.match.usecase

import app.cash.turbine.test
import com.lucas.soccerchallenge.core.data.repository.SoccerRepository
import com.lucas.soccerchallenge.core.network.model.mapper.toMatch
import com.lucas.soccerchallenge.ui.base.Resource
import com.lucas.soccerchallenge.utils.ModelCreator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert
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
    fun `should await match when fetch match success`() = runTest {
        val match = ModelCreator.fixture.map { it.toMatch() }.first()
        coEvery { repository.fetchMatch(match.id) } returns match
        useCase.result.test {
            useCase.execute(this@runTest, FetchMatchUseCase.Params(match.id))
            val initialize = awaitItem()
            MatcherAssert.assertThat(initialize, IsInstanceOf.instanceOf(Resource.Initialize::class.java))
            val loading = awaitItem()
            MatcherAssert.assertThat(loading, IsInstanceOf.instanceOf(Resource.Loading::class.java))
            val success = awaitItem()
            MatcherAssert.assertThat(success, IsInstanceOf.instanceOf(Resource.Success::class.java))
            val data = (success as Resource.Success).data
            Assert.assertEquals(match, data)
        }
    }

}
