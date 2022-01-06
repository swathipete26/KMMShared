plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

version = "1.0"

kotlin {
    android()
    iosX64()
    iosArm64()
    //iosSimulatorArm64() sure all ios dependencies support this target

    cocoapods {

        framework {
            // Configure fields required by CocoaPods.
            summary = "KMM Shared Library"
            homepage = "https://github.com/swathipete26/KMMShared"
            ios.deploymentTarget = "13.0"
            // Framework name configuration. Use this property instead of deprecated 'frameworkName'
            baseName = "MyFramework"
            // (Optional) Dynamic framework support
            isStatic = false
            // (Optional) Dependency export
            export(project(":KMMShared"))
            transitiveExport = true
            // (Optional) Bitcode embedding
            embedBitcode(org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode.BITCODE)
        }

        // Maps custom Xcode configuration to NativeBuildType
        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType.RELEASE
    }
    
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        //val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            //iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        //val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            //iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 23
        targetSdk = 31
    }
}