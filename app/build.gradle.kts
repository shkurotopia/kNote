import com.android.build.api.dsl.Packaging

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "xyz.shkurotopia.knote"
    compileSdk = 33

    defaultConfig {
        applicationId = "xyz.shkurotopia.knote"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources =true

            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        dataBinding = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    fun Packaging.() {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.work:work-runtime-ktx:2.8.0")
    implementation("androidx.navigation:navigation-runtime-ktx:2.5.3")
    val composeBom = platform("androidx.compose:compose-bom:2023.01.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation(("androidx.work:work-runtime:2.8.0"))
    implementation("androidx.activity:activity-compose:1.6.1")

    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-android-compiler:2.45")

    implementation("androidx.room:room-runtime:2.5.0")
    kapt("androidx.room:room-compiler:2.5.0")

    implementation("androidx.room:room-ktx:2.5.0")

    implementation("io.noties.markwon:core:4.6.2")
    implementation("io.noties.markwon:editor:4.6.2")
    implementation("io.noties.markwon:ext-strikethrough:4.6.2")
    implementation("io.noties.markwon:ext-tasklist:4.6.2")
    implementation("io.noties.markwon:image-glide:4.6.2")
    implementation("io.noties.markwon:linkify:4.6.2")

    testImplementation("junit:junit:4.13.2")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}