plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.doctorscheduling"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<JavaCompile>().configureEach {
        options.compilerArgs.add("-Xlint:deprecation")
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation ("io.github.medyo:android-about-page:2.0.0")
    implementation (libs.core)
    implementation(libs.multidex)
    implementation(libs.core.ktx)
    implementation(libs.material)
    implementation(libs.circleimageview)
    implementation(libs.material.calendarview)
    implementation(libs.fresco.v340)
    implementation(libs.infer.annotation.v0180)
    implementation (libs.cardview)
    annotationProcessor(libs.compiler.v4150)
    implementation(libs.webpsupport)
    implementation(libs.retrofit)
    implementation(libs.glide)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.appcompat)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.volley)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}
