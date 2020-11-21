package com.coditory.gradle.build

import org.gradle.api.Project
import org.gradle.api.tasks.compile.GroovyCompile
import org.gradle.api.tasks.compile.JavaCompile

internal object CompilationConfiguration {
    fun configure(project: Project) {
        configureJavaCompilation(project)
        configureGroovyCompilation(project)
        if (ClassChecker.isClassAvailable("org.jetbrains.kotlin.gradle.tasks.KotlinCompile")) {
            configureKotlinCompilation(project)
        }
    }

    private fun configureJavaCompilation(project: Project) {
        project.tasks.withType(JavaCompile::class.java) {
            it.options.encoding = "UTF-8"
            it.options.compilerArgs.addAll(
                listOf(
                    // error instead of warn
                    "-Werror",
                    // enable recommended lint options
                    "-Xlint",
                    // disable serial lint
                    // adding serial value to exceptions is not fun
                    "-Xlint:-serial"
                )
            )
        }
    }

    private fun configureGroovyCompilation(project: Project) {
        project.tasks.withType(GroovyCompile::class.java) {
            it.groovyOptions.encoding = "UTF-8"
        }
    }

    private fun configureKotlinCompilation(project: Project) {
        project.tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java) {
            it.kotlinOptions.jvmTarget = "11"
            it.kotlinOptions.allWarningsAsErrors = true
        }
    }
}
