package com.uszkaisandor.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}