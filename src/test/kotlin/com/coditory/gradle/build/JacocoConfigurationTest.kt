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
            .build()

        // expect
        project.afterEvaluate {
            val tasks = project.tasks.withType(JacocoReport::class.java).toList()
            assertThat(tasks).isNotEmpty
            tasks.forEach {
                assertThat(it.reports.xml.isEnabled).isEqualTo(true)
                assertThat(it.reports.html.isEnabled).isEqualTo(true)
                assertThat(it.executionData).isEqualTo(project.fileTree(project.buildDir).include("jacoco/*.exec"))
            }
        }
    }
}
