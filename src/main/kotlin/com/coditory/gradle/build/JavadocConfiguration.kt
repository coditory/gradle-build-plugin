package com.coditory.gradle.build

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.external.javadoc.JavadocMemberLevel
import org.gradle.external.javadoc.JavadocOutputLevel
import org.gradle.external.javadoc.StandardJavadocDocletOptions

internal object JavadocConfiguration {
    fun configure(project: Project) {
        project.tasks.withType(Javadoc::class.java) {
            if (it.name == "javadoc") {
                val javaExtension = project.extensions.getByType(JavaPluginExtension::class.java)
                val main = javaExtension.sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)
                it.classpath = main.compileClasspath
                it.source = main.allJava
                it.isFailOnError = false

                val options = it.options as StandardJavadocDocletOptions
                options.addBooleanOption("Xdoclint:none", true)
                options.addStringOption("Xmaxwarns", "1")
                options.outputLevel = JavadocOutputLevel.QUIET
                options.memberLevel = JavadocMemberLevel.PUBLIC
                options.encoding = "UTF-8"
            }
        }
    }
}
