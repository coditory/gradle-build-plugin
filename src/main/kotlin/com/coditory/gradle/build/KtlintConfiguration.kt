package com.coditory.gradle.build

import org.gradle.api.Project
import org.jlleitschuh.gradle.ktlint.KtlintExtension

internal object KtlintConfiguration {
    fun configure(project: Project) {
        project.plugins.withId("org.jlleitschuh.gradle.ktlint") {
            configureOnPluginEnabled(project)
        }
    }

    private fun configureOnPluginEnabled(project: Project) {
        val ktlintExtension = project.extensions.getByType(KtlintExtension::class.java)
        ktlintExtension.version.set("0.45.2")
    }
}
