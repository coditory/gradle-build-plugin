package com.coditory.gradle.build

import com.coditory.gradle.build.base.TestProjectBuilder.Companion.projectWithPlugins
import org.assertj.core.api.Assertions.assertThat
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.junit.jupiter.api.Test

internal class JacocoConfigurationTest {
    @Test
    fun `should configure jacoco to create report from all tests`() {
        // given
        val project = projectWithPlugins()
            .withPlugins(JacocoPlugin::class)
            .withFile("build/jacoco/test.exec", "Some test content")
            .withFile("build/jacoco/other.exec", "Some other content")
            .build()

        // expect
        val tasks = project.tasks.withType(JacocoReport::class.java).toList()
        assertThat(tasks).isNotEmpty
        tasks.forEach {
            assertThat(it.reports.xml.required.get()).isEqualTo(true)
            assertThat(it.reports.html.required.get()).isEqualTo(true)
            assertThat(it.executionData.files).contains(
                project.buildDir.resolve("jacoco/test.exec"),
                project.buildDir.resolve("jacoco/other.exec")
            )
        }
    }

    @Test
    fun `should configure jacoco so test exec file is optional`() {
        // given
        val project = projectWithPlugins()
            .withPlugins(JacocoPlugin::class)
            .withFile("build/jacoco/other.exec", "Some other content")
            .build()

        // expect
        val tasks = project.tasks.withType(JacocoReport::class.java).toList()
        tasks.forEach {
            assertThat(it.executionData.files).contains(
                project.buildDir.resolve("jacoco/other.exec")
            )
        }
    }
}
