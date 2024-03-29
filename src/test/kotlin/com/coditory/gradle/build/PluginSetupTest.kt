package com.coditory.gradle.build

import com.coditory.gradle.build.base.TestProjectBuilder.Companion.createProject
import com.coditory.gradle.integration.IntegrationTestPlugin
import com.coditory.gradle.manifest.ManifestPlugin
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PluginSetupTest {
    private val project = createProject()

    @Test
    fun `should register build plugin with its dependencies`() {
        assertThat(project.plugins.getPlugin(BuildPlugin.PLUGIN_ID))
            .isInstanceOf(BuildPlugin::class.java)
        assertThat(project.plugins.getPlugin(IntegrationTestPlugin.PLUGIN_ID))
            .isInstanceOf(IntegrationTestPlugin::class.java)
        assertThat(project.plugins.getPlugin(ManifestPlugin.PLUGIN_ID))
            .isInstanceOf(ManifestPlugin::class.java)
    }
}
