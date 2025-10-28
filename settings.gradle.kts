rootProject.name = "kotlin-time"
include("src")

dependencyResolutionManagement {
    versionCatalogs {
        create("deps") {
            version("ktor", "3.3.0")
            version("detekt", "1.23.6")
            version("kotlin", "2.2.20")
            version("kotlin_serialization_json", "1.9.0")
            version("nexus_staging", "2.0.0")
            version("exposed", "1.0.0-rc-2")
            version("kotest", "6.0.0")
            version("valiktor_core", "0.12.0")

            library("ktor_client_core", "io.ktor", "ktor-client-core").versionRef("ktor")
            library("ktor_server_content_negotiation", "io.ktor", "ktor-server-content-negotiation").versionRef("ktor")
            library("ktor_client_content_negotiation", "io.ktor", "ktor-client-content-negotiation").versionRef("ktor")
            library("ktor_serialization_kotlinx_json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")
            library("serialization_json", "org.jetbrains.kotlinx", "kotlinx-serialization-json").versionRef("kotlin_serialization_json")
            library("kotlin_test", "org.jetbrains.kotlin", "kotlin-test-junit5").versionRef("kotlin")
            library("ktor_server_test_host", "io.ktor", "ktor-server-test-host").versionRef("ktor")
            library("exposed_time", "org.jetbrains.exposed", "exposed-java-time").versionRef("exposed")
            library("exposed_jdbc", "org.jetbrains.exposed", "exposed-jdbc").versionRef("exposed")
            library("kotest", "io.kotest", "kotest-assertions-core").versionRef("kotest")
            library("kotest-jvm", "io.kotest", "kotest-assertions-core-jvm").versionRef("kotest")
            plugin("detekt", "io.gitlab.arturbosch.detekt").versionRef("detekt")
            library("detekt_formatting", "io.gitlab.arturbosch.detekt", "detekt-formatting").versionRef("detekt")
            plugin("nexus.release", "io.github.gradle-nexus.publish-plugin").versionRef("nexus_staging")
            plugin("kotlin_serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin")
            library("valiktor_core", "org.valiktor", "valiktor-core").versionRef("valiktor_core")

            bundle(
                "exposed",
                listOf(
                    "exposed_time",
                    "exposed_jdbc"
                )
            )
        }
    }
}
include("main")
