package com.android.academy.fundamentals.homework.data

import com.android.academy.fundamentals.homework.model.Actor
import com.android.academy.fundamentals.homework.model.Genre
import com.android.academy.fundamentals.homework.model.Movie
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class JsonLoadKtTest {
    @Test
    fun `Parse genres json`() {
        val data = JsonLoadKtTest::class.java.getResource("/json/genres.json")?.readText()

        val genres = parseGenres(data!!)

        assertThat(genres).contains(buildGenre())
    }

    @Test
    fun `Parse actors json`() {
        val data = JsonLoadKtTest::class.java.getResource("/json/people.json")?.readText()

        val actors = parseActors(data!!)

        assertThat(actors).contains(
            Actor(
                id = 62,
                name = "Bruce Willis",
                imageUrl = "https://image.tmdb.org/t/p/w342/A1XBu3CffBpSK8HEIJM8q7Mn4lz.jpg"
            )
        )
    }

    @Test
    fun `Parse movies json`() {
        var data = JsonLoadKtTest::class.java.getResource("/json/genres.json")?.readText()
        val genres = parseGenres(data!!)

        data = JsonLoadKtTest::class.java.getResource("/json/people.json")?.readText()
        val actors = parseActors(data!!)

        data = JsonLoadKtTest::class.java.getResource("/json/data.json")?.readText()
        val movies = parseMovies(data!!, genres, actors)

        assertThat(movies).contains(
            Movie(
                id = 671039,
                title = "Rogue City",
                storyLine = "Caught in the crosshairs of police corruption and Marseille’s warring gangs, a loyal cop must protect his squad by taking matters into his own hands.",
                imageUrl = "https://image.tmdb.org/t/p/w342/9HT9982bzgN5on1sLRmc1GMn6ZC.jpg",
                detailImageUrl = "https://image.tmdb.org/t/p/w342/gnf4Cb2rms69QbCnGFJyqwBWsxv.jpg",
                rating = 3,
                reviewCount = 200,
                pgAge = 13,
                runningTime = 116,
                genres = listOf(
                    Genre(id = 53, name = "Thriller"),
                    Genre(id = 28, name = "Action"),
                    buildGenre(),
                    Genre(id = 80, name = "Crime"),
                ),
                actors = listOf(
                    Actor(
                        id = 84433,
                        name = "Lannick Gautry",
                        imageUrl = "https://image.tmdb.org/t/p/w342/h94QUkKrwUncYhJ1eMz23kBAJuc.jpg"
                    ),
                    Actor(
                        id = 37919,
                        name = "Stanislas Merhar",
                        imageUrl = "https://image.tmdb.org/t/p/w342/k5XQM1XXG71GLd41hFcx8yhkxRy.jpg"
                    ),
                    Actor(
                        id = 1407184,
                        name = "Kaaris",
                        imageUrl = "https://image.tmdb.org/t/p/w342/zHxqojtMgk5aBqMQyTqcoPc4TRG.jpg"
                    ),
                    Actor(
                        id = 1003,
                        name = "Jean Reno",
                        imageUrl = "https://image.tmdb.org/t/p/w342/q7dYamebioHRuvb9EWeSw8yTEfS.jpg"
                    ),
                    Actor(
                        id = 62439,
                        name = "David Belle",
                        imageUrl = "https://image.tmdb.org/t/p/w342/nxRnf3O6Y3Yldd0pibuo43x3RZ.jpg"
                    ),
                    Actor(
                        id = 70165,
                        name = "Gérard Lanvin",
                        imageUrl = "https://image.tmdb.org/t/p/w342/3FSr4VW7JF3mBcVwPnJmOeR09NB.jpg"
                    ),
                ),
                isLiked = false
            )
        )
    }

    private fun buildGenre() = Genre(id = 18, name = "Drama")
}