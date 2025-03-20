import org.apache.tools.ant.filters.EscapeUnicode
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

plugins {
    id("java") // Java support
    alias(libs.plugins.kotlin) // Kotlin support
    alias(libs.plugins.changelog) // Gradle Changelog Plugin
    alias(libs.plugins.gradleIntelliJPlugin) // Gradle IntelliJ Plugin
}
kotlin {
    jvmToolchain(17) // 使用 JDK 17
}

fun properties(key: String) = providers.gradleProperty(key)
fun environment(key: String) = providers.environmentVariable(key)
fun dateValue(pattern: String): String =
    LocalDate.now(ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ofPattern(pattern))

group = properties("pluginGroup").get()
version = properties("pluginMajorVersion").get();

repositories {
    mavenCentral()
    maven("https://cache-redirector.jetbrains.com/intellij-dependencies")
}

dependencies {
    implementation(libs.jsoup)
    implementation(libs.zxingcore)
    implementation(libs.zxingjavase)
    implementation(libs.rsyntaxtextarea)
    implementation(libs.hutool)
    implementation(libs.jdom2)
}
intellij {
    pluginName = properties("pluginName")
    version = properties("platformVersion")
    type = properties("platformType")
    plugins = properties("platformPlugins").map { it.split(',').map(String::trim).filter(String::isNotEmpty) }
}

changelog {
    header = provider { "${version.get()} (${dateValue("yyyy/MM/dd")})" }
    groups.empty()
    repositoryUrl = properties("pluginRepositoryUrl")
}

tasks {
    patchPluginXml {
        version = properties("pluginMajorVersion")
        sinceBuild = properties("pluginSinceBuild")
        untilBuild = properties("pluginUntilBuild")
        pluginDescription = projectDir.resolve("DESCRIPTION.md").readText()
        changeNotes = provider {
            val markdownText = projectDir.resolve("CHANGELOG.md").readText()
            val lines = markdownText.split("\n")
            var htmlContent = ""
            var inList = false

            for (line in lines) {
                when {
                    line.matches(Regex("^# (.+)$")) -> {
                        if (inList) htmlContent += "</ul>"
                        htmlContent += "<h1>${line.removePrefix("# ")}</h1>"
                        inList = false
                    }
                    line.matches(Regex("^## (.+)$")) -> {
                        if (inList) htmlContent += "</ul>"
                        htmlContent += "<h2>${line.removePrefix("## ")}</h2>"
                        inList = false
                    }
                    line.matches(Regex("^### (.+)$")) -> {
                        if (inList) htmlContent += "</ul>"
                        htmlContent += "<h3>${line.removePrefix("### ")}</h3>"
                        inList = false
                    }
                    line.matches(Regex("^- (.+)$")) -> {
                        if (!inList) {
                            htmlContent += "<ul>"
                            inList = true
                        }
                        htmlContent += "<li>${line.removePrefix("- ")}</li>"
                    }
                    inList && (line.isBlank() || line.matches(Regex("^#.+|^##.+|^###.+"))) -> {
                        htmlContent += "</ul>"
                        inList = false
                        if (line.isNotBlank()) htmlContent += "<br>"
                    }
                    else -> {
                        if (inList) htmlContent += "</ul>"
                        inList = false
                        htmlContent += if (line.isBlank()) "<br>" else "$line<br>"
                    }
                }
            }
            if (inList) htmlContent += "</ul>"
            htmlContent
        }
    }
    listProductsReleases {
        sinceVersion = "2024.1"
    }
    signPlugin {
        certificateChain = environment("CERTIFICATE_CHAIN")
        privateKey = environment("PRIVATE_KEY")
        password = environment("PRIVATE_KEY_PASSWORD")
    }
    publishPlugin {
        dependsOn("patchChangelog")
        token = environment("PUBLISH_TOKEN")
    }
    wrapper {
        gradleVersion = properties("gradleVersion").get()
        distributionType = Wrapper.DistributionType.ALL
    }
    processResources {
        filesMatching("**/*.properties") {
            filter(EscapeUnicode::class)
        }
    }
}
