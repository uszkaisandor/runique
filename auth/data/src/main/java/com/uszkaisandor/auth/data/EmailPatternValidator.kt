package com.uszkaisandor.auth.data

import android.util.Patterns
import com.uszkaisandor.auth.domain.PatternValidator

class EmailPatternValidator : PatternValidator {

    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }

}