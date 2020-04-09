package com.lucas.soccerchallenge.repository

import com.lucas.soccerchallenge.MATCH1
import com.lucas.soccerchallenge.MATCH_ENTITY
import com.lucas.soccerchallenge.api.SoccerService
import com.lucas.soccerchallenge.base.networking.Resource
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.data.toMatch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.`when` as whenever
import org.mockito.Mockito.mock
import retrofit2.Response


class SoccerRepositoryImplTest {

    lateinit var service: SoccerService
    lateinit var repository: SoccerRepositoryImpl
    lateinit var responseSpy: ResponseSpy

    @Before
    fun setUp() {
        service = mock(SoccerService::class.java)
        responseSpy = ResponseSpy()
    }

    @Test
    fun `get fixture returns one match`() = runBlockingTest {
        whenever(service.getFixture()).thenReturn(Response.success(listOf(MATCH_ENTITY)))
        repository = SoccerRepositoryImpl(service)

        repository.getFixture { responseSpy.spy(it) }

        assertEquals(MATCH_ENTITY.toMatch(), responseSpy.value?.first())
    }

    @Test
    fun `get results returns one match`() = runBlockingTest {
        whenever(service.getResults()).thenReturn(Response.success(listOf(MATCH_ENTITY)))
        repository = SoccerRepositoryImpl(service)

        repository.getResults { responseSpy.spy(it) }

        assertEquals(MATCH_ENTITY.toMatch(), responseSpy.value?.first())
    }
}

class ResponseSpy {
    var value: List<Match>? = null
    var error: String = ""
    fun spy(resource: Resource<List<Match>>) {
        when(resource) {
            is Resource.Success -> {
                value = resource.data
            }
            is Resource.Error -> {
                error = resource.message
            }
        }
    }
}