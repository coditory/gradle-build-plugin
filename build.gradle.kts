plugins {
    kotlin("jvm") version "1.9.24"
    id("jacoco")
    id("com.github.kt3k.coveralls") version "2.12.2"
    id("com.gradle.plugin-publish") version "1.3.0"
    id("java-gradle-plugin")
    id("maven-publish")
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

ktlint {
    version.set("0.45.2")
}

dependencies {
    val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10"
    val ktlintPlugin = "org.jlleitschuh.gradle:ktlint-gradle:11.6.1"
    val detectPlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.7"

    implementation(gradleApi())
    implementation("com.coditory.gradle:manifest-plugin:0.2.6")
    implementation("com.coditory.gradle:integration-test-plugin:2.2.5")
    compileOnly(kotlinGradlePlugin)
    compileOnly(ktlintPlugin)
    compileOnly(detectPlugin)

    testImplementation(kotlinGradlePlugin)
    testImplementation(ktlintPlugin)
    testImplementation(detectPlugin)
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.3")
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

group = "com.coditory.gradle"

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "failed", "skipped")
        setExceptionFormat("full")
    }
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

coveralls {
    sourceDirs = listOf("src/main/kotlin")
}

gradlePlugin {
    plugins {
        create("buildPlugin") {
            id = "com.coditory.build"
            implementationClass = "com.coditory.gradle.build.BuildPlugin"
            displayName = "Coditory Build Plugin"
            description = "Contains standard configuration for java based projects"
        }
    }
}

pluginBundle {
    website = "https://github.com/coditory/gradle-build-plugin"
    vcsUrl = "https://github.com/coditory/gradle-build-plugin"
    tags = listOf("java build")
}
