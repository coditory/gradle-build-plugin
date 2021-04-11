package com.coditory.gradle.build

import com.coditory.gradle.build.base.TestProjectBuilder.Companion.createProject
import org.assertj.core.api.Assertions.assertThat
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.external.javadoc.JavadocMemberLevel.PUBLIC
import org.junit.jupiter.api.Test

internal class JavadocConfigurationTest {
    @Test
    fun `should configure javadoc defaults`() {
        // given
        val project = createProject()

        // expect
        val tasks = project.tasks.withType(Javadoc::class.java).toList()
        assertThat(tasks).isNotEmpty
        tasks.forEach {
            assertThat(it.isFailOnError).isEqualTo(false)
            assertThat(it.options.encoding).isEqualTo("UTF-8")
            assertThat(it.options.memberLevel).isEqualTo(PUBLIC)
        }
    }
}
