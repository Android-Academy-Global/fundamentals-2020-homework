package com.android.academy.fundamentals.homework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.academy.fundamentals.homework.utils.viewModelTestingRules
import kotlinx.coroutines.launch
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test

class SimpleViewModel : ViewModel() {
    private val _example = MutableLiveData<Int>()
    val example: LiveData<Int> = _example

    init {
        viewModelScope.launch {
            _example.value = 5
        }
    }
}

class SimpleViewModelTest {

    @get:Rule
    val viewModelRules = viewModelTestingRules()

    @Test
    fun `initialization flow`() {
        val simpleViewModel = SimpleViewModel()
        assertThat(simpleViewModel.example.value).isEqualTo(5)
    }
}