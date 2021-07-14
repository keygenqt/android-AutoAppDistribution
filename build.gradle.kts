buildscript {

    //////////////////////////////////////
    // Versions
    //////////////////////////////////////

    // base
    extra["coreKtx"] = "1.6.0"
    extra["androidMaterial"] = "1.4.0"

    // firebase
    extra["googleServices"] = "4.3.8"
    extra["firebaseBom"] = "28.1.0"
    extra["appDistribution"] = "2.1.3"

    // compose
    extra["composeVersion"] = "1.0.0-rc01"

    // other
    extra["lottie"] = "1.0.0-rc01-1"

    // test
    extra["junit"] = "4.13.2"
    extra["mockWebserver"] = "4.9.1"
    extra["mockitoCore"] = "3.11.2"

    //////////////////////////////////////
    // Dependencies
    //////////////////////////////////////

    val googleServices: String by extra
    val appDistribution: String by extra

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.google.gms:google-services:$googleServices")
        classpath("com.google.firebase:firebase-appdistribution-gradle:$appDistribution")
    }
}

//////////////////////////////////////
// Tasks
//////////////////////////////////////

// https://docs.gradle.org/current/userguide/build_lifecycle.html
gradle.taskGraph.beforeTask {
    // download before build file with list testers for https://firebase.google.com/docs/app-distribution
    if (name == "preBuild") {
        "${rootProject.rootDir}/distribution".let { path ->
            // add dir if not exist
            mkdir(path)
            // download testers
            ant.invokeMethod(
                "get", mapOf(
                    "src" to "https://raw.githubusercontent.com/keygenqt/android-AutoAppDistribution/master/data/testers",
                    "dest" to path
                )
            )
            // create note
            // @todo add create note
        }
    }
}