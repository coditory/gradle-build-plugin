package com.coditory.gradle.build.base

import com.coditory.gradle.build.BuildPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.testfixtures.ProjectBuilder
import java.io.File
import kotlin.reflect.KClass

class SpecProjectBuilder private constructor(projectDir: File, name: String) {
    private val project = ProjectBuilder.builder()
        .withProjectDir(projectDir)
        .withName(name)
        .build()

    fun withGroup(group: String): SpecProjectBuilder {
        project.group = group
        return this
    }

    fun withVersion(version: String): SpecProjectBuilder {
        project.version = version
        return this
    }

    fun withPlugins(vararg plugins: KClass<out Plugin<*>>): SpecProjectBuilder {
        plugins
            .toList()
            .forEach { project.plugins.apply(it.java) }
        return this
    }

    fun build(): Project {
        return project
    }

    companion object {
        private var projectDirs = mutableListOf<File>()

        fun project(name: String = "sample-project"): SpecProjectBuilder {
            return SpecProjectBuilder(createProjectDir(name), name)
        }

        fun projectWithPlugins(name: String = "sample-project"): SpecProjectBuilder {
            return project(name)
                .withPlugins(JavaPlugin::class, BuildPlugin::class)
        }

        fun removeProjectDirs() {
            projectDirs.forEach {
                it.deleteRecursively()
            }
        }

        private fun createProjectDir(projectName: String): File {
            val projectParentDir = createTempDir()
            val projectDir = projectParentDir.resolve(projectName)
            projectDir.mkdir()
            projectDirs.add(projectDir)
            return projectDir
        }
    }
}
