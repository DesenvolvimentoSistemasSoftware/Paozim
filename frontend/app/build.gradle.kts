plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.plserialization)
    kotlin("kapt")
}

android {
    namespace = "com.mobile.paozim"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mobile.paozim"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
}

dependencies {
    val navVersion = "2.7.7"
    val intuitVersion = "1.0.6"
    val gifVersion = "1.2.17"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // intuit
    implementation("com.intuit.sdp:sdp-android:$intuitVersion")
    implementation("com.intuit.ssp:ssp-android:$intuitVersion")

    // gif
    implementation("pl.droidsonroids.gif:android-gif-drawable:$gifVersion")

    // Kotlin Navigation component
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //Image load
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation(libs.picasso)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okHttp)
    implementation(libs.series.json)
    implementation(libs.serialization.converter)

    //Dagger - Hilt
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    //Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
