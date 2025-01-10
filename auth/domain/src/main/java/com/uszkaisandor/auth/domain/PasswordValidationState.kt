package com.uszkaisandor.auth.domain

data class PasswordValidationState(
    val hasMinimumLength: Boolean = false,
    val hasDigit: Boolean = false,
    val hasLowerCaseCharacter: Boolean = false,
    val hasUpperCaseCharacter: Boolean = false,
) {
    val isValidPassword: Boolean
        get() = hasMinimumLength && hasDigit && hasLowerCaseCharacter && hasUpperCaseCharacter
}
