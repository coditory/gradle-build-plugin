import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("jacoco")
    id("com.github.kt3k.coveralls") version "2.12.0"
    id("com.gradle.plugin-publish") version "1.0.0"
    id("java-gradle-plugin")
    id("maven-publish")
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

ktlint {
    version.set("0.45.2")
}

dependencies {
    val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20"
    val ktlintPlugin = "org.jlleitschuh.gradle:ktlint-gradle:11.0.0"
    val detectPlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.21.0"

    implementation(gradleApi())
    implementation("com.coditory.gradle:manifest-plugin:0.2.0")
    implementation("com.coditory.gradle:integration-test-plugin:1.4.0")
    compileOnly(kotlinGradlePlugin)
    compileOnly(ktlintPlugin)
    compileOnly(detectPlugin)

    testImplementation(kotlinGradlePlugin)
    testImplementation(ktlintPlugin)
    testImplementation(detectPlugin)
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

group = "com.coditory.gradle"

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "failed", "skipped")
        setExceptionFormat("full")
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
        allWarningsAsErrors = true
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
