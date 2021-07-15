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
    if (name == "preBuild") {
        // update dir distribution
        "${rootProject.rootDir}/distribution".let {
            // add dir if not exist
            mkdir(it)
            // make data for  https://firebase.google.com/docs/app-distribution
            updateTesters(it)
            createNote(it)
        }
    }
}

// create note from git commits
fun createNote(dir: String, defaultNote: String = "Minor fixes") {
    println("> Task :app:createNote")
    // get topical git log & write to file
    File("$dir/note").let { file ->
        file.createNewFile()
        val time = file.lastModified()
        val log = """git log --pretty=format:"(%ad, %an) %s" --date=format:"%Y-%m-%d %H:%M:%S" --after=$time""".exec()
        file.writeText(log.ifEmpty { defaultNote })
    }
}

// download before build file with list testers
fun updateTesters(dir: String) {
    println("> Task :app:updateTesters")
    // download testers
    ant.invokeMethod(
        "get", mapOf(
            "src" to "https://raw.githubusercontent.com/keygenqt/android-AutoAppDistribution/master/data/testers",
            "dest" to dir
        )
    )
}

// execute command
fun String.exec(): String = java.io.ByteArrayOutputStream().let {
    project.exec {
        workingDir = file("./")
        commandLine = this@exec.split("\\s".toRegex())
        standardOutput = it
    }
    return String(it.toByteArray())
}