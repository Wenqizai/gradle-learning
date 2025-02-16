# 文档

[The Apache Groovy programming language - Groovy reference documentation](https://groovy-lang.org/single-page-documentation.html)
# Why

未来趋势，很多开源项目基于 gradle 构建，重要是公司需要就学呗。

优势：构建快，编写脚本灵活
缺点：学习成本高，重要是大版本之间兼容性较差。

# 概览

## 目录结构

⚠️upload failed, check dev console
![[Gradle与maven生成的目录结构.jpeg]]

构造 war 包有 webapp 目录，gradlew 和 gradlew.bat （win） 执行指定 wrapper 版本的 gradle，而不是本地安装的 gradle。

## 常用命令

| 命令                   | 作用            |
| -------------------- | ------------- |
| Gradle clean         | 清空 build 目录   |
| Gradle classes       | 编译代码和配置文件     |
| Gradle test          | 编译测试代码，生成测试报告 |
| Gradle build         | 构建项目          |
| Gradle build -x test | 跳过测试构建        |
执行命令需要在 `build.gradle` 目录下进行。

## init.d

查看 `init.d` 目录下的 `readme.txt` 的说明。大致知道，gradle 在 build 构建之前会先执行该目录下以 `。gradle` 结尾的文件脚本。

```
You can add .gradle (e.g. test.gradle) init scripts to this directory. Each one is executed at the start of the build.
```

### init.gradle

通常我们命名这个文件用来修改 gradle 的源，加速依赖构建和下载。

```
allprojects {
    repositories {
		mavenLocal()
		maven{ url 'https://mirrors.cloud.tencent.com/gradle/'}
		maven{ url 'https://mirrors.cloud.tencent.com/nexus/repository/maven-public/'}
		maven{ url 'https://maven.aliyun.com/repository/gradle-plugin'}
    	maven{ url 'https://nexus.cuba-platform.cn/repository/jmix/'}
		maven{ url 'https://maven.aliyun.com/repository/google'}
		maven{ url 'https://maven.aliyun.com/repository/jcenter'}
		maven{ url 'https://maven.aliyun.com/repository/public'}
        mavenCentral()
    }
}
```

`mavenLocal()`：使用 maven 的本地仓库，依赖于 MAVEN_HOME 指定的 settings.xml 文件。

Maven 和 Gradle 存放依赖目录结构的方式不一样的，所以 Maven 和 Gradle 是不用共用同一个仓库 jar 包，即使两者配置的仓库目录是一样的。

其中 Gradle 存放 jar 包的地址为 `GRADLE_USER_HOME/caches` 目录下。

## Wrapper 包装器

Wrapper 就是 Gradle 的一层包装，用来解决不同项目中 Gradle 版本不一致的问题，官方推荐使用 Wrapper 来构建我们的项目。

项目目录下脚本文件就是用来执行 Wrapper 的脚本：`gradlew` 和 `gradlew.bat`。

`./gradlew build` 的命令执行流程 ：

1. 读取 gradle 目录下的 `gradle-wrapper.properties` 文件配置信息；
2. 将指定的 gradle 版本下载到 `GRADLE_USER_HOME/wrapper/dists` 目录下；
3. 构建本地缓存到目录 `GRADLE_USER_HOME/caches`，相当于本地仓库。 

`gradle-wrapper.properties` 目录解释：

| 字段名              | 说明                                                          |
| ---------------- | ----------------------------------------------------------- |
| DistributionBase | Gradle 存储的基本目录, 通常设置为 `GRADLE_USER_HOME` 或 `PROJECT`        |
| DistributionPath | Gradle 解压后的 Gradle 存储目录，通常为 `wrapper/dists`                 |
| DistributionUrl  | 下载 gradle 的 url 地址                                          |
| ZipStoreBase     | 存储 Gradle zip 文件的基本目录, 通常设置为 `GRADLE_USER_HOME` 或 `PROJECT` |
| ZipStorePath     | 存储 Gradle zip 文件的路径，通常为 `wrapper/dists`                     |
# Groovy

Groovy 也是运行在 JVM 上的脚本语言，可以很好与 Java 代码和相关库进行交互操作，兼容 Java 语法。相对于 Java 做脚本，Groovy 代码量更少，更好支持动态类型转换和函数式编程，支持 DSL 语法。

## 安装

下载地址：[The Apache Groovy programming language - Download](https://groovy.apache.org/download.html#distro)
配置环境变量： `GROOVY_HOME` ，path = `%GROOVY_HOME%/bin`
验证命令：`groovy -v`

## 语法

1. Groovy 作为面向对象时，可定义类。作为脚本语言时，可以不定义类；
2. 脚本定义和类定义可以在同一个文件中使用；
3. Groovy 使用 `def` 定义变量，方法。不建议使用具体的数据类型，如 Java 的基本数据类型、包装数据类型、String 等。
4. 注释语法与 Java 一致，行末尾的 `;` 可以忽略，默认换行作为结束符；
5. 默认类、方法、字段都是 public 修饰的；
6. 属性赋值
7. 方法
	1. 声明时：可忽略参数类型、返回值类型，return 关键字（默认最后一句的返回值作为方法返回值）；
	2. 调用时，可忽略 `()`。
8. 基本类型也是对象，可直接调用对象的方法；
9. 字符串
	1. 单引号：作为字符串常量使用；
	2. 双引号：可引用变量 `${}`，有运算能力；
	3. 三引号：模板字符串，支持换行。 

Groovy 会自动判断数据类型，自动进行**类型转换**。

> **Groovy 类与 Java 类的区别**

1. 没有可见修饰符的类或方法自动是 public；
2. 没有可见修饰符的字段自动转换为属性，不需要显式的 getter 和 setter 方法；
3. 如果属性声明为 final，则不会生成 setter；
4. 脚本自动生成的类名就是定义脚本的文件名。

> **集合**

Groovy 可以将不同基本类型添加到同一个集合中。

> 类导入

Groovy 会默认导入一些类包，所以在书写 Groovy 代码时可以不用导入一些包。

如下，默认导入的包有：

```
import java.lang.*
import java.util.*
...
```

>闭包

定义：闭包是一个开放的、匿名的代码块，可以接收参数，返回值，也可以应用其周围作用域中声明的变量，类似 Java 中的 Lambda 表达式。

```
{ [closureParameters ->] statements}
```

# 测试支持

Gradle 测试任务自动检测并执行测试集中的所有单元测试，测试执行完成后会生成一个报告。支持 JUint 和 TestNG 测试。

⚠️upload failed, check dev console
![[Gradle测试默认读取和报告输出目录.png]]

**不执行测试**

1. `build.gradle` 文件添加指令

```
// 不执行测试  
test {  
    enabled(false)  
    useJUnitPlatform()  
}

// 只执行指定/排除包的测试
test {  
    enabled(true)  
    useJUnitPlatform()  
    include('com/wenqi/**')  
    exclude('com/wenqi/none/**')  
}
```

2. 指令指定跳过 

```
gradle build -x test
gradle build --exclude-task test
```

# 生命周期

[Gradle基础 构建生命周期和Hook技术对于初学者来说，面对各种各样的Gradle构建脚本，想要梳理它的构建流程 - 掘金](https://juejin.cn/post/6844903607679057934)

# Settings 文件

Settings 文件主要是在*项目初始化*阶段确定引入哪些工程需要加入到项目构建中，为构建项目工程树做准备。

Gradle 的工程树，类似与 maven 的 project 与 mudle。开发人员需要关注 `settings.gradle` 文件，这样我们可以掌握整个 project 的全貌。

