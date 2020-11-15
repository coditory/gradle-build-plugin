package com.coditory.gradle.build

import com.coditory.gradle.build.base.SpecProjectBuilder.Companion.projectWithPlugins
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ConfigureDefaultRepositorySpec {
    @Test
    fun `should configure maven central as a default repository`() {
        // given
        val project = projectWithPlugins("sample-project")
            .withGroup("com.coditory")
            .withVersion("0.0.1-SNAPSHOT")
            .build()

        // expect
        val repositories = project.repositories
        assertThat(repositories.contains(repositories.mavenCentral()))
            .isTrue()
    }
}
