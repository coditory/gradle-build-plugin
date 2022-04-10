package com.coditory.gradle.build

import com.coditory.gradle.build.base.TestProjectBuilder.Companion.projectWithPlugins
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.junit.jupiter.api.Test

internal class DetektConfigurationTest {
    @Test
    fun `should configure detekt tasks defaults`() {
        // given
        val project = projectWithPlugins()
            .withPlugins(KotlinPlatformJvmPlugin::class)
            .withPlugins(DetektPlugin::class)
            .withFile("config/detekt/config.yml", "# empty config")
            .build()

        // expect
        val tasks = project.tasks.withType(Detekt::class.java).toList()
        assertThat(tasks).isNotEmpty
        tasks.forEach {
            assertThat(it.buildUponDefaultConfig).isEqualTo(true)
            assertThat(it.jvmTarget).isEqualTo("17")
            assertThat(it.reports.xml.required.get()).isEqualTo(true)
            assertThat(it.reports.html.required.get()).isEqualTo(true)
            assertThat(it.config.files).contains(
                project.projectDir.resolve("config/detekt/config.yml")
            )
        }
    }
}
