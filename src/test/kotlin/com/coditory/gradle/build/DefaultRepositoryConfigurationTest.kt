package com.coditory.gradle.build

import com.coditory.gradle.build.base.TestProjectBuilder.Companion.createProject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class DefaultRepositoryConfigurationTest {
    @Test
    fun `should configure maven central as a default repository`() {
        // given
        val project = createProject()

        // expect
        val repositories = project.repositories
        assertThat(repositories.contains(repositories.mavenCentral()))
            .isTrue()
    }
}
