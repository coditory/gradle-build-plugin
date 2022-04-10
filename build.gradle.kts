import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    id("jacoco")
    id("com.github.kt3k.coveralls") version "2.12.0"
    id("com.gradle.plugin-publish") version "1.0.0-rc-1"
    id("java-gradle-plugin")
    id("maven-publish")
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

ktlint {
    version.set("0.45.2")
}

dependencies {
    implementation(gradleApi())
    implementation("com.coditory.gradle:manifest-plugin:0.1.14")
    implementation("com.coditory.gradle:integration-test-plugin:1.3.0")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")

    testImplementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
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
            description = "Contains standard configuration for java based projects"
        }
    }
}

pluginBundle {
    website = "https://github.com/coditory/gradle-build-plugin"
    vcsUrl = "https://github.com/coditory/gradle-build-plugin"
    tags = listOf("java build")
}
