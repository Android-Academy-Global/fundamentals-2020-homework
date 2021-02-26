package com.android.academy.fundamentals.homework.presentation.features.movies.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.R
import com.android.academy.fundamentals.homework.di.MovieRepositoryProvider
import com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel.MovieListViewModelFactory
import com.android.academy.fundamentals.homework.presentation.features.movies.viewmodel.MoviesListViewModelImpl

class MoviesListFragment : Fragment() {

    private val viewModel: MoviesListViewModelImpl by viewModels {
        MovieListViewModelFactory((requireActivity() as MovieRepositoryProvider).provideMovieRepository())
    }

    private var listener: MoviesListItemClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is MoviesListItemClickListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.recycler_movies).apply {
            this.layoutManager = GridLayoutManager(this.context, 2)
            val adapter = MoviesListAdapter { movieData ->
                listener?.onMovieSelected(movieData)
            }

            this.adapter = adapter

            loadDataToAdapter(adapter)
        }
    }

    private fun loadDataToAdapter(adapter: MoviesListAdapter) {
        viewModel.moviesOutput.observe(viewLifecycleOwner, { movieList ->
            adapter.submitList(movieList)
        })
    }

    override fun onDetach() {
        listener = null

        super.onDetach()
    }

    interface MoviesListItemClickListener {
        fun onMovieSelected(movieId: Int)
    }

    companion object {
        fun create() = MoviesListFragment()
    }
}
