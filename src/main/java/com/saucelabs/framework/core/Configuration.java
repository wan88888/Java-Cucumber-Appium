package com.saucelabs.framework.core;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    private static final Properties properties = new Properties();
    private static final String ANDROID = "android";
    private static final String IOS = "ios";

    static {
        try (InputStream input = Configuration.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("Unable to find config.properties");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    /**
     * 获取测试运行平台
     * @return 平台: "android" 或 "ios"
     */
    public static String getPlatform() {
        // 优先使用系统属性，允许通过命令行参数覆盖
        String platformEnv = System.getProperty("platform");
        if (platformEnv != null && !platformEnv.isEmpty()) {
            return platformEnv.toLowerCase();
        }
        return properties.getProperty("platform", ANDROID).toLowerCase();
    }

    /**
     * 检查当前平台是否是Android
     * @return 如果当前平台是Android则返回true
     */
    public static boolean isAndroid() {
        return getPlatform().equals(ANDROID);
    }

    /**
     * 检查当前平台是否是iOS
     * @return 如果当前平台是iOS则返回true
     */
    public static boolean isIOS() {
        return getPlatform().equals(IOS);
    }

    /**
     * 获取Appium服务器URL
     * @return Appium服务器URL
     */
    public static String getAppiumServerUrl() {
        String platform = getPlatform();
        if (platform.equals(ANDROID)) {
            return properties.getProperty("appium.android.url", "http://127.0.0.1:4723");
        } else if (platform.equals(IOS)) {
            return properties.getProperty("appium.ios.url", "http://127.0.0.1:4724");
        } else {
            throw new RuntimeException("Unsupported platform: " + platform);
        }
    }

    /**
     * 获取设备能力配置
     * @return 设备能力配置
     */
    public static DesiredCapabilities getCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // 通用配置
        capabilities.setCapability("newCommandTimeout", getPropertyAsInt("appium.newCommandTimeout", 60));
        capabilities.setCapability("noReset", getPropertyAsBoolean("appium.noReset", false));
        capabilities.setCapability("fullReset", getPropertyAsBoolean("appium.fullReset", false));
        
        if (isAndroid()) {
            configureAndroidCapabilities(capabilities);
        } else if (isIOS()) {
            configureIOSCapabilities(capabilities);
        } else {
            throw new RuntimeException("Unsupported platform: " + getPlatform());
        }

        return capabilities;
    }

    /**
     * 配置Android平台的能力
     * @param capabilities 能力对象
     */
    private static void configureAndroidCapabilities(DesiredCapabilities capabilities) {
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, properties.getProperty("android.device.name"));
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        
        // Appium 2.x 特定配置
        capabilities.setCapability("appium:skipServerInstallation", getPropertyAsBoolean("android.skipServerInstallation", false));
        capabilities.setCapability("appium:skipDeviceInitialization", getPropertyAsBoolean("android.skipDeviceInitialization", false));
        capabilities.setCapability("appium:ignoreHiddenApiPolicyError", true);
        
        // 如果指定了app路径，则安装应用
        String appPath = properties.getProperty("android.app.path");
        if (appPath != null && !appPath.isEmpty()) {
            capabilities.setCapability(MobileCapabilityType.APP, appPath);
        } else {
            // 否则使用已安装的应用
            capabilities.setCapability("appPackage", properties.getProperty("android.app.package"));
            capabilities.setCapability("appActivity", properties.getProperty("android.app.activity"));
        }
        
        // 其他Android特定配置
        capabilities.setCapability("autoGrantPermissions", getPropertyAsBoolean("android.autoGrantPermissions", true));
        capabilities.setCapability("unicodeKeyboard", getPropertyAsBoolean("android.unicodeKeyboard", false));
        capabilities.setCapability("resetKeyboard", getPropertyAsBoolean("android.resetKeyboard", false));
        
        // 添加可选的自定义Android配置
        if (properties.containsKey("android.systemPort")) {
            capabilities.setCapability("systemPort", getPropertyAsInt("android.systemPort", 8200));
        }
    }

    /**
     * 配置iOS平台的能力
     * @param capabilities 能力对象
     */
    private static void configureIOSCapabilities(DesiredCapabilities capabilities) {
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, properties.getProperty("ios.device.name"));
        capabilities.setCapability(MobileCapabilityType.UDID, properties.getProperty("ios.device.udid"));
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, properties.getProperty("ios.platform.version"));
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        
        // Appium 2.x 特定配置
        capabilities.setCapability("appium:skipServerInstallation", getPropertyAsBoolean("ios.skipServerInstallation", false));
        capabilities.setCapability("appium:skipDeviceInitialization", getPropertyAsBoolean("ios.skipDeviceInitialization", false));
        capabilities.setCapability("appium:ignoreHiddenApiPolicyError", true);
        
        // 如果指定了app路径，则安装应用
        String appPath = properties.getProperty("ios.app.path");
        String bundleId = properties.getProperty("ios.bundle.id");
        
        if (appPath != null && !appPath.isEmpty()) {
            capabilities.setCapability(MobileCapabilityType.APP, appPath);
        } else if (bundleId != null && !bundleId.isEmpty()) {
            // 使用已安装的应用
            capabilities.setCapability("bundleId", bundleId);
        }
        
        // 其他iOS特定配置
        capabilities.setCapability("autoAcceptAlerts", getPropertyAsBoolean("ios.autoAcceptAlerts", true));
        capabilities.setCapability("useNewWDA", getPropertyAsBoolean("ios.useNewWDA", false));
        capabilities.setCapability("usePrebuiltWDA", getPropertyAsBoolean("ios.usePrebuiltWDA", false));
        capabilities.setCapability("showXcodeLog", getPropertyAsBoolean("ios.showXcodeLog", true));
        
        // 添加可选的自定义iOS配置
        if (properties.containsKey("ios.webDriverAgentUrl")) {
            capabilities.setCapability("webDriverAgentUrl", properties.getProperty("ios.webDriverAgentUrl"));
        }
        
        if (properties.containsKey("ios.wdaLocalPort")) {
            capabilities.setCapability("wdaLocalPort", getPropertyAsInt("ios.wdaLocalPort", 8100));
        }
    }

    /**
     * 将属性值解析为整数
     * @param key 属性键
     * @param defaultValue 默认值
     * @return 属性值的整数表示，如果无法解析则返回默认值
     */
    private static int getPropertyAsInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 将属性值解析为布尔值
     * @param key 属性键
     * @param defaultValue 默认值
     * @return 属性值的布尔表示，如果无法解析则返回默认值
     */
    private static boolean getPropertyAsBoolean(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }
} 