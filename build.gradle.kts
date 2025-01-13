// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.devtoolsKsp)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    alias(libs.plugins.compose.compiler) apply false
}