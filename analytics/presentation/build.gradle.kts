plugins {
    alias(libs.plugins.runique.android.feature.ui)
}

android {
    namespace = "com.uszkaisandor.analytics.presentation"
}

dependencies {
    implementation(projects.analytics.domain)
}