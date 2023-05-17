package com.one.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class OnePlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        def extension = project.extensions.create("onebit", OneExtension);
        project.afterEvaluate {
            println "hello ${extension.name}!"

        }
    }
}