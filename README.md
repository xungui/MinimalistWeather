# MinimalistWeather

> 基于**BaronTalk**的[MinimalistWeather](https://github.com/BaronZ88/MinimalistWeather)修改，使用Kotlin实现，数据库更换到Jetpack的Room


## 一. 前言

**MinimalistWeather 是 Android 平台上一款开源天气 App ，目前还在开发中。项目基于 MVP 架构，采用各主流开源库实现。开发此项目主要是为展示各种主流开发框架的使用方式以及 Android 项目的设计方案，并作为团队项目开发规范的一部分。**

采用的框架包括：

* RxJava
* Retrofit2
* OKHttp3
* Room
* Dagger2
* ButterKnife
* RetroLambda
* Stetho

**本项目还展示了：**

* MVP+RxJava在实际项目中的应用，MVP中RxJava生命周期的管理...；
* 上述罗列的各种开源框架的使用方法；
* Java8 Lambda表达式和Stream API的用法；
* 怎样适配Material Design；
* ToolBar、RecycleView、CardView、CoordinatorLayout等新控件的用法；
* Gradle的基本配置（包括签名打包、项目依赖等等）；
* 如何更好的管理Gradle依赖库的版本；
* 代码混淆配置；
* 如何快速开发一款结构清晰、可扩展性强的Android Application；

## 二. 项目结构设计图

![架构设计图（MVP）](framework_minimalist_weather.png)

## 三. 项目包结构介绍

**App Module包结构**

```Kotlin
-com.minimalist.weather.kotlin
    + di    //包含Dagger2依赖注入的唯一Component，统筹整个APP的DI工作
    - feature   // 业务 feature，feature 内按页面划分，如果是大型项目可以按业务模块划分，对于特大型项目建议走模块化（组件化）方案，每个业务模块再按照 MinimalistWeather 的分包规则来分包
        + adapter   //各类循环视图的适配器
        + base
        + contact   //View-Presenter 接口
        + home  //主页面
        - selectcity
            - xxActivity.kt // Activity 作为View-Presenter实现业务的基石，提供View层的生命周期控制
            - xxFragment.kt //实现的View层View逻辑
            - xxModule.kt // 为这一组View-Presenter提供DI
            - xxPresenter.kt    //实现控制逻辑
         -SplashActivity.kt     // 放在这里是为了便于查找应用程序入口
    + main  // 整个APP的的配置资源
        + stetho
        - WeatherApp.kt // Application 类
        - AppConstants.kt   // App 全局常量(如果有)
    + model //MVP 中所有 Model 层的数据处理都在这里
        + data
            + entity //数据库表集合
            + source //数据库实现
            - Local.kt  //标注本地数据库的注解
            - Remote.kt //标注远程数据库的注解
            - RepositoryModule.kt   //给数据库提供DI
        + http  //网络数据请求
        + preference
    + utils //APP使用的工具
    + widget //view的widget
```

## 三. 开源许可 [![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](https://www.apache.org/licenses/LICENSE-2.0)

```
Copyright 2017 Baron Zhang

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

