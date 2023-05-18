@file:android.annotation.SuppressLint("GradleDependency", "UseTomlInstead")

plugins {
    id("com.android.application")
    kotlin("android")
//    id(libs.plugins.hilt.get().pluginId)
    id("com.google.dagger.hilt.android")
    kotlin("kapt")

}

android {
    compileSdk = libs.versions.compile.sdk.version.get().toInt()

    defaultConfig {
        minSdk = libs.versions.min.sdk.version.get().toInt()
        targetSdk = libs.versions.target.sdk.version.get().toInt()

        applicationId = AppCoordinates.APP_ID
        versionCode = AppCoordinates.APP_VERSION_CODE
        versionName = AppCoordinates.APP_VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    lint {
        warningsAsErrors = true
        abortOnError = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compilerextension.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.github.jayteealao.pastelmusic.app"

    // Use this block to configure different flavors
//    flavorDimensions("version")
//    productFlavors {
//        create("full") {
//            dimension = "version"
//            applicationIdSuffix = ".full"
//        }
//        create("demo") {
//            dimension = "version"
//            applicationIdSuffix = ".demo"
//        }
//    }
}

// Allow references to generated code
//kapt {
//    correctErrorTypes = true
//}

dependencies {
    implementation(projects.libraryAndroid)
    implementation(projects.libraryCompose)
    implementation(projects.libraryKotlin)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraint.layout)
    implementation(libs.androidx.core.ktx)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
// https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-guava
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-guava:1.6.4")
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.compose)
    implementation(libs.lifecycle.livedata)
    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.coil)
    implementation(libs.bundles.accompanist)
    implementation(libs.bundles.media3)
    implementation("androidx.compose.ui:ui-text-google-fonts:1.2.1")
    kapt("com.google.dagger:hilt-compiler:2.45")
    implementation("com.google.dagger:hilt-android:2.45")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
//    annotationProcessor("com.google.dagger:hilt-compiler:2.45")
    implementation("com.github.theapache64:rebugger:1.0.0-alpha03")

//    https://github.com/google/dagger/issues/3068#issuecomment-999118496
    implementation("com.squareup:javapoet:1.13.0")
//    https://github.com/google/dagger/issues/3383#issuecomment-1121189678
//    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}
