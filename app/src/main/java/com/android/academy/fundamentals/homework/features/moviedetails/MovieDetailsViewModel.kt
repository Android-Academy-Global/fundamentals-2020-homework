package com.android.academy.fundamentals.homework.features.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

internal abstract class MovieDetailsViewModel : ViewModel() {

    abstract val stateOutput: LiveData<MovieDetailsViewState>
}
