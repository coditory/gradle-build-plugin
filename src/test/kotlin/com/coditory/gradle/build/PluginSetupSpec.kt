package com.coditory.gradle.build

import com.coditory.gradle.integration.IntegrationTestPlugin
import com.coditory.gradle.build.base.SpecProjectBuilder.Companion.projectWithPlugins
import com.coditory.gradle.manifest.ManifestPlugin
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PluginSetupSpec {
    private val project = projectWithPlugins()
        .build()

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
