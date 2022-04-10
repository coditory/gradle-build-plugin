package com.coditory.gradle.build

import com.coditory.gradle.integration.IntegrationTestPlugin
import com.coditory.gradle.manifest.ManifestPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

open class BuildPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        if (project.plugins.hasPlugin(JavaPlugin::class.java)) {
            setupPlugin(project)
        }
    }

    private fun setupPlugin(project: Project) {
        project.plugins.apply(ManifestPlugin::class.java)
        project.plugins.apply(IntegrationTestPlugin::class.java)
        TestConfiguration.configure(project)
        DefaultRepositoryConfiguration.configure(project)
        CompilationConfiguration.configure(project)
        JavadocConfiguration.configure(project)
        JacocoConfiguration.configure(project)
    }

    companion object {
        const val PLUGIN_ID = "com.coditory.build"
    }
}
