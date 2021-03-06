package com.coditory.gradle.build

import org.gradle.api.Project
import org.gradle.testing.jacoco.tasks.JacocoReport

internal object JacocoConfiguration {
    fun configure(project: Project) {
        project.afterEvaluate {
            project.tasks.withType(JacocoReport::class.java) {
                if (it.name == "jacocoTestReport") {
                    it.executionData(project.fileTree(project.buildDir).include("jacoco/*.exec"))
                    it.reports.xml.isEnabled = true
                    it.reports.html.isEnabled = true
                }
            }
        }
    }
}
