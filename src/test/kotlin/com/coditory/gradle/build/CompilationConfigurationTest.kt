package com.coditory.gradle.build

import com.coditory.gradle.build.base.TestProjectBuilder.Companion.createProjectWithPlugins
import com.coditory.gradle.build.base.TestProjectBuilder.Companion.projectWithPlugins
import org.assertj.core.api.Assertions.assertThat
import org.gradle.api.plugins.GroovyPlugin
import org.gradle.api.tasks.compile.GroovyCompile
import org.gradle.api.tasks.compile.JavaCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.junit.jupiter.api.Test

internal class CompilationConfigurationTest {
    @Test
    fun `should configure java compilation`() {
        // given
        val project = createProjectWithPlugins()

        // expect
        val tasks = project.tasks.withType(JavaCompile::class.java).toList()
        assertThat(tasks).isNotEmpty
        tasks.forEach {
            assertThat(it.options.encoding)
                .isEqualTo("UTF-8")
            assertThat(it.options.compilerArgs)
                .isEqualTo(listOf("-Werror", "-Xlint", "-Xlint:-serial"))
        }
    }

    @Test
    fun `should configure groovy compilation`() {
        // given
        val project = projectWithPlugins()
            .withPlugins(GroovyPlugin::class)
            .build()

        // expect
        val tasks = project.tasks.withType(GroovyCompile::class.java).toList()
        assertThat(tasks).isNotEmpty
        tasks.forEach {
            assertThat(it.groovyOptions.encoding)
                .isEqualTo("UTF-8")
        }
    }

    @Test
    fun `should configure kotlin compilation`() {
        // given
        val project = projectWithPlugins()
            .withPlugins(KotlinPlatformJvmPlugin::class)
            .build()

        // expect
        val tasks = project.tasks.withType(KotlinCompile::class.java).toList()
        assertThat(tasks).isNotEmpty
        tasks.forEach {
            assertThat(it.kotlinOptions.jvmTarget)
                .isEqualTo("11")
            assertThat(it.kotlinOptions.allWarningsAsErrors)
                .isEqualTo(true)
        }
    }
}
