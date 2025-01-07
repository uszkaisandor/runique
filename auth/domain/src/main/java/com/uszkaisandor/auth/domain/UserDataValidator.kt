package com.uszkaisandor.auth.domain

class UserDataValidator(
    private val patternValidator: PatternValidator
) {

    fun isValidEmail(email: String): Boolean = patternValidator.matches(email.trim())

    fun validatePassword(password: String): PasswordValidationState {
        return PasswordValidationState(
            hasMinimumLength = password.length >= MIN_PASSWORD_LENGTH,
            hasDigit = password.any { it.isDigit() },
            hasUpperCaseCharacter = password.any { it.isUpperCase() },
            hasLowerCaseCharacter = password.any { it.isLowerCase() }

        )
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 9
    }
}