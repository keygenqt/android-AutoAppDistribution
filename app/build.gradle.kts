plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // https://firebase.google.com/docs/app-distribution/android/distribute-gradle
    id("com.google.firebase.appdistribution")

    // https://github.com/diffplug/spotless
    id("com.diffplug.spotless") version "5.12.5"
}

// https://github.com/diffplug/spotless/tree/main/plugin-gradle#kotlin
spotless {
    kotlin {
        target("**/*.kt")
        licenseHeaderFile("${rootProject.rootDir}/spotless.license")
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}

android {

    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.keygenqt.auto_distribution"
        minSdk = 23
        targetSdk = 30
        versionCode = 5
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            // Configure distribution properties
            // gradlew assembleDebug appDistributionUploadDebug
            // https://firebase.google.com/docs/app-distribution/android/distribute-gradle
            firebaseAppDistribution {
                appId = "1:841649622778:android:0d0b2ae9be211ce8600343"
                artifactType = "APK"
                serviceCredentialsFile = "${project.rootDir}/KEY/autoappdistribution-7b79e1dbd654.json"
                releaseNotesFile = "${project.rootDir}/distribution/note"
                testersFile = "${project.rootDir}/distribution/testers"
            }
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["composeVersion"] as String
    }
}

dependencies {

    val coreKtx: String by rootProject.extra
    val androidMaterial: String by rootProject.extra
    val composeVersion: String by rootProject.extra
    val firebaseBom: String by rootProject.extra
    val lottie: String by rootProject.extra
    val junit: String by rootProject.extra
    val mockWebserver: String by rootProject.extra
    val mockitoCore: String by rootProject.extra

    // base
    implementation("androidx.core:core-ktx:$coreKtx")
    implementation("com.google.android.material:material:$androidMaterial")

    // firebase
    implementation(platform("com.google.firebase:firebase-bom:$firebaseBom"))
    implementation("com.google.firebase:firebase-crashlytics")

    // compose
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.activity:activity-compose:1.3.0-rc01")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-alpha08")

    // other
    implementation("com.airbnb.android:lottie:3.7.1")
    implementation("com.airbnb.android:lottie-compose:$lottie")

    // compose test
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

    // unit test
    testImplementation("junit:junit:$junit")
    testImplementation("com.squareup.okhttp3:mockwebserver:$mockWebserver")
    testImplementation("org.mockito:mockito-core:$mockitoCore")
}
