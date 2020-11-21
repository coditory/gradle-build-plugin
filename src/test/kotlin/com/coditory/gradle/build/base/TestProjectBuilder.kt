package com.coditory.gradle.build.base

import com.coditory.gradle.build.BuildPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.testfixtures.ProjectBuilder
import java.io.File
import java.nio.file.Files
import kotlin.reflect.KClass

class TestProjectBuilder private constructor(projectDir: File, name: String) {
    private val project = ProjectBuilder.builder()
        .withProjectDir(projectDir)
        .withName(name)
        .build()

    fun withGroup(group: String): TestProjectBuilder {
        project.group = group
        return this
    }

    fun withVersion(version: String): TestProjectBuilder {
        project.version = version
        return this
    }

    fun withPlugins(vararg plugins: KClass<out Plugin<*>>): TestProjectBuilder {
        plugins
            .toList()
            .forEach { project.plugins.apply(it.java) }
        return this
    }

    fun withBuildGradle(content: String): TestProjectBuilder {
        val buildFile = project.rootDir.resolve("build.gradle")
        buildFile.writeText(content.trimIndent().trim())
        return this
    }

    fun withFile(path: String, content: String): TestProjectBuilder {
        val filePath = project.rootDir.resolve(path).toPath()
        Files.createDirectories(filePath.parent)
        val testFile = Files.createFile(filePath).toFile()
        testFile.writeText(content.trimIndent().trim())
        return this
    }

    fun withDirectory(path: String): TestProjectBuilder {
        val filePath = project.rootDir.resolve(path).toPath()
        Files.createDirectories(filePath)
        return this
    }

    fun build(): Project {
        return project
    }

    companion object {
        private var projectDirs = mutableListOf<File>()

        fun project(name: String = "sample-project"): TestProjectBuilder {
            return TestProjectBuilder(createProjectDir(name), name)
                .withGroup("com.coditory")
                .withVersion("0.1.0-SNAPSHOT")
        }

        fun projectWithPlugins(name: String = "sample-project"): TestProjectBuilder {
            return project(name)
                .withPlugins(JavaPlugin::class, BuildPlugin::class)
        }

        fun createProjectWithPlugins(name: String = "sample-project"): Project {
            return project(name)
                .withPlugins(JavaPlugin::class, BuildPlugin::class)
                .build()
        }

        private fun createProjectDir(projectName: String): File {
            removeProjectDirs()
            val projectParentDir = createTempDir()
            val projectDir = projectParentDir.resolve(projectName)
            projectDir.mkdir()
            projectDirs.add(projectDir)
            return projectDir
        }

        fun removeProjectDirs() {
            projectDirs.forEach {
                it.deleteRecursively()
            }
        }
    }
}
