package com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel

import com.android.academy.fundamentals.homework.model.Movie
import org.junit.Test
import kotlin.test.assertEquals

class MoviesListItemMapperTest {

    //TODO 1.6 Run test and see green result
    @Test
    fun `mapper maps some fields as is`() {
        //TODO 1.1 Arrange: Create instance of MovieListItemMapper

        //TODO 3.2 Use createMovie() factory method instead of Movie constructor

        /* TODO 1.2 Arrange: Uncomment Movie instance
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
        */

        //TODO 1.3 Act: Use created MovieListItemMapper to map Movie into MoviesListItem

        //TODO 1.4 Assert: Create instance of MoviesListItem with same data as in Movie model

        //TODO 1.5 Assert: Use assertEquals([expected], [actual]) to check equality of objects
        // [expected] - model, you've created in step 1.4
        // [actual] - mapped model, you've got in step 1.3

        /* TODO 3.3 Instead of assertEquals for whole model use one assertEquals for each parameter:
        assertEquals(671039, mappedItem.id)
        assertEquals("Test 1", mappedItem.title)
        assertEquals(200, mappedItem.reviewCount)
        assertEquals(true, mappedItem.isLiked)
        assertEquals(15, mappedItem.pgAge)
        assertEquals(55, mappedItem.runningTime)
        assertEquals("test url", mappedItem.imageUrl)
        */

    }

    /*TODO 3.4 Uncomment createMapper() factory method and use it instead of MoviesListItemMapper constructor
       private fun createMapper() = MoviesListItemMapper()
     */
}