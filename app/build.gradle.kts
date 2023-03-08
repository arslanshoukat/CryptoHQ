plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
}

android {
  namespace = "com.haroof.cryptohq"
  compileSdk = Versions.COMPILE_SDK

  defaultConfig {
    applicationId = "com.haroof.cryptohq"
    minSdk = Versions.MIN_SDK
    targetSdk = Versions.TARGET_SDK
    versionCode = Versions.VERSION_CODE
    versionName = Versions.VERSION_NAME

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables.useSupportLibrary = true
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
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.4.0"
  }
  packagingOptions {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  implementation(Libs.CORE_KTX)
  implementation(Libs.LIFECYCLE_RUNTIME_KTX)
  implementation(Libs.ACTIVITY_COMPOSE)
  implementation(Libs.MATERIAL)
  implementation(Libs.COMPOSE_UI)
  implementation(Libs.COMPOSE_UI_TOOLING_PREVIEW)
  testImplementation(Libs.JUNIT)
  androidTestImplementation(Libs.ANDROIDX_JUNIT)
  androidTestImplementation(Libs.ANDROIDX_ESPRESSO_CORE)
  androidTestImplementation(Libs.COMPOSE_UI_JUNIT4)
  debugImplementation(Libs.COMPOSE_UI_TOOLING)
  debugImplementation(Libs.COMPOSE_UI_TEST_MANIFEST)
}