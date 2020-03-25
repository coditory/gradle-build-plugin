import pl.allegro.tech.build.axion.release.domain.hooks.HookContext
import pl.allegro.tech.build.axion.release.domain.hooks.HooksConfig

plugins {
    kotlin("jvm") version "1.3.70"
    id("jacoco")
    id("pl.allegro.tech.build.axion-release") version "1.11.0"
    id("com.github.kt3k.coveralls") version "2.10.1"
    id("com.gradle.plugin-publish") version "0.10.1"
    id("java-gradle-plugin")
    id("maven-publish")
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

repositories {
    jcenter()
    gradlePluginPortal()
}

ktlint {
    version.set("0.36.0")
    enableExperimentalRules.set(true)
}

dependencies {
    implementation(gradleApi())
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("com.coditory.gradle:manifest-plugin:0.1.0")
    implementation("com.coditory.gradle:integration-test-plugin:1.1.1")

    testImplementation("org.assertj:assertj-core:3.15.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

scmVersion {
    versionCreator("versionWithBranch")
    tag.prefix = project.name
    hooks = HooksConfig().also {
        it.pre(
            "fileUpdate",
            mapOf(
                "files" to listOf("readme.md") as Any,
                "pattern" to KotlinClosure2<String, HookContext, String>({ v, _ -> v }),
                "replacement" to KotlinClosure2<String, HookContext, String>({ v, _ -> v })
            )
        )
        it.pre("commit")
    }
}

group = "com.coditory.gradle"
version = scmVersion.version

tasks {
    withType<Test> {
        testLogging {
            events("passed", "failed", "skipped")
            setExceptionFormat("full")
        }
    }
    withType<Test> {
        useJUnitPlatform()
    }
    jacocoTestReport {
        reports {
            xml.isEnabled = true
            html.isEnabled = true
        }
    }
    coveralls {
        sourceDirs = listOf("src/main/kotlin")
    }
}

gradlePlugin {
    plugins {
        create("buildPlugin") {
            id = "com.coditory.build"
            implementationClass = "com.coditory.gradle.build.BuildPlugin"
        }
    }
}

// Marking new version (incrementPatch [default], incrementMinor, incrementMajor)
// ./gradlew markNextVersion -Prelease.incrementer=incrementMinor
// Releasing the plugin:
// ./gradlew release && ./gradlew publishPlugins
pluginBundle {
    website = "https://github.com/coditory/gradle-build-plugin"
    vcsUrl = "https://github.com/coditory/gradle-build-plugin"
    description = "Contains standard configuration for java based projects"
    tags = listOf("java build additional configuration")

    (plugins) {
        "buildPlugin" {
            displayName = "Build plugin"
        }
    }
}
