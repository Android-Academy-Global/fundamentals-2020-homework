package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import com.android.academy.fundamentals.homework.R
import com.android.academy.fundamentals.homework.common.text.NativeText
import com.android.academy.fundamentals.homework.model.createMovie
import org.junit.Test
import java.time.LocalDate
import kotlin.test.assertEquals

class MoviesListItemMapperTest {

    private fun createMapper() = MoviesListItemMapper()

    @Test
    fun `mapper maps some fields as is`() {
        val mapper = createMapper()
        val movie = createMovie(
            id = 671039,
            title = "Test 1",
            reviewCount = 200,
            isLiked = true,
            pgAge = 15,
            runningTime = 55,
            imageUrl = "test url",
        )

        val listItem = mapper.map(movie)

        assertEquals(671039, listItem.id)
        assertEquals("Test 1", listItem.title)
        assertEquals(200, listItem.reviewCount)
        assertEquals(true, listItem.isLiked)
        assertEquals(15, listItem.pgAge)
        assertEquals(55, listItem.runningTime)
        assertEquals("test url", listItem.imageUrl)
    }

    @Test
    fun `map movie without url`() {
        val mapper = createMapper()
        val movie = createMovie(imageUrl = null)

        val listItem = mapper.map(movie)

        assertEquals(null, listItem.imageUrl)
    }

    @Test
    fun `map movie that's released today`() {
        val mapper = createMapper()
        val movie = createMovie(releaseDate = LocalDate.of(2021, 10, 2))

        val listItem = mapper.map(movie)

        assertEquals(NativeText.Resource(R.string.movies_list_released_today), listItem.release)
    }
}