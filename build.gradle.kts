// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.23" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.7" apply false
}