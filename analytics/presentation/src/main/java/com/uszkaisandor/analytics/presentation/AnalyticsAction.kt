package com.uszkaisandor.analytics.presentation

sealed interface AnalyticsAction {
    data object OnBackClick : AnalyticsAction

}