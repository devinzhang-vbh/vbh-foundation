package com.foundation.manage.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {


    public static String getPropertieyValue(String key) {
        String value = null;
        InputStream in = PropertiesUtil.class.getResourceAsStream("/config.properties");
        Properties p = new Properties();
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(in, "UTF-8");
            p.load(inputStreamReader);
            value = p.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void main(String[] args) {
        String domain = PropertiesUtil.getPropertieyValue("ms.domain");
        System.out.println("ms.domain：" + domain);
    }

}