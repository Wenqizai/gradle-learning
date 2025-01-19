package com.wenqi.groovy.demo

/**
 * 字符串
 */
def desc = "测试";

def str1 = '单引号, 不支持变量引用, 不支持换行操作 ${desc}'
println(str1)

def str2 = "双引号, 支持变量引用, 不支持换行操作 ${desc}"
println(str2)

def str3 = """三引号, 支持变量引用, 支持换行操作
                        ${desc}
                   """
println(str3)


// 查看三种字符串的变量类型
println(str1.getClass().toString())
println(str2.getClass().toString()) // GStringImpl.class
println(str3.getClass().toString()) // GStringImpl.class
