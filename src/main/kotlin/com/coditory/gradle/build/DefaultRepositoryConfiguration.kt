package com.coditory.gradle.build

import org.gradle.api.Project

internal object DefaultRepositoryConfiguration {
    fun configure(project: Project) {
        project.repositories.add(project.repositories.mavenCentral())
    }
}
