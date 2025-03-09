plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"
}

hilt {
    enableAggregatingTask = false
}

android {
    namespace = "com.geko.challenge"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.geko.challenge"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isShrinkResources = true

            isMinifyEnabled = true
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
}

// https://github.com/JetBrains/kotlin/blob/master/plugins/compose/design/compiler-metrics.md
// ./gradlew assembleRelease -PenableComposeCompilerMetrics=true -PenableComposeCompilerReports=true
composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose-reports")
    metricsDestination = layout.buildDirectory.dir("compose-reports")
}

dependencies {
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.room.runtime)

    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    ksp(libs.androidx.room.compiler)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)
    //taken from here https://developer.android.com/jetpack/androidx/releases/compose-material3-adaptive
    implementation(libs.androidx.adaptive)
    implementation(libs.androidx.adaptive.layout)
    implementation("androidx.compose.material3.adaptive:adaptive-navigation:1.1.0-rc01")
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp)
    implementation(libs.navigation.compose)
    implementation(libs.hilt.compose)
    implementation(libs.dagger.hilt)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    ksp(libs.dagger.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}