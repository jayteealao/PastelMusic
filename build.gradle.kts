@file:Suppress("DSL_SCOPE_VIOLATION")
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {

    repositories {
        google()
        mavenCentral()
//        maven { url = java.net.URI("https://jitpack.io") }
    }

//    configurations.all {
//        resolutionStrategy.eachDependency {
//            when (requested.name) {
//                "javapoet" -> useVersion("1.13.0")
//            }
//        }
//    }
}
plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    kotlin("android") apply false
    alias(libs.plugins.hilt) version "2.44" apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.versions)
//    base
}

allprojects {
    group = PUBLISHING_GROUP
}

val ktlintVersion = libs.versions.ktlint.asProvider().get()
val detektFormatting = libs.detekt.formatting

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    detekt {
        config = rootProject.files("config/detekt/detekt.yml")
    }

    dependencies {
        detektPlugins(detektFormatting)
    }
}

tasks {
    withType<DependencyUpdatesTask>().configureEach {
        rejectVersionIf {
            candidate.version.isStableVersion().not()
        }
    }
}
