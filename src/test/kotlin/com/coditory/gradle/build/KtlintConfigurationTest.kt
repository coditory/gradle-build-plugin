package com.coditory.gradle.build

import com.coditory.gradle.build.base.TestProjectBuilder.Companion.projectWithPlugins
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.junit.jupiter.api.Test

internal class KtlintConfigurationTest {
    @Test
    fun `should configure ktlint version`() {
        // given
        val project = projectWithPlugins()
            .withPlugins(KotlinPlatformJvmPlugin::class)
            .withPlugins(KtlintPlugin::class)
            .build()

        // expect
        val extension = project.extensions.getByType(KtlintExtension::class.java)
        assertThat(extension.version.get()).isEqualTo("0.45.2")
    }
}
