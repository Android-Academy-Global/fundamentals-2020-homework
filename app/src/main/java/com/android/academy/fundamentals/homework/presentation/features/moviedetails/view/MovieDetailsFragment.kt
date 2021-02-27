package com.android.academy.fundamentals.homework.presentation.features.moviedetails.view

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.academy.fundamentals.homework.R
import com.android.academy.fundamentals.homework.di.MovieRepositoryProvider
import com.android.academy.fundamentals.homework.extensions.exhaustive
import com.android.academy.fundamentals.homework.model.MovieDetails
import com.android.academy.fundamentals.homework.presentation.features.moviedetails.viewmodel.MovieDetailsViewModelFactory
import com.android.academy.fundamentals.homework.presentation.features.moviedetails.viewmodel.MovieDetailsViewModelImpl
import com.android.academy.fundamentals.homework.presentation.features.moviedetails.viewmodel.MovieDetailsViewState.FailedToLoad
import com.android.academy.fundamentals.homework.presentation.features.moviedetails.viewmodel.MovieDetailsViewState.MovieLoaded
import com.android.academy.fundamentals.homework.presentation.features.moviedetails.viewmodel.MovieDetailsViewState.NoMovie

class MovieDetailsFragment : Fragment() {

    private val viewModel: MovieDetailsViewModelImpl by viewModels {
        MovieDetailsViewModelFactory((requireActivity() as MovieRepositoryProvider).provideMovieRepository())
    }

    private var listener: MovieDetailsBackClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MovieDetailsBackClickListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = arguments?.getSerializable(PARAM_MOVIE_ID) as? Int ?: return

        view.findViewById<RecyclerView>(R.id.recycler_movies).apply {
            this.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            this.adapter = ActorsListAdapter()
        }

        view.findViewById<View>(R.id.back_button_layout)?.setOnClickListener {
            listener?.onMovieDeselected()
        }

        viewModel.loadDetails(movieId)

        viewModel.stateOutput.observe(viewLifecycleOwner, { state ->
            when (state) {
                is MovieLoaded -> bindUI(view, state.movie)
                NoMovie -> showMovieNotFoundError()
                is FailedToLoad -> Toast.makeText(
                    requireContext(),
                    R.string.error_network_failed,
                    Toast.LENGTH_SHORT
                ).show()
            }.exhaustive
        })
    }

    private fun showMovieNotFoundError() {
        Toast.makeText(
            requireContext(),
            R.string.error_movie_not_found,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun bindUI(view: View, movie: MovieDetails) {
        updateMovieDetailsInfo(movie)

        val adapter = view.findViewById<RecyclerView>(R.id.recycler_movies).adapter as ActorsListAdapter
        adapter.submitList(movie.actors)
    }

    override fun onDetach() {
        listener = null

        super.onDetach()
    }

    private fun updateMovieDetailsInfo(movie: MovieDetails) {
        view?.findViewById<ImageView>(R.id.movie_logo_image)?.load(movie.detailImageUrl)

        view?.findViewById<TextView>(R.id.movie_age_restrictions_text)?.text =
            context?.getString(R.string.movies_list_allowed_age_template, movie.pgAge)

        view?.findViewById<TextView>(R.id.movie_name_text)?.text = movie.title
        view?.findViewById<TextView>(R.id.movie_tags_text)?.text = movie.genres.joinToString { it.name }
        view?.findViewById<TextView>(R.id.movie_reviews_count_text)?.text =
            context?.getString(R.string.movies_list_reviews_template, movie.reviewCount)
        view?.findViewById<TextView>(R.id.movie_storyline_text)?.text = movie.storyLine
        val starsImages = listOf<ImageView?>(
            view?.findViewById(R.id.star1_image),
            view?.findViewById(R.id.star2_image),
            view?.findViewById(R.id.star3_image),
            view?.findViewById(R.id.star4_image),
            view?.findViewById(R.id.star5_image)
        )
        starsImages.forEachIndexed { index, imageView ->
            imageView?.let {
                val colorId = if (movie.rating > index) R.color.pink_light else R.color.gray_dark
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

        private const val PARAM_MOVIE_ID = "movie_id"

        fun create(movieId: Int) = MovieDetailsFragment().also {
            val args = bundleOf(
                PARAM_MOVIE_ID to movieId
            )
            it.arguments = args
        }
    }
}
