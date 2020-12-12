package com.android.academy.fundamentals.homework.features.moviedetails

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.R
import com.android.academy.fundamentals.homework.model.MovieData


class MovieDetailsFragment : Fragment() {

    var listener: MovieDetailsBackClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MovieDetailsBackClickListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieData = arguments?.getSerializable(PARAM_MOVIE_DATA) as? MovieData ?: return

        updateMovieDetailsInfo(movieData)

        view.findViewById<RecyclerView>(R.id.recycler_movies).apply {

            this.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            val adapter = ActorsListAdapter()

            this.adapter = adapter

            adapter.submitList(movieData.actors)
        }

        view.findViewById<View>(R.id.back_button_layout)?.setOnClickListener {
            listener?.onMovieDeselected()
        }
    }

    override fun onDetach() {
        listener = null

        super.onDetach()
    }

    private fun updateMovieDetailsInfo(movieData: MovieData) {
        view?.findViewById<ImageView>(R.id.movie_logo_image)
            ?.setImageResource(movieData.detailImageRes)

        view?.findViewById<TextView>(R.id.movie_age_restrictions_text)?.text =
            context?.getString(R.string.movies_list_allowed_age_template, movieData.pgAge)

        view?.findViewById<TextView>(R.id.movie_name_text)?.text = movieData.title
        view?.findViewById<TextView>(R.id.movie_tags_text)?.text = movieData.genre
        view?.findViewById<TextView>(R.id.movie_reviews_count_text)?.text =
            context?.getString(R.string.movies_list_reviews_template, movieData.reviewCount)
        view?.findViewById<TextView>(R.id.movie_storyline_text)?.text = movieData.storyLine

        val starsImages = listOf<ImageView?>(
            view?.findViewById(R.id.star1_image),
            view?.findViewById(R.id.star2_image),
            view?.findViewById(R.id.star3_image),
            view?.findViewById(R.id.star4_image),
            view?.findViewById(R.id.star5_image)
        )
        starsImages.forEachIndexed { index, imageView ->
            imageView?.let {
                val colorId =
                    if (movieData.rating > index) R.color.pink_light else R.color.gray_dark
                ImageViewCompat.setImageTintList(
                    imageView, ColorStateList.valueOf(
                        ContextCompat.getColor(imageView.context, colorId)
                    )
                )
            }
        }
    }

    interface MovieDetailsBackClickListener {
        fun onMovieDeselected()
    }

    companion object {
        private const val PARAM_MOVIE_DATA = "movie_data"

        fun create(movieData: MovieData) = MovieDetailsFragment().also {
            val args = bundleOf(
                PARAM_MOVIE_DATA to movieData
            )
            it.arguments = args
        }
    }
}