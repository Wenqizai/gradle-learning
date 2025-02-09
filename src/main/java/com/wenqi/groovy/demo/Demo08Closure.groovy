package com.wenqi.groovy.demo

def running = { who ->
    println("running start...")

    println("running $who...")

    println("running end...")

}

running("2000")

println("############")

running.call("2001")

println("############")

// test 02
def running2(Closure closure) {
    println("running start...")
    closure()
    println("running end...")
}

running2({ println("running...") })

println("############")

// test 03
def caculate(Closure closure) {
    def num1 = 10;
    def num2 = 15;
    closure(num1, num2)
}

caculate({ a, b -> println("a + b = ${a + b}") })

println("############")

// test 04
// 闭包作为方法的最后一个参数, 那么闭包可以写在方法外边
def caculate2(num1, num2, Closure closure) {
    closure(num1, num2)
}

caculate2(10, 15, { a, b -> println("a + b = ${a + b}") })
caculate2(10, 15) { a, b -> println("a + b = ${a + b}") }

