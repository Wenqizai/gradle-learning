package com.wenqi.groovy.script

/**
 * 作为脚本语言, 无需定义类, 编译 class 文件时会自动生成类
 */
def name = "wenqi"
println "Hello $name"

/**
 * 混合使用脚本语言和面向对象语言
 */
class AsScriptDemo2 {
    int age;
    String name;
    boolean gender;
}