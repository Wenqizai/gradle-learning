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

# Task 

项目实质是 Task 对象的集合，一个 Task 表示一个逻辑上较为独立的执行过程，比如编译、拷贝文件、打包 Jar 文件，甚至是一个执行命令操作。

## Task 行为

`doFirst` 和 `doLast` 在任务执行阶段执行。可以在 Task 内部定义，也可以在 Task 外部定义。

**任务执行顺序**

Task 维护一个双向链表，初始状态：doFirst -> action -> doLast，执行顺序也是从左到右。当我们不断添加任务时，列表就维护成以下状态。

`doFirst2 -> doFirst1 -> action1 -> doLast1 -> doLast2`

由此可以，doFirst 后添加先执行，doLast 后添加后执行。

## Task 依赖

执行任务 task 时，先执行依赖的任务 taskA 和 taskB。

```
task A {  
    doLast {  
        println("taskA")  
    }  
}  
  
task B {  
    doLast {  
        println("taskB")  
    }  
}  
```

**参数依赖**

```
task C (dependsOn: ['A', 'B']) {  
    doLast {  
        println("taskC")  
    }  
}
```

**内部依赖**

```
// 内部依赖  
task D {  
    dependsOn = ['A', 'B']  
    doLast {  
        println("taskD")  
    }  
}
```

**外部依赖**

```
task E {  
    doLast {  
        println("taskE")  
    }  
}  
E.dependsOn = ['A', 'B']
```

**跨项目依赖**

默认执行 `build.gradle` 所在的项目，如果需要执行跨项目依赖需要指定项目名。

```
// 跨项目依赖, task F 依赖 root.subproject03 下的 task subproject03Hello
task F {  
    dependsOn = [':root:subproject03:subproject03Hello']  
    doLast {  
        println("taskF")  
    }  
}
```

**依赖多个 Task**

一个 Task 依赖于多个 Task 时，被依赖的 Task 之间如果没有依赖关系，那么它们之间的执行顺序是随机的，并无影响。

Task 被多个依赖时，该 Task 只会执行一次，不会执行多次。

## Task 执行 

Task 执行语法：`gradle [taskName...] [--option-name...]`。

1.  常见执行的任务：

```
gradle build: 构建项目, 编译, 测试, 打包等操作'

gradle run: 运行一个服务, 需要 application 插件支持, 并且指定了主启动类才能运行 

gradle clean: 清理当前项目的 build 目录

gradle init: 初始化 gradle 项目

gradle wrapper: 生成 wrapper 文件夹
```

2. 项目报告相关的

```
gradle projects 列出项目的层次结构
gralde tasks (--all) 列出当前项目已分配给任务组的哪些任务
gralde tasks --group="someGroup" 列出指定分组的任务信息
gradle help --task someTask 显示某个任务的详细信息
gralde dependencies 查看项目的依赖
gradle properties 

gradle init --type pom 将 maven 项目转换为 gralde 项目
```

Gradle 依赖冲突时，默认使用高版本的 jar 包。

**Gradle 任务指令依赖**

![|775](https://docs.gradle.org/current/userguide/img/javaPluginTasks.png)

## Task 定义

`tasks.create` 和 `tasks.register`，其中 `tasks.register` 是延迟执行的。

**属性定义**

| 属性          | 描述                      | 默认值         |
| ----------- | ----------------------- | ----------- |
| type        | 基于一个存在的 task 来创建，类似于继承  | DefaultTask |
| overwrite   | 和 type 配合使用，是否重写 task   | False       |
| denpendsOn  | 指定 task 的依赖             | []          |
| action      | 添加 Task 的 action 或者一个闭包 | Null        |
| description | 任务描述                    | Null        |
| group       | 配置任务分组                  | Null        |

## Task 类型 

官方提供了一些 Task 类型，我们在创建 Task 时可以指定 Task 类型，就可以复用这些 Task 的 API，无需重复编码。

| 任务类型                     | 作用                                            |
| ------------------------ | --------------------------------------------- |
| Delete                   | 删除文件/目录                                       |
| Copy                     | 复制文件，可以重命名                                    |
| CreateStarScripts        | 创建启动脚本                                        |
| Exec                     | 执行命令行进程                                       |
| GenerateMavenPom         | 生成 Maven 模块 POM 文件                            |
| GradleBuild              | 执行 Gradle 构建                                  |
| Jar                      | 生成 Jar 文件                                     |
| JavaCompile              | 编译                                            |
| Javadoc                  | 生成文档                                          |
| PublishToMavenRepository | 将 MavenPublication 发布到 mavenaritifactrepostal |
| Tar                      | 生成 Tar 文件                                     |
| Test                     | 执行 Test                                       |
| Upload                   | 将 Configuration 的构建上传到一组存储库                   |
| War                      | 生成 war 文件                                     |
| Zip                      | 打包 zip 文件                                     |
# 文件

Gradle 的文件操作相当于调用 Java File 类操作。

**文件树**

通过 `fileTree("src")` 可以获取目录下的文件树，可以进行遍历操作。

# 依赖 

**依赖方式**

1. 本地依赖：依赖本地某个 Jar 包，具体可通过文件集合、文件树方式指定；
2. 项目依赖：依赖某个 Project；
3. 直接依赖：依赖的类型、依赖的组名、依赖的名称、依赖的版本号。

**依赖类型**

类似 Maven 的 scope 标签，具体如下：

| 类型                   | 作用                                                |
| -------------------- | ------------------------------------------------- |
| `compileOnly`        | Java 插件提供，编译期运行，不需要打包                             |
| `runtimeOnly`        | Java 插件提供，运行期有效，编译时不需要，取代 runtime                 |
| `implementation`     | Java 插件提供，针对源码目录 `src/main`，在编译和运行时有效，取代 compile  |
| `testCompileOnly`    | Java 插件提供，用于编译测试的依赖项，运行时不需要                       |
| `testRuntimeOnly`    | Java 插件提供，在测试运行时需要，而不是测试编译时                       |
| `testImplementation` | Java 插件提供，针对测试源码目录 `src/main`                     |
| `provideCompile`     | War 插件提供，编译、测试阶段代码依赖该 jar 包，无需打进 war 包中           |
| `compile`            | gradle 7 已经移除                                     |
| `runtime`            | gradle 7 已经移除                                     |
| `api`                | Java-library 插件支持，依赖项可以传递性给使用者，用于编译和运行，取代 compile |
| `compileOnlyApi`     | Java-library 插件支持，在声明模块和使用者在编译时需要的依赖项，运行时不需要      |

## api&implementation

`api` 和 `implementation ` 都是用于编译和运行，主要区别是不同项目的可见性。

**api**

1. 可见性：使用是 api 声明的依赖在当前项目和所有依赖该项目的其他项目都可见；
2. 传播性：本项目 api 声明的依赖，可传递到依赖该项目的其他项目；

场景：适用于公共 API 的依赖，方便其他项目访问。当 api 依赖变更时，依赖的相关项目都要重新加载，运行，编译和运行都较慢。

**implementation** 

1. 可见性：implementation 声明的依赖仅在本项目可见，其他项目不可见；
2. 传播性：implementation 声明的依赖不会传播到其他项目中。

场景：适用于内部实现细节的依赖，不传递到其他项目。正常情况使用 implementation 声明依赖，加快构建速度。

合理利用 `api ` 和 ` implementation` 减少项目中的依赖。

## 依赖冲突 

Gradle 处理依赖冲突时，默认采用最新版本的 jar 包。除此之外，我们也可以通过其他方式来处理 jar 包冲突。

1. 利用 exclude 来排除指定的版本的 jar 包；
2. 仅引用当前 jar 包，不依赖其他 jar 包，可设置 `transitive(false)`；
3. 强制使用该 jar 包，加 `!！`


```
implementation('org.hibernate:hibernate-core:5.6.15.Final') {  
    transitive = false  
    exclude(group: 'org.slf4j')  
}

// 强制使用指定版本  
implementation('org.slf4j:slf4j-log4j12:1.7.36!!')  
implementation('org.slf4j:slf4j-log4j12:1.4.0') {  
    version {  
        strictly('1.7.36')  
    }  
}
```
# 插件

插件分类：

1. 脚本插件
2. 二进制插件（对象插件）
	1. 内部插件
	2. 第三方插件
	3. 自定义插件

## 脚本插件

脚本插件本质就是一个脚本，通过 apply from 将脚本加载进来就可以。可以是本地脚本，也可以是网络上的脚本。

```
apply from: "script.gradle"
```

脚本插件好处是，模块化，职责单一，便于管理脚本。

## 二进制/对象插件

二进制插件就是实现了 `org.gralde.api.Plugin`, 每个 Java gradle 插件都有一个 plugin id。

⚠️upload failed, check dev console
![[image-20250226232526524.png]]

**内部插件**

> 引用方式

- `apply` 方式 

写法：`apply: map` ，其中 key: plugin   value: 插件 id，插件全类名，插件的简类名

```
apply plugin: 'java'

apply {
  plugin 'java'
}
```

- `plugins dsl` 

```
plugins {  
    id 'java'  
    id 'war'  
}
```

**第三方插件**

第三方插件一般都是下载完第三方依赖，可以通过全类名或者简类名引用。

## 自定义插件 

用户自定义 插件的局限性在本项目才能使用，其他项目不能使用。因此一般使用自定义插件是通过 `buildSrc` 项目。

### BuildSrc 项目

BuildSrc 是 Gradle 默认的插件目录，编译 Gradle 时回自动识别这个目录，将其中的代码编译为插件。该项目定义的插件可以被所有的项目引用。

#### 当前工程使用

步骤：

1. 新建 moudle `buildSrc`
2. 编写源码入口 `src/groovy/com/wenqi`
3. 编写 `build.gradle`，完成依赖定义
4. 编写插件：`src.groovy.com.wenqi.Text`
5. 编写资源目录：`src/resources/META-INF/gradle-plugins`
6. 编写 properties 文件：`com.wenqi.plugin.properties`
7. 定义构建的项目插件：`implementation-class=com.wenqi.Text`
8. 其他项目 `build.gradle` 引用该插件：`apply plugin : 'com.wenqi.plugin'`

#### 其他工程使用 

将 buildSrc 目录发布到 maven 仓库中，其他工程就可以使用该自定义插件。

其他工程引用该插件时，可以通过 buildScript：

```
buildscript {  
    repositories {  
        maven {url "$rootDir/lib/releases"}  
    }  
  
    dependencies {  
        classpath 'com.wenqi:library:1.0-SNAPSHOT'  
    }  
}
```


# build.gradle

`build.gradle` 文件本质上是一个构建脚本文件，支持 Java 和 Groovy 语言。

- 每个 project 都有一个 `build.gradle` 文件，是项目构建入口，可配置版本、插件、依赖库等信息；
- 每个 `build.gradle` 可以获取到父工程的信息，也可以获取到子工程的信息。因此可以在 root 工程做统一的配置。

[史上最全Android build.gradle配置详解，你懂的！Android Studio是采用gradle来构建项 - 掘金](https://juejin.cn/post/6844903933584883720)


# 项目发布 

项目发布执行步骤：引入发布插件、设置发布代码、执行发布命令。

- 引入发布插件 

```
plugins {  
    id 'maven-publish'  
}
```

- 设置发布代码 

```
publishing {
    publications {
        myLibrary(MavenPublication) {
            groupId = 'com.wenqi'
            artifactId = 'library'
            version = '1.0-SNAPSHOT'
            from components.java // 发布 jar 包
//            from components.web  // 发布 war 包
        }
    }
    repositories {
        maven {
            name = "myRepo"
            url = "$rootDir/lib/releases"
        }
    }

}
```

















