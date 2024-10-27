package com.msb.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {
    private static Properties props;
    static{
        props = new Properties();
        try {
            props.load(PropertyMgr.class.getClassLoader()
                    .getResourceAsStream("config"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String get(String key){
        return (String)props.get(key);
    }
}
