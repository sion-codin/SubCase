plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.compose.compiler)
    id("org.lsposed.lsplugin.apksign") version "1.4"

    // Google Firebase Crashlytics
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

apksign {
    storeFileProperty = "KEYSTORE_FILE"
    storePasswordProperty = "KEYSTORE_PASSWORD"
    keyAliasProperty = "KEY_ALIAS"
    keyPasswordProperty = "KEY_PASSWORD"
}

val verCode: Int? by rootProject
val verName: String? by rootProject

val androidStoreFile: String? by rootProject
val androidStorePassword: String? by rootProject
val androidKeyAlias: String? by rootProject
val androidKeyPassword: String? by rootProject

android {
    namespace = "ano.subcase"
    compileSdk = 35

    defaultConfig {
        applicationId = "ano.subcase"
        minSdk = 26
        targetSdk = 35
        versionCode = verCode
        versionName = verName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ndk {
            abiFilters.addAll(
                arrayOf(
                    //"armeabi-v7a",
                    "arm64-v8a",
                    // "x86", "x86_64"
                )
            )
        }
    }

    buildTypes {
        debug {}
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        setProperty("archivesBaseName", "SubCase-$verName-$verCode")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    packaging {
        resources.excludes.addAll(listOf("META-INF/*"))
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.timber)

    //com.google.accompanist:accompanist-systemuicontroller
    implementation(libs.accompanist.systemuicontroller)

    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    implementation("com.caoccao.javet:javet-node-android:4.1.7")

    // Google Firebase Crashlytics
    implementation(platform("com.google.firebase:firebase-bom:34.2.0"))
    implementation("com.google.firebase:firebase-crashlytics-ndk")
}
