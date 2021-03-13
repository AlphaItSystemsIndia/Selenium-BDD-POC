package com.amazon.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProjectProperties {
    private static final Properties projectProperties = new Properties();
    private static final String RESOURCE_FILE_NAME = "project.properties";
    private static final String KEY_HOME_URL = "HOME_URL";
    private static final String KEY_CHROME_DRIVER_FILE = "CHROME_DRIVER_FILE";

    static {
        InputStream resourceInputStream = null;
        try {
            resourceInputStream = ProjectProperties.class.getClassLoader().getResourceAsStream(RESOURCE_FILE_NAME);
            projectProperties.load(resourceInputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (resourceInputStream != null) {
                try {
                    resourceInputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static String getHomeUrl() {
        return projectProperties.getProperty(KEY_HOME_URL);
    }

    public static String getChromeDriverFilePath() {
        return projectProperties.getProperty(KEY_CHROME_DRIVER_FILE);
    }

    public static String getProperty(String key) {
        return projectProperties.getProperty(key);
    }
}
