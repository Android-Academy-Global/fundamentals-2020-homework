package com.android.academy.fundamentals.homework


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.android.academy.fundamentals.homework.common.text.NativeText
import com.android.academy.fundamentals.homework.common.text.toCharSequence
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PluralsTest {
    @Test
    fun oneDayBeforeRelease() {
        val text = NativeText.Plural(
            R.plurals.movies_list_days_before_release,
            1,
            listOf(1)
        )

        val formattedString = text.toCharSequence(context)

        assertEquals("1 day before release", formattedString)
    }

    @Test
    fun thirtySixDaysBeforeRelease() {
        val text = NativeText.Plural(
            R.plurals.movies_list_days_before_release, 36,
            listOf(36)
        )

        val formattedString = text.toCharSequence(context)

        assertEquals("36 days before release", formattedString)
    }
}

private val context get() = InstrumentationRegistry.getInstrumentation().targetContext