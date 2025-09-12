import org.eclipse.jgit.api.Git
import org.eclipse.jgit.internal.storage.file.FileRepository

buildscript {
    dependencies {
        classpath(libs.org.eclipse.jgit)
    }
}

val repo = FileRepository(rootProject.file(".git"))
val refId = repo.refDatabase.exactRef("refs/remotes/origin/main").objectId!!
val commitCount = Git(repo).log().add(refId).call().count()

val verCode by extra(commitCount)
val verName by extra("0.4")

val androidTargetSdkVersion by extra(34)
val androidCompileSdkVersion by extra(34)

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.compose.compiler) apply false

    // Google Firebase Crashlytics
    id("com.google.gms.google-services") version "4.4.3" apply false
    id("com.google.firebase.crashlytics") version "3.0.6" apply false
}