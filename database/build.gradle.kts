plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  kotlin("kapt")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.haroof.database"
  compileSdk = Versions.COMPILE_SDK

  defaultConfig {
    minSdk = Versions.MIN_SDK

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
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {
  implementation(Libs.CORE_KTX)
  implementation(Libs.ROOM_RUNTIME)
  implementation(Libs.ROOM_KTX)
  annotationProcessor(Libs.ROOM_COMPILER)
  kapt(Libs.ROOM_COMPILER)
  implementation(Libs.HILT_ANDROID)
  kapt(Libs.HILT_ANDROID_COMPILER)
}

// Allow references to generated code
kapt {
  correctErrorTypes = true
}