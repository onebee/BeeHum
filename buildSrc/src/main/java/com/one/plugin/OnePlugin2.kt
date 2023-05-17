package com.one.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author  diaokaibin@gmail.com on 2023/5/17.
 */
class OnePlugin2 : Plugin<Project> {
    override fun apply(target: Project) {
        val extension2 = target.extensions.create("onebit", OneExtension2::class.java)
        target.afterEvaluate{
            println("hi - " + extension2.name)
        }

    }
}