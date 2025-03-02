package com.wenqi

import org.gradle.api.Plugin
import org.gradle.api.Project

class Text implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.task('text') {
            group = 'wenqi'
            doLast {
                println '自定义插件: Hello from text'
            }
        }
    }
}
