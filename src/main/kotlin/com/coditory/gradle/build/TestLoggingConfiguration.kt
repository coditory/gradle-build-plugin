package com.coditory.gradle.build

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED

internal object TestLoggingConfiguration {
    fun configure(project: Project) {
        project.tasks.withType(Test::class.java) { task ->
            task.useJUnitPlatform()
            task.testLogging {
                it.exceptionFormat = FULL
                it.events = setOf(PASSED, SKIPPED, FAILED)
            }
        }
    }
}
