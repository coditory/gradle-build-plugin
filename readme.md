# Build Gradle Plugin

[![Join the chat at https://gitter.im/coditory/gradle-build-plugin](https://badges.gitter.im/coditory/gradle-build-plugin.svg)](https://gitter.im/coditory/gradle-integration-test-plugin?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.org/coditory/gradle-build-plugin.svg?branch=master)](https://travis-ci.org/coditory/gradle-build-plugin)
[![Coverage Status](https://coveralls.io/repos/github/coditory/gradle-build-plugin/badge.svg)](https://coveralls.io/github/coditory/gradle-build-plugin)
[![Gradle Plugin Portal](https://img.shields.io/badge/Plugin_Portal-v0.1.0-green.svg)](https://plugins.gradle.org/plugin/com.coditory.build)

This plugin adds typical build configuration for JVM projects:
- [manifest generation](https://github.com/coditory/gradle-manifest-plugin)
- [integration tests](https://github.com/coditory/gradle-integration-test-plugin)
- sets up more verbose test logs

## Enabling the plugin

Add to your `build.gradle`:

```gradle
plugins {
  id 'com.coditory.build' version '0.1.0'
}
```

## More verbose test logs

This plugin configures test tasks as we all did multiple times in countless projects:

```gradle
tasks.withType(Test) {
    testLogging {
        exceptionFormat = 'full'
        events = ['passed', 'skipped', 'failed']
    }
}
```
