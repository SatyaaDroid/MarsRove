plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlinKsp)
}

android {
    namespace = "com.app.marsrover"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.marsrover"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.lifecycle.runtime.compose)
// navigation
    implementation(libs.androidx.navigation)
    //retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.convert.gson)   // retrofit converter gson
    implementation(libs.retrofit.rxjava2) // rxjava2

    // okhttp3 Bom
    implementation(platform(libs.okhttp3.bom))
    implementation(libs.okhttp3) // okhttp3
    implementation(libs.okhttp3.logging) // okhttp3 logging interceptor

    implementation(libs.gson)// google Gson

    //hilt
    implementation(libs.googleHiltAndroid)
    ksp (libs.googleHiltCompiler)
    implementation(libs.composeHilt)

    //coil
    implementation(libs.coil)

    //Room
    implementation(libs.androidx.roomRuntime)
    ksp(libs.androidx.roomCompiler)
    implementation(libs.androidx.roomKtx)
    implementation(libs.androidx.roomTesting)

    //font
    implementation(libs.androidx.font)




}