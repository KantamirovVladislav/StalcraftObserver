plugins {
    alias(libs.plugins.devtoolsKsp)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("kotlinx-serialization")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.stalcraftobserver"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.stalcraftobserver"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true

        }
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas"
                )
            }
        }
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    annotationProcessor(libs.androidx.room.room.compiler)
    ksp(libs.androidx.room.room.compiler)
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.room.compiler)
    // To use Kotlin Symbol Processing (KSP)
    ksp(libs.androidx.room.room.compiler)

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)

    // optional - RxJava3 support for Room
    implementation(libs.androidx.room.rxjava3)

    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation(libs.androidx.room.guava)

    // optional - Test helpers
    testImplementation(libs.androidx.room.testing)

    // optional - Paging 3 Integration
    implementation(libs.androidx.room.paging)
    ksp(libs.hilt.android.compiler)
    // Coil for async images
    implementation(libs.coil.compose)

    implementation(libs.kotlin.reflect)

    implementation(libs.coil.network.okhttp)
    implementation(libs.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.kotlinx.serialization.json.v163)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.material.icons.extended)

    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m3)
    implementation(libs.vico.views)
    implementation(libs.vico.core)
}