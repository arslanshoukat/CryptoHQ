plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  kotlin("kapt")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.haroof.domain"
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
  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {
  implementation(project(mapOf("path" to ":common")))
  implementation(project(mapOf("path" to ":data")))
  implementation(Libs.CORE_KTX)
  implementation(Libs.HILT_ANDROID)
  kapt(Libs.HILT_ANDROID_COMPILER)
  coreLibraryDesugaring(Libs.JDK_LIBS_DESUGAR)
}

// Allow references to generated code
kapt {
  correctErrorTypes = true
}