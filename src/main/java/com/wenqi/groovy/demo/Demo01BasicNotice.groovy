package com.wenqi.groovy.demo

class Demo01BasicNotice {
    def description = "Groovy 基础知识"
    def author = "wenqi"

    /**
     * 可以不定义方法类型, 返回值类型, 返回最后一行
     */
    def sale(price) {
        "销售价格为 $price"
        '销售价格为 $price'
    }
}
