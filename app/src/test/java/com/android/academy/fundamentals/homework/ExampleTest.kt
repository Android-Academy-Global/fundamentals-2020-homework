package com.android.academy.fundamentals.homework

import junit.framework.Assert.assertEquals
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class ExampleTest {
    @Test
    fun `test plus operator`() {
        val result = 2 + 2
        val expectedResult = 4
        assertEquals(expectedResult, result)
        assertThat(result).isEqualTo(expectedResult)
    }
}