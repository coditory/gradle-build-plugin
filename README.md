# Build Gradle Plugin
[![Build Status](https://github.com/coditory/gradle-build-plugin/workflows/Build/badge.svg?branch=master)](https://github.com/coditory/gradle-build-plugin/actions?query=workflow%3ABuild+branch%3Amaster)
[![Coverage Status](https://coveralls.io/repos/github/coditory/gradle-build-plugin/badge.svg?branch=master)](https://coveralls.io/github/coditory/gradle-build-plugin?branch=master)
[![Gradle Plugin Portal](https://img.shields.io/badge/Plugin_Portal-v0.1.13-green.svg)](https://plugins.gradle.org/plugin/com.coditory.build)
[![Join the chat at https://gitter.im/coditory/gradle-build-plugin](https://badges.gitter.im/coditory/gradle-build-plugin.svg)](https://gitter.im/coditory/gradle-integration-test-plugin?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

This plugin applies a typical configuration for Coditory JVM projects:
- Adds [manifest generation](https://github.com/coditory/gradle-manifest-plugin)
- Adds [integration tests](https://github.com/coditory/gradle-integration-test-plugin)
- Sets up some [reasonable defaults](#add-reasonable-defaults) for java projects

## Enabling the plugin

Add to your `build.gradle`:

```gradle
plugins {
  id 'com.coditory.build' version '0.1.13'
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
        exceptionFormat = 'full'
        events = ['passed', 'skipped', 'failed']
    }
}

tasks.withType(JavaCompile) {
    // make all test/main builds use UTF-8
    options.encoding = 'UTF-8'
    // produce error on lint problems
    options.compilerArgs << "-Werror"
    options.compilerArgs << "-Xlint"
    options.compilerArgs << "-Xlint:-serial"
}

tasks.withType(GroovyCompile) {
    // make all test/main builds use UTF-8
    groovyOptions.encoding = 'UTF-8'
}

tasks.withType(KotlinCompile) {
    // by default target newest LTS JVM
    kotlinOptions.jvmTarget = "11"
    // produce errors on warnings
    kotlinOptions.allWarningsAsErrors = true
}

// make Jacoco report combine all types of tests
jacocoTestReport {
    executionData(fileTree(project.buildDir).include("jacoco/*.exec"))
    reports {
        xml.enabled = true
        html.enabled = true
    }
}

// defaults for Javadoc
javadoc {
    source = sourceSets.main.allJava
    classpath = configurations.compileClasspath
    failOnError = false
    options {
        memberLevel = JavadocMemberLevel.PUBLIC
        outputLevel = JavadocOutputLevel.QUIET
        encoding = 'UTF-8'
    }
}
```
