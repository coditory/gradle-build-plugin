package com.coditory.gradle.build

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.external.javadoc.JavadocMemberLevel
import org.gradle.external.javadoc.JavadocOutputLevel

internal object JavadocConfiguration {
    fun configure(project: Project) {
        project.tasks.withType(Javadoc::class.java) {
            if (it.name == "javadoc") {
                val javaConvention = project.convention.getPlugin(JavaPluginConvention::class.java)
                val main = javaConvention.sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)
                it.classpath = main.compileClasspath
                it.source = main.allJava
                it.isFailOnError = false
                it.options.outputLevel = JavadocOutputLevel.QUIET
                it.options.memberLevel = JavadocMemberLevel.PUBLIC
                it.options.encoding = "UTF-8"
            }
        }
    }
}
