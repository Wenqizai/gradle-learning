package com.wenqi.groovy.demo

def obj = new Demo01BasicNotice()

/**
 * 可以直接调用属性的 setter getter 方法, 而被调用对象内可以不声明 setter getter 方法
 * 行末可以不声明 ;
 */
println obj.getDescription()

println obj.sale(100)

/**
 * 对象属性赋值
 */
def notice = new Demo01BasicNotice()
// 方式 1
notice.author = 'newOne';
// 方式 2
notice['author'] = 'newTwo'
// 方式 3
notice.setAuthor('newThree')
// 方式 4 具名构造器
def obj2 = new Demo01BasicNotice(description: 'Groovy 属性赋值', author: 'newFour');

println notice.getAuthor()
println obj2.getAuthor()

/**
 * 对象属性取值
 */
println notice.author
println notice.getAuthor()
println notice['author']
