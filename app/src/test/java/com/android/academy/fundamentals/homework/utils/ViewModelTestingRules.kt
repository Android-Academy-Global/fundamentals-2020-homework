package com.android.academy.fundamentals.homework.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.rules.RuleChain

fun viewModelTestingRules(): RuleChain =
    RuleChain
        .outerRule(InstantTaskExecutorRule())
        .around(MainCoroutineDispatcherRule())