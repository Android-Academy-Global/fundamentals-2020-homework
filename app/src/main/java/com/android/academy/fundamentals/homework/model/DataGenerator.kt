package com.android.academy.fundamentals.homework.model

import com.android.academy.fundamentals.homework.R

object DataGenerator {

    fun generateMovieList() = arrayListOf(
        MovieData(
            1,
            13,
            "Avengers: End Game",
            "Action, Adventure, Drama",
            "137 min",
            125,
            false,
            4,
            R.drawable.img_movies_item_header_avengers,
            R.drawable.img_movie_details_header,
            "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\' actions and restore balance to the universe.",
            arrayListOf(
                ActorData(1, "Robert Downey Jr.", R.drawable.img_movie_cast_1),
                ActorData(2, "Chris Evans", R.drawable.img_movie_cast_2),
                ActorData(3, "Mark Ruffalo", R.drawable.img_movie_cast_3),
                ActorData(4, "Chris Hemsworth", R.drawable.img_movie_cast_4),
            )
        ),
        MovieData(
            2,
            16,
            "Tenet",
            "Action, Sci-Fi, Thriller",
            "97 min",
            98,
            true,
            5,
            R.drawable.img_movies_item_header_tenet,
            R.drawable.img_movie_details_header,
            "A secret agent embarks on a dangerous, time-bending mission to prevent the start of World War III.",
            arrayListOf(
                ActorData(2, "Chris Evans", R.drawable.img_movie_cast_2),
                ActorData(1, "Robert Downey Jr.", R.drawable.img_movie_cast_1),
                ActorData(3, "Mark Ruffalo", R.drawable.img_movie_cast_3),
                ActorData(4, "Chris Hemsworth", R.drawable.img_movie_cast_4),
            )
        ),
        MovieData(
            3,
            13,
            "Black Widow",
            "Action, Adventure, Sci-Fi",
            "102 min",
            38,
            false,
            4,
            R.drawable.img_movies_item_header_black_widow,
            R.drawable.img_movie_details_header,
            "At birth the Black Widow (aka Natasha Romanova) is given to the KGB, which grooms her to become its ultimate operative. When the U.S.S.R. breaks up, the government tries to kill her as the action moves to present-day New York, where she is a freelance operative.",
            arrayListOf(
                ActorData(3, "Mark Ruffalo", R.drawable.img_movie_cast_3),
                ActorData(1, "Robert Downey Jr.", R.drawable.img_movie_cast_1),
                ActorData(2, "Chris Evans", R.drawable.img_movie_cast_2),
                ActorData(4, "Chris Hemsworth", R.drawable.img_movie_cast_4),
            )
        ),
        MovieData(
            4,
            13,
            "Wonder Woman 1984",
            "Action, Adventure, Fantasy",
            "120 min",
            74,
            false,
            5,
            R.drawable.img_movies_item_header_ww84,
            R.drawable.img_movie_details_header,
            "Wonder Woman squares off against Maxwell Lord and the Cheetah, a villainess who possesses superhuman strength and agility.",
            arrayListOf(
                ActorData(4, "Chris Hemsworth", R.drawable.img_movie_cast_4),
                ActorData(1, "Robert Downey Jr.", R.drawable.img_movie_cast_1),
                ActorData(2, "Chris Evans", R.drawable.img_movie_cast_2),
                ActorData(3, "Mark Ruffalo", R.drawable.img_movie_cast_3),
            )
        )
    )

}