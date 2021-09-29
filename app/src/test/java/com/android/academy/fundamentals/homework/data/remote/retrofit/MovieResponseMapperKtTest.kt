package com.android.academy.fundamentals.homework.data.remote.retrofit

import org.junit.Test
import kotlin.test.assertEquals

class MovieResponseMapperKtTest {

    @Test
    fun `spectators age mapping`() {
        val response = createMovieResponse(adult = true)
        val model = response.toMovie()
        assertEquals(16, model.pgAge)
    }
}