plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt") // Enable Kotlin Annotation Processing Tool (KAPT) for Moshi code generation

}

android {
    namespace = "com.example.c7_2"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.c7_2"
        minSdk = 35
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.coil.compose)

    implementation(libs.retrofit) //Retrofit for network calls
    implementation(libs.converter.moshi) // Moshi converter for Retrofit
    implementation(libs.moshi.kotlin) //Moshi for JSON parsing
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.14.0") // Moshi code generation
    implementation(libs.okhttp) // OkHttp for network layer
    implementation(libs.androidx.lifecycle.viewmodel.compose) // ViewModel Compose integration
    implementation(libs.androidx.lifecycle.runtime.compose) // Lifecycle runtime Compose integration

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}