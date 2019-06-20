object Versions {
    const val min_sdk = 21
    const val target_sdk = 28
    const val compile_sdk = 28
    const val version_code = 1
    const val version_name = "1.0"

    const val kotlin = "1.3.40"
    const val support = "28.0.0"
    const val constraint_layout = "1.1.3"
    const val koin = "2.0.1"
    const val aac = "1.1.1"
    const val coroutines = "1.1.1"
    const val retrofit = "2.4.0"
    const val moshi = "1.5.0"
    const val mockito_kotlin = "2.1.0"
    const val picasso = "2.71828"
    const val retrofit_coroutines_adapter = "0.9.2"
    const val arrow = "0.7.3"
    const val junit = "4.12"
}

object Dependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val support = "com.android.support:appcompat-v7:${Versions.support}"
    const val recycler_view = "com.android.support:recyclerview-v7:${Versions.support}"
    const val constraint_layout = "com.android.support.constraint:constraint-layout:${Versions.constraint_layout}"
    const val aac_extensions = "android.arch.lifecycle:extensions:${Versions.aac}"
    const val aac_viewmodel = "android.arch.lifecycle:viewmodel:${Versions.aac}"
    const val aac_core_testing = "android.arch.core:core-testing:${Versions.aac}"
    const val koin = "org.koin:koin-android:${Versions.koin}"
    const val koin_viewmodel = "org.koin:koin-android-viewmodel:${Versions.koin}"
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_moshi_converter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val retrofit_coroutines_adapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofit_coroutines_adapter}"
    const val arrow = "io.arrow-kt:arrow-core:${Versions.arrow}"
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
    const val junit = "junit:junit:${Versions.junit}"
    const val mockito_kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockito_kotlin}"
}