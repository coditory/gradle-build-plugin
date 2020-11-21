package com.coditory.gradle.build

import com.coditory.gradle.build.base.TestProjectBuilder.Companion.createProjectWithPlugins
import org.assertj.core.api.Assertions.assertThat
import org.gradle.api.internal.tasks.testing.junitplatform.JUnitPlatformTestFramework
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.junit.jupiter.api.Test

internal class TestConfigurationTest {
    @Test
    fun `should configure test logging`() {
        // given
        val project = createProjectWithPlugins()
        val testTasks = project.tasks.withType(org.gradle.api.tasks.testing.Test::class.java)

        // expect
        testTasks
            .map { it.testLogging }
            .forEach {
                assertThat(it.exceptionFormat).isEqualTo(FULL)
                assertThat(it.events).containsExactlyInAnyOrder(PASSED, FAILED, SKIPPED)
            }
    }

    @Test
    fun `should configure test plugin to use junit platform`() {
        // given
        val project = createProjectWithPlugins()
        val testTasks = project.tasks.withType(org.gradle.api.tasks.testing.Test::class.java)

        // expect
        testTasks.forEach {
            assertThat(it.testFramework.javaClass).isEqualTo(JUnitPlatformTestFramework::class.java)
        }
    }
}
