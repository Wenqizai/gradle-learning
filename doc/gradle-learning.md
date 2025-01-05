# Why

未来趋势，很多开源项目基于 gradle 构建，重要是公司需要就学呗。

优势：构建快，编写脚本灵活
缺点：学习成本高，重要是大版本之间兼容性较差。

# 概览

## 目录结构

⚠️upload failed, check dev console
![[Gradle与maven生成的目录结构.jpeg]]

构造 war 包有 webapp 目录，gradlew 和 gradlew.bat （win） 执行指定 wrapper 版本的 gradle，而不是本地安装的 gradle。