### modules

| module | description                                                                                                                                                                                                                                                                    |
| ----------------- |--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [springboot-bulletchat](https://github.com/xmxe/springboot-gradle/tree/master/springboot-bulletchat) | Netty视频弹幕实现   |
| [springboot-interceptor](https://github.com/xmxe/springboot-gradle/tree/master/springboot-interceptor) | 使用拦截器获取Controller方法名、注解等相关信息 |
| [springboot-jwt](https://github.com/xmxe/springboot-gradle/tree/master/springboot-jwt) | JWT ---JSON WEB TOKEN |
| [springboot-mongodb](https://github.com/xmxe/springboot-gradle/tree/master/springboot-mongodb) | 继承mongodb测试  |
| [springboot-qrlogin](https://github.com/xmxe/springboot-gradle/tree/master/springboot-qrlogin) | [聊聊二维码扫码登录的原理](https://mp.weixin.qq.com/s/d-xV8RN18wXPPwhZqUZLew)  |
| [springboot-Retrofit](https://github.com/xmxe/springboot-gradle/tree/master/springboot-Retrofit) | [一款适用于 Spring Boot 的神级 HTTP 客户端框架](https://mp.weixin.qq.com/s/kN3B1W6pNsTrxJJ0DLtbgQ)  |
| [springboot-test](https://github.com/xmxe/springboot-gradle/tree/master/springboot-test) | [Spring Boot 集成 JUnit5，更优雅单元测试！](https://mp.weixin.qq.com/s/cWUdtoKxlQ-20bKn09F3tw) <br /> [为什么要写单元测试？如何写单元测试？](https://mp.weixin.qq.com/s/aHQTuIQ90U7zXWuQyTaZiw) |
| [springboot-vpaim](https://github.com/xmxe/springboot-gradle/tree/master/springboot-vpaim) | 自定义校验器(Validator)、自定义属性编辑器(PropertyEditorSupport)、@Autowired三种注入方式、详细测试@InitBinder和@ModelAttribute <br />[这么写参数校验 (Validator) 就不会被劝退了](https://mp.weixin.qq.com/s/zm_jZuzAlc-JcqAQLTv7jQ)<br />[Spring Boot 实现各种参数校验，非常实用！](https://mp.weixin.qq.com/s/AfNNzLoIeu7YpQhRKlqNzg) |
| [springboot-webflux](https://github.com/xmxe/springboot-gradle/tree/master/springboot-webflux)  | 测试webflux，连接数据库 |


---

# 1.Gradle属性文件

## 1.1 gradle.properties

gradle.properties位于项目根目录下，主要设置Gradle后台进程JVM内存大小、日记级别等等；

Gradle属性配置参考：[Gradle属性配置](https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_configuration_properties)

## 1.2 local.properties

为Gradle配置环境变量，例如sdk、ndk路径；


# 2. setting.gradle

此配置文件位于根目录下，用于指示Gradle在构建应用时应将哪些模块包含在内；

多个模块之间用空格隔开

```
include ':app', ':lib'
```

# 3.顶级配置文件

此配置文件位于项目根目录下，用于定义项目所有模块的配置文件，

```
/**
 * The buildscript {} block is where you configure the repositories and
 * dependencies for Gradle itself--meaning, you should not include dependencies
 * for your modules here. For example, this block includes the Android plugin for
 * Gradle as a dependency because it provides the additional instructions Gradle
 * needs to build Android app modules
 *
 * buildscript{}块是为Gradle本身配置存储库和依赖项的地方——这意味着，这里不应该包含模块的依赖项。
 * 例如，此块包含Gradle的Android插件作为依赖项，因为它提供了Gradle构建Android应用程序模块所需的
 * 附加指令
 */

buildscript {

    /**
     * The repositories {} block configures the repositories Gradle uses to
     * search or download the dependencies. Gradle pre-configures support for remote
     * repositories such as JCenter, Maven Central, and Ivy. You can also use local
     * repositories or define your own remote repositories. The code below defines
     * JCenter as the repository Gradle should use to look for its dependencies.
     *
     * repositories{}块配置Gradle用于搜索或下载依赖项的存储库。
     * Gradle预先配置了对远程存储库（如JCenter、Maven Central和Ivy）的支持。
     * 您还可以使用本地存储库或定义自己的远程存储库。下面的代码将JCenter定义为Gradle应该
     * 用来查找其依赖项的存储库。
     */
    repositories {
        google()
        jcenter()
    }
    /**
     * The dependencies {} block configures the dependencies Gradle needs to use
     * to build your project. The following line adds Android Plugin for Gradle
     * version 3.1.0 as a classpath dependency.
     * 
     * dependencies{}块配置Gradle构建项目所需的依赖项。
     * 下一行添加了Android Plugin for Gradle version 3.1.0作为类路径依赖项。
     */
    dependencies {
        classpath 'com.android.tools.build:gradle:3.1.0'
    }
}
/**
 * The allprojects {} block is where you configure the repositories and
 * dependencies used by all modules in your project, such as third-party plugins
 * or libraries. Dependencies that are not required by all the modules in the
 * project should be configured in module-level build.gradle files. For new
 * projects, Android Studio configures JCenter as the default repository, but it
 * does not configure any dependencies.
 * 
 * allprojects{}块用于配置项目中所有模块（如第三方插件或库）使用的存储库和依赖项。
 * 项目中的所有模块都不需要的依赖项应在模块级配置构建.gradle文件夹。
 * 对于新项目，androidstudio将JCenter配置为默认存储库，但不配置任何依赖项。
 */
 
allprojects {
   repositories {
       google()
       jcenter()
   }
}
```

## 3.1 buildscript{ }

主要配置Gradle代码托管库和依赖项；

### 3.1.1 repositories{ }

Gradle引用代码托管库，Gradle预先配置了远程支持JCenter、Maven、Central和lvy等存储区，当然也可以指定本地自定义的远程存储库,eg:google()；

JCenter():就可以使用JCenJCenter上托管的开源项目；

### 3.1.2 dependencies{ }

项目依赖Gradle的插件版本路径；

## 3.2 allprojects{ }

配置所有模块中使用的依赖项，例如第三方插件或libraries；

# 4. 模块配置文件

参考：[模块配置文件-build.gradle](https://blog.csdn.net/niuba123456/article/details/81074171)

# 5. Gradle-Wrapper配置

参考：[Gradle-Wrapper详解](https://blog.csdn.net/niuba123456/article/details/81074340)

------