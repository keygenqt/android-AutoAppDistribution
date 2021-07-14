pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
    }
    plugins {
        id("com.android.application") version "7.1.0-alpha03"
        id("com.android.library") version "7.1.0-alpha03"
        id("org.jetbrains.kotlin.android") version "1.5.10"
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "AutoAppDistribution"
include(":app")
