package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import com.android.academy.fundamentals.homework.model.Movie
import org.junit.Test
import kotlin.test.assertEquals

class MoviesListItemMapperTest {

    @Test
    fun `mapper maps some fields as is`() {
        val mapper = MoviesListItemMapper()
        val movie = Movie(
            id = 671039,
            title = "Test 1",
            reviewCount = 200,
            isLiked = true,
            pgAge = 15,
            runningTime = 55,
            imageUrl = "test url",
            genres = listOf(),
            rating = 4
        )

        val listItem = mapper.map(movie)

        assertEquals(
            MoviesListItem(
                id = 671039,
                title = "Test 1",
                reviewCount = 200,
                isLiked = true,
                pgAge = 15,
                runningTime = 55,
                imageUrl = "test url",
                genres = listOf(),
                rating = 4
            ),
            listItem
        )
    }
}