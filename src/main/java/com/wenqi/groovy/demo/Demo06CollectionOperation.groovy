package com.wenqi.groovy.demo

// ========================= List 相关操作 =========================
// 集合中添加元素
def list = [1, 2, 3]
assert list instanceof List
list.add(4)
println list

// 添加集合
def list2 = [5, 6, 7]
println(list += list2)


// 删除元素
// 指定下标
list.remove(2)
println list

// 指定元素
list.removeElement(3)
println list

// 指定集合
def list3 = [5, 6]
list.removeAll(list3)
println list

// 弹出第一个元素
println list.pop()
println list

// 修改下标元素 put
list.putAt(0, 3)
println(list)

// 遍历 list
list.forEach {
    println("Item: ${it}") // it 表示当前元素, 是一个隐式参数
}


// ========================= Map 相关操作 =========================
// key 可以不使用引号, 也可以使用单双引号
def map = [J:"Java", 'G':"Groovy", "C":"C++"]
map.put("K", "Kotlin")

// 遍历 map
map.each {key, value -> {
    println("key: ${key}, value: ${value}")
}}
map.each {entry -> {
    println("entry.key: ${entry.key}, entry.value: ${entry.value}")
}}

// 删除元素
map.remove("K")  // 根据 key 删除
map.remove("J", "Java") // 根据 k-v 删除
map.remove("C", "C+")
println map

// 操作符
map2 = map + ["K": "Kotlin"]
println map2

map3 = map + ["C": "C"]
println map3

println(map)
