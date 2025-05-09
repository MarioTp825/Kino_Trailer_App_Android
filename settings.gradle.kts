pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "My Movie"
include(":app")
include(":data")
include(":domain")

val filePath = settingsDir.parentFile.toString() + "/my_movie_detail/.android/include_flutter.groovy"
apply(from = File(filePath))
include(":cross_platform_integration")
include(":flutter_implementation")
