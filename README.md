# Build Gradle Plugin
[![Build Status](https://github.com/coditory/gradle-build-plugin/workflows/Build/badge.svg?branch=master)](https://github.com/coditory/gradle-build-plugin/actions?query=workflow%3ABuild+branch%3Amaster)
[![Coverage Status](https://coveralls.io/repos/github/coditory/gradle-build-plugin/badge.svg?branch=master)](https://coveralls.io/github/coditory/gradle-build-plugin?branch=master)
[![Gradle Plugin Portal](https://img.shields.io/badge/Plugin_Portal-v0.1.9-green.svg)](https://plugins.gradle.org/plugin/com.coditory.build)
[![Join the chat at https://gitter.im/coditory/gradle-build-plugin](https://badges.gitter.im/coditory/gradle-build-plugin.svg)](https://gitter.im/coditory/gradle-integration-test-plugin?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

This plugin adds typical build configuration for JVM projects:
- [manifest generation](https://github.com/coditory/gradle-manifest-plugin)
- [integration tests](https://github.com/coditory/gradle-integration-test-plugin)
- sets up more verbose test logs

## Enabling the plugin

Add to your `build.gradle`:

```gradle
plugins {
  id 'com.coditory.build' version '0.1.9'
}
```

## More verbose test logs

Configures test tasks to produce some output:

```gradle
tasks.withType(Test) {
    testLogging {
        useJUnitPlatform()
        exceptionFormat = 'full'
        events = ['passed', 'skipped', 'failed']
    }
}
```
