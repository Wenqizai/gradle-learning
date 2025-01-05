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