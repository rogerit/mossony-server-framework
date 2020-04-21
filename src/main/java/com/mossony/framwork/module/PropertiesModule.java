package com.mossony.framwork.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取application.properties的配置信息
 */
public class PropertiesModule extends AbstractModule {

    @Override
    protected void configure() {
        try {
            Properties properties = new Properties();
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            properties.load(resourceAsStream);
            Names.bindProperties(binder(), properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
