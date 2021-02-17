package com.android.academy.fundamentals.homework.features.moviedetails

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
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
import com.android.academy.fundamentals.homework.model.Movie
import com.google.android.material.transition.MaterialContainerTransform

class MovieDetailsFragment : Fragment() {

    private val viewModel: MovieDetailsViewModelImpl by viewModels {
        MovieDetailsViewModelFactory((requireActivity() as MovieRepositoryProvider).provideMovieRepository())
    }

    private var listener: MovieDetailsBackClickListener? = null

    private val logoImageView: ImageView by lazy {
        requireView().findViewById(R.id.movie_logo_image)
    }

    private val restrictionsTextView: TextView by lazy {
        requireView().findViewById(R.id.movie_age_restrictions_text)
    }
    private val nameTextView: TextView by lazy {
        requireView().findViewById(R.id.movie_name_text)
    }
    private val tagsTextView: TextView by lazy {
        requireView().findViewById(R.id.movie_tags_text)
    }
    private val reviewsCountTextView: TextView by lazy {
        requireView().findViewById(R.id.movie_reviews_count_text)
    }
    private val storylineTextView: TextView by lazy {
        requireView().findViewById(R.id.movie_storyline_text)
    }
    private val starImageViews: List<ImageView> by lazy {
        listOf(
            requireView().findViewById(R.id.star1_image),
            requireView().findViewById(R.id.star2_image),
            requireView().findViewById(R.id.star3_image),
            requireView().findViewById(R.id.star4_image),
            requireView().findViewById(R.id.star5_image),
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MovieDetailsBackClickListener) {
            listener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.container
            duration = resources.getInteger(R.integer.movies_motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.background_color
                )
            )
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
                is MovieDetailsViewState.MovieLoaded -> bindUI(view, state.movie)
                MovieDetailsViewState.NoMovie -> showMovieNotFoundError()
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

    private fun bindUI(view: View, movie: Movie) {
        updateMovieDetailsInfo(movie)

        val adapter =
            view.findViewById<RecyclerView>(R.id.recycler_movies).adapter as ActorsListAdapter
        adapter.submitList(movie.actors)
    }

    override fun onDetach() {
        listener = null

        super.onDetach()
    }

    private fun updateMovieDetailsInfo(movie: Movie) {
        logoImageView.load(
            movie.detailImageUrl,
            // https://github.com/coil-kt/coil/issues/650#issuecomment-774776210
            builder = {
                target { result -> logoImageView.setImageDrawable(result) }
            }
        )

        restrictionsTextView.text =
            getString(R.string.movies_list_allowed_age_template, movie.pgAge)

        nameTextView.text = movie.title
        tagsTextView.text = movie.genres.joinToString { genre -> genre.name }
        reviewsCountTextView.text =
            getString(R.string.movies_list_reviews_template, movie.reviewCount)
        storylineTextView.text = movie.storyLine
        starImageViews.forEachIndexed { index, imageView ->
            val colorId = if (movie.rating > index) R.color.pink_light else R.color.gray_dark
            ImageViewCompat.setImageTintList(
                imageView,
                ColorStateList.valueOf(ContextCompat.getColor(imageView.context, colorId))
            )
        }
    }

    interface MovieDetailsBackClickListener {

        fun onMovieDeselected()
    }

    companion object {

        private const val PARAM_MOVIE_ID = "movie_id"

        fun create(movieId: Int) = MovieDetailsFragment().also {
            val args = bundleOf(
                PARAM_MOVIE_ID to movieId,
            )
            it.arguments = args
        }
    }
}
