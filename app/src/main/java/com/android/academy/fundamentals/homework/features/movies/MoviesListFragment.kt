package com.android.academy.fundamentals.homework.features.movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.academy.fundamentals.homework.R

class MoviesListFragment : Fragment() {

    var listener: MoviesListItemClickListener? = null

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

    override fun onStart() {
        super.onStart()

        view?.findViewById<View>(R.id.movies_list_item_layout)?.setOnClickListener {
            listener?.onMovieSelected()
        }
    }

    override fun onDetach() {
        listener = null

        super.onDetach()
    }

    interface MoviesListItemClickListener {
        fun onMovieSelected()
    }

    companion object {
        fun create() = MoviesListFragment()
    }
}