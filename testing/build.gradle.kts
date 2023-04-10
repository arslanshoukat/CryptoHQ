plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
}

android {
  namespace = "com.haroof.testing"
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
  api(project(mapOf("path" to ":data")))
  implementation(project(mapOf("path" to ":domain")))
  implementation(Libs.CORE_KTX)
  implementation(Libs.COROUTINES_TEST)
  implementation(Libs.JUNIT)
  implementation(Libs.ANDROIDX_JUNIT)
  implementation(Libs.HILT_ANDROID_TESTING)
  implementation(Libs.ANDROIDX_TEST_RUNNER)
  coreLibraryDesugaring(Libs.JDK_LIBS_DESUGAR)
}