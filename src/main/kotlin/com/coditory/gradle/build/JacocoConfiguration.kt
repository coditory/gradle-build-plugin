package com.coditory.gradle.build

import org.gradle.api.Project
import org.gradle.testing.jacoco.tasks.JacocoReport

internal object JacocoConfiguration {
    fun configure(project: Project) {
        project.plugins.withId("jacoco") {
            project.tasks.withType(JacocoReport::class.java) {
                if (it.name == "jacocoTestReport") {
                    it.executionData.setFrom(project.fileTree(project.buildDir).include("/jacoco/*.exec"))
                    it.reports.xml.required.set(true)
                    it.reports.html.required.set(true)
                }
            }
        }
    }
}
