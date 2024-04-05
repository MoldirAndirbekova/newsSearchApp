plugins {
    id("java-library")
//    id("org.jetbrains.kotlin.jvm")
//    id("org.jetbrains.kotlin.multiplatform") version "1.9.22"
//    id("org.jetbrains.kotlin.plugin.serialization")
//    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization")
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("androidx.annotation:annotation:1.7.1")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:1.6.3")
    implementation ("com.github.skydoves:retrofit-adapters-result:1.0.9")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}