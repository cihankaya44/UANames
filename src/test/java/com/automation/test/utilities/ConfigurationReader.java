//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.outomation.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {
    private static Properties configFile;

    public ConfigurationReader() {
    }

    public static String getProperty(String key) {
        return configFile.getProperty(key);
    }

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("configuration.properties");
            configFile = new Properties();
            configFile.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException var1) {
            System.out.println("Failed to load properties file!");
            var1.printStackTrace();
        }

    }
}
