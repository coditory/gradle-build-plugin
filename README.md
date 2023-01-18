# Build Gradle Plugin
[![Build](https://github.com/coditory/gradle-build-plugin/actions/workflows/build.yml/badge.svg)](https://github.com/coditory/gradle-build-plugin/actions/workflows/build.yml)
[![Coverage Status](https://coveralls.io/repos/github/coditory/gradle-build-plugin/badge.svg)](https://coveralls.io/github/coditory/gradle-build-plugin)
[![Gradle Plugin Portal](https://img.shields.io/badge/Plugin_Portal-v0.1.19-green.svg)](https://plugins.gradle.org/plugin/com.coditory.build)

This plugin applies a typical configuration for Coditory JVM projects:
- Adds [manifest generation](https://github.com/coditory/gradle-manifest-plugin)
- Adds [integration tests](https://github.com/coditory/gradle-integration-test-plugin)
- Sets up some [reasonable defaults](#add-reasonable-defaults) for java projects

## Enabling the plugin

Add to your `build.gradle`:

```gradle
plugins {
  id 'com.coditory.build' version '0.1.19'
}
```

## Add reasonable defaults

Adds maven central as the default repository:

```gradle
// add a default repository
repositories {
    mavenCentral()
}

// enable junit and produce logs for tests
tasks.withType(Test) {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = "full"
        events = ["passed", "skipped", "failed"]
    }
}

tasks.withType(JavaCompile) {
    // make all test/main builds use UTF-8
    options.encoding = "UTF-8"
    // produce error on lint problems
    options.compilerArgs << "-Werror"
    options.compilerArgs << "-Xlint"
    options.compilerArgs << "-Xlint:-serial"
}

tasks.withType(GroovyCompile) {
    // make all test/main builds use UTF-8
    groovyOptions.encoding = "UTF-8"
}

tasks.withType(KotlinCompile) {
    // produce errors on warnings
    kotlinOptions.allWarningsAsErrors = true
}

// the most recent ktlint version (that does not cause problems)
// https://github.com/JLLeitschuh/ktlint-gradle/issues/589
ktlint {
    version.set("0.45.2")
}

// make Jacoco report combine all types of tests
jacocoTestReport {
    executionData(fileTree(project.buildDir).include("jacoco/*.exec"))
    reports {
        xml.required = true
        html.required = true
    }
}

// make javadoc less strict to limit noisy logs
javadoc {
    source = sourceSets.main.allJava
    classpath = configurations.compileClasspath
    failOnError = false
    options {
        this as StandardJavadocDocletOptions
        addBooleanOption("Xdoclint:none", true)
        addStringOption("Xmaxwarns", "1")
        memberLevel = JavadocMemberLevel.PUBLIC
        outputLevel = JavadocOutputLevel.QUIET
        encoding = "UTF-8"
    }
}
```

You're responsible to setup jvm toolchains:
```
// build.gradle for java:
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

// build.gradle.kts for kotlin >=1.8:
kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

// build.gradle.kts of for kotlin <1.8
kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(11))
    }
}
```
