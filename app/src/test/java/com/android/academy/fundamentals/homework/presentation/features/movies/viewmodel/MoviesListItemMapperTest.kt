package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import com.android.academy.fundamentals.homework.R
import com.android.academy.fundamentals.homework.common.text.NativeText
import com.android.academy.fundamentals.homework.common.time.CurrentTimeProviderStub
import com.android.academy.fundamentals.homework.model.createMovie
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import kotlin.test.assertEquals

class MoviesListItemMapperTest {

    private fun createMapper(
        currentTime: LocalDateTime = LocalDateTime.of(2021, Month.SEPTEMBER, 29, 12, 0, 0, 0)
    ) = MoviesListItemMapper(CurrentTimeProviderStub(currentTime))

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
    fun `map movie that's released today`() {
        val today = LocalDateTime.of(2021, Month.SEPTEMBER, 29, 12, 0, 0, 0)
        val mapper = createMapper(currentTime = today)
        val movie = createMovie(releaseDate = LocalDate.of(2021, Month.SEPTEMBER, 29))

        val listItem = mapper.map(movie)

        assertEquals(NativeText.Resource(R.string.movies_list_released_today), listItem.release)
    }

    @Test
    fun `map movie that's released 50 days ago`() {
        val today = LocalDateTime.of(2021, Month.SEPTEMBER, 29, 12, 0, 0, 0)
        val mapper = createMapper(currentTime = today)
        val movie = createMovie(releaseDate = LocalDate.of(2021, Month.AUGUST, 10))

        val listItem = mapper.map(movie)

        assertEquals(
            NativeText.Plural(R.plurals.movies_list_days_after_release, 50, listOf(50)),
            listItem.release
        )
    }

    @Test
    fun `map movie that will be released tomorrow`() {
        val today = LocalDateTime.of(2021, Month.SEPTEMBER, 29, 12, 0, 0, 0)
        val mapper = createMapper(currentTime = today)
        val movie = createMovie(releaseDate = LocalDate.of(2021, Month.SEPTEMBER, 30))

        val listItem = mapper.map(movie)

        assertEquals(
            NativeText.Plural(R.plurals.movies_list_days_before_release, 1, listOf(1)),
            listItem.release
        )
    }
}