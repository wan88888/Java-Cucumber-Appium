# Java-Cucumber-Appium 移动自动化测试框架

这个框架使用Java、Cucumber和Appium设计用于移动应用程序测试。它遵循Page Object Model (POM)设计模式，支持Android和iOS平台。

## 版本

- Cucumber: 4.8.0
- Selenium: 3.141.59
- Appium Java Client: 7.3.0
- 支持Appium Server 2.x

## 项目结构

```
.
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── saucelabs
│   │               └── framework
│   │                   ├── core
│   │                   │   ├── Configuration.java
│   │                   │   └── DriverManager.java
│   │                   ├── pages
│   │                   │   ├── BasePage.java
│   │                   │   ├── PageFactory.java
│   │                   │   ├── android
│   │                   │   │   ├── LoginPageAndroid.java
│   │                   │   │   └── ProductsPageAndroid.java
│   │                   │   └── ios
│   │                   │       ├── LoginPageIOS.java
│   │                   │       └── ProductsPageIOS.java
│   │                   └── utils
│   │                       ├── ScreenshotUtils.java
│   │                       └── WaitUtils.java
│   └── test
│       ├── java
│       │   └── com
│       │       └── saucelabs
│       │           ├── runners
│       │           │   └── TestRunner.java
│       │           └── stepdefinitions
│       │               ├── Hooks.java
│       │               └── LoginStepDefinitions.java
│       └── resources
│           ├── features
│           │   └── login.feature
│           ├── apps
│           │   └── README.md
│           ├── config.properties
│           └── logback.xml
└── pom.xml
```

## 前提条件

- Java JDK 8或更高版本
- Maven
- Appium Server 2.x
- Android SDK (用于Android测试)
- Xcode (用于iOS测试)
- Android模拟器或真实设备
- iOS模拟器或真实设备

## Appium配置

本框架支持Android和iOS使用不同端口的Appium服务器实例：

- Android: 127.0.0.1:4723
- iOS: 127.0.0.1:4724

### 启动Appium服务器

#### Android服务器:
```bash
appium --port 4723 --base-path /wd/hub
```

#### iOS服务器:
```bash
appium --port 4724 --base-path /wd/hub
```

## 配置

框架使用位于`src/test/resources/config.properties`的配置文件。你可以修改此文件来配置测试环境：

```properties
# 一般设置
platform=android  # 或 'ios'

# Appium设置
appium.android.url=http://127.0.0.1:4723
appium.ios.url=http://127.0.0.1:4724

# Android设置
android.device.name=Android设备名称
android.app.package=com.swaglabsmobileapp
android.app.activity=com.swaglabsmobileapp.MainActivity
# 如果需要安装APK，提供路径
# android.app.path=/path/to/SauceLabs-Demo-App.apk

# iOS设置
ios.device.name=iPhone设备名称
ios.app.path=/path/to/SauceLabs-Demo-App.app
# 如应用已安装，可以提供bundleId
# ios.bundle.id=com.swaglabsmobileapp
```

## 被测应用

该框架设置为测试SauceLabs Sample App。你需要下载它并放置在`src/test/resources/apps/`目录中，或者修改配置文件中的路径。

## 运行测试

### 命令行

从命令行运行测试：

```bash
# 使用默认平台(来自config.properties)
mvn clean test

# 使用特定平台
mvn clean test -Dplatform=android
mvn clean test -Dplatform=ios
```

### IDE

你也可以通过在IDE中运行`TestRunner`类来直接运行测试。

## 测试报告

运行测试后，可以在以下位置找到测试报告：

- HTML报告: `target/cucumber-reports/report.html`
- JSON报告: `target/cucumber-reports/report.json`

## 添加新测试

1. 在`src/test/resources/features`目录中创建新的feature文件
2. 在`src/test/java/com/saucelabs/stepdefinitions`目录中创建新的步骤定义类
3. 在`src/main/java/com/saucelabs/framework/pages/android`和`src/main/java/com/saucelabs/framework/pages/ios`目录中创建新的页面对象
4. 更新`PageFactory`类以处理新的页面对象

## 故障排除

1. 确保Appium服务器正在运行，并在正确的端口上
2. 验证Android模拟器或iOS模拟器是否正在运行
3. 检查`config.properties`中的配置
4. 确保应用程序路径正确且应用程序文件存在
5. 检查设备连接状态和设备名称是否正确

## 许可证

本项目以MIT许可证授权。 