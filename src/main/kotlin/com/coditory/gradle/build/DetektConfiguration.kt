package com.coditory.gradle.build

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project

internal object DetektConfiguration {
    fun configure(project: Project) {
        project.plugins.withId("io.gitlab.arturbosch.detekt") {
            configureOnPluginEnabled(project)
        }
    }

    private fun configureOnPluginEnabled(project: Project) {
        val detektExtension = project.extensions.getByType(DetektExtension::class.java)
        detektExtension.buildUponDefaultConfig = true
        detektExtension.config.setFrom(project.fileTree(project.projectDir).include("/config/detekt/config.yml"))
        project.tasks.withType(Detekt::class.java) { task ->
            // task.buildUponDefaultConfig = true
            // task.config.setFrom(project.fileTree(project.projectDir).include("/config/detekt/config.yml"))
            task.jvmTarget = Versions.JVM_VERSION
            task.reports.apply {
                html.required.set(true)
                xml.required.set(true)
            }
        }
    }
}
