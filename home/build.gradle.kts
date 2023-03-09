plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  kotlin("kapt")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.haroof.home"
  compileSdk = Versions.COMPILE_SDK

  defaultConfig {
    minSdk = Versions.MIN_SDK
    targetSdk = Versions.TARGET_SDK

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.4.0"
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {
  implementation(project(mapOf("path" to ":designsystem")))
  implementation(project(mapOf("path" to ":data")))
  implementation(Libs.CORE_KTX)
  implementation(Libs.LIFECYCLE_VIEWMODEL_KTX)
  implementation(Libs.LIFECYCLE_VIEWMODEL_COMPOSE)
  implementation(Libs.MATERIAL)
  implementation(Libs.COMPOSE_UI)
  implementation(Libs.COMPOSE_UI_TOOLING_PREVIEW)
  implementation(Libs.HILT_ANDROID)
  kapt(Libs.HILT_ANDROID_COMPILER)
  testImplementation(Libs.COROUTINES_TEST)
  testImplementation(Libs.JUNIT)
  androidTestImplementation(Libs.ANDROIDX_JUNIT)
  androidTestImplementation(Libs.ANDROIDX_ESPRESSO_CORE)
  androidTestImplementation(Libs.COMPOSE_UI_JUNIT4)
  debugImplementation(Libs.COMPOSE_UI_TOOLING)
  debugImplementation(Libs.COMPOSE_UI_TEST_MANIFEST)
}

// Allow references to generated code
kapt {
  correctErrorTypes = true
}