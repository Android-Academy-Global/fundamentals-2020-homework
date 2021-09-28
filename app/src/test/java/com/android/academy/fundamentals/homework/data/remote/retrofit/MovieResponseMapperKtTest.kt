package com.android.academy.fundamentals.homework.data.remote.retrofit

import org.junit.Test
import kotlin.test.assertEquals

class MovieResponseMapperKtTest {

    @Test
    fun `toMovie if object is marked for adult returns adult age`() {
        val movieResponse = createMovieResponse(adult = true)

        val movie = movieResponse.toMovie()

        assertEquals(16, movie.pgAge)
    }
}

