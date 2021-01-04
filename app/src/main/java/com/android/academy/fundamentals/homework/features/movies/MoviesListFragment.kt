package com.android.academy.fundamentals.homework.features.movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.fundamentals.homework.R
import com.android.academy.fundamentals.homework.features.data.loadMovies
import com.android.academy.fundamentals.homework.model.MovieData
import kotlinx.coroutines.*


class MoviesListFragment : Fragment() {

    var listener: MoviesListItemClickListener? = null

    private var coroutinesScope = CoroutineScope(Dispatchers.Main)
    private var movies: List<MovieData>? = null

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
        coroutinesScope.launch {
            val moviesData = movies ?: loadMovies(requireContext())

            withContext(Dispatchers.Main) {
                movies = moviesData
                adapter.submitList(moviesData)
            }
        }
    }

    override fun onDetach() {
        listener = null

        super.onDetach()
    }

    override fun onDestroyView() {
        coroutinesScope.cancel()
        coroutinesScope = CoroutineScope(Dispatchers.Main)

        super.onDestroyView()
    }

    interface MoviesListItemClickListener {
        fun onMovieSelected(movieData: MovieData)
    }

    companion object {
        fun create() = MoviesListFragment()
    }
}