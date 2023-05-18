plugins {
    `kotlin-dsl`
}
repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(libs.kgp)
    implementation("com.android.tools.build:gradle:8.0.1")
//    implementation(libs.agp)
    implementation("com.squareup:javapoet:1.13.0")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}