rootProject.name = "kotlin-time"
include("src")

dependencyResolutionManagement {
    versionCatalogs {
        create("deps") {
            version("ktor", "2.3.11")
            version("detekt", "1.23.6")
            version("kotlin", "2.1.10")
            version("kotlin_serialization_json", "1.8.1")
            version("nexus_staging", "0.30.0")
            version("exposed", "0.53.0")
            version("kotest", "5.7.2")
            version("valiktor_core", "0.12.0")

            library("ktor_client_core", "io.ktor", "ktor-client-core").versionRef("ktor")
            library("ktor_server_content_negotiation", "io.ktor", "ktor-server-content-negotiation").versionRef("ktor")
            library("ktor_client_content_negotiation", "io.ktor", "ktor-client-content-negotiation").versionRef("ktor")
            library("ktor_serialization_kotlinx_json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")
            library("serialization_json", "org.jetbrains.kotlinx", "kotlinx-serialization-json").versionRef("kotlin_serialization_json")
            library("kotlin_test", "org.jetbrains.kotlin", "kotlin-test-junit5").versionRef("kotlin")
            library("ktor_server_test_host", "io.ktor", "ktor-server-test-host").versionRef("ktor")
            library("exposed_time", "org.jetbrains.exposed", "exposed-java-time").versionRef("exposed")
            library("kotest", "io.kotest", "kotest-assertions-core").versionRef("kotest")
            library("kotest-jvm", "io.kotest", "kotest-assertions-core-jvm").versionRef("kotest")
            plugin("detekt", "io.gitlab.arturbosch.detekt").versionRef("detekt")
            library("detekt_formatting", "io.gitlab.arturbosch.detekt", "detekt-formatting").versionRef("detekt")
            plugin("nexus_release", "io.codearte.nexus-staging").versionRef("nexus_staging")
            plugin("kotlin_serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin")
            library("valiktor_core", "org.valiktor", "valiktor-core").versionRef("valiktor_core")

            bundle(
                "exposed",
                listOf(
                    "exposed_time",
                )
            )
        }
    }
}
include("main")
