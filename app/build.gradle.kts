plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  kotlin("kapt")
  id("org.jmailen.kotlinter") version "4.2.0"
}

android {
  namespace = "com.stephanieverissimo.rickandmortyapi"
  compileSdk = 34
  
  kotlinter {
    failBuildWhenCannotAutoFormat = false
    ignoreFailures = false
    reporters = arrayOf("checkstyle", "plain")
  }
  defaultConfig {
    applicationId = "com.stephanieverissimo.rickandmortyapi"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"
    
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }
  
  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
    unitTests.isReturnDefaultValues = true
  }
  
  
  
  
  
  
  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
          getDefaultProguardFile("proguard-android-optimize.txt"),
          "proguard-rules.pro"
                   )
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
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.7"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  
  implementation("androidx.core:core-ktx:1.12.0")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
  implementation("androidx.activity:activity-compose:1.8.2")
  implementation(platform("androidx.compose:compose-bom:2023.10.01"))
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.ui:ui-graphics")
  implementation("androidx.compose.ui:ui-tooling-preview")
  implementation("androidx.compose.material3:material3")
  
  //compose
  implementation("androidx.activity:activity-compose:1.8.2")
  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
  implementation("androidx.navigation:navigation-compose:2.7.6")
  //retrofit
  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")
  
  //coroutines
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
  
  //serialization
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
  //coil
  implementation("io.coil-kt:coil-compose:2.5.0")
  implementation("io.coil-kt:coil:2.5.0")
  //koin
  implementation("io.insert-koin:koin-core:3.5.3")
  implementation("io.insert-koin:koin-android:3.5.3")
  implementation("io.insert-koin:koin-androidx-compose:3.5.3")
  //voyager
  implementation("cafe.adriel.voyager:voyager-koin:1.0.0")
  implementation("cafe.adriel.voyager:voyager-navigator:1.0.0")
  implementation("cafe.adriel.voyager:voyager-screenmodel:1.0.0")
  
  //mockk
  testImplementation("io.mockk:mockk-agent:1.13.9")
  testImplementation("io.mockk:mockk:1.13.9")
  testImplementation("io.mockk:mockk-android:1.13.9")
  
  //paggin 3.0
    implementation ("androidx.paging:paging-runtime:3.2.1")
  //  implementation ("androidx.paging:paging-compose:3.2.1")
    implementation ("androidx.paging:paging-compose:1.0.0-beta01")
    
    
    
    testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
  androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
  androidTestImplementation("androidx.compose.ui:ui-test-junit4")
  debugImplementation("androidx.compose.ui:ui-tooling")
  debugImplementation("androidx.compose.ui:ui-test-manifest")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
  testImplementation("android.arch.core:core-testing:1.1.1")
  androidTestImplementation("com.android.support.test:runner:1.0.2")
  
}
