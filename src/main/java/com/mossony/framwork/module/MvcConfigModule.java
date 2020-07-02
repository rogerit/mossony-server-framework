package com.mossony.framwork.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取application.properties的配置信息
 */
public class MvcConfigModule extends AbstractModule {

    @Override
    protected void configure() {
        try {
            bindProperties();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    private void bindProperties() throws IOException {
        Properties properties = new Properties();
        File file = new File("./application.properties");
        InputStream resourceAsStream;
        if (file.exists()) {
            resourceAsStream = new FileInputStream(file);
        } else {
            resourceAsStream = getClass().getClassLoader().getResourceAsStream("application.properties");
        }
        properties.load(resourceAsStream);

        System.out.println("配置参数 :");
        System.out.println(properties);
        Names.bindProperties(binder(), properties);
    }

}
