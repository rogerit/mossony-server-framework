package com.mossony.framwork;

import com.newbanker.fac.undertow.servlet.ServletServer;
import com.newbanker.fac.undertow.servlet.controller.AnimalDogController;
import com.newbanker.fac.undertow.servlet.dao.UserMapper;
import com.newbanker.fac.undertow.servlet.dao.model.User;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Names;

import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.druid.DruidDataSourceProvider;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdk.nashorn.internal.runtime.regexp.joni.MatcherFactory;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

public class MossServletTest {

    @Test
    public void mapping1() {
        Map<String, Map<String, ParameterMethod>> request2MethodMap = Guice.createInjector().getInstance(ControllerMapper.class).getRequest2MethodMap();
        System.out.println(request2MethodMap);
    }

    @Test
    public void mybatis() throws IOException {
        Properties myBatisProperties = new Properties();
        myBatisProperties.setProperty("JDBC.url", "jdbc:mysql://localhost:3306/hello?useSSL=false&characterEncoding=utf8&connectionCollation=utf8mb4_unicode_ci");
        myBatisProperties.setProperty("JDBC.username", "root");
        myBatisProperties.setProperty("JDBC.password", "Passw0rd");
        myBatisProperties.setProperty("JDBC.driverClassName", "com.mysql.jdbc.Driver");
        myBatisProperties.setProperty("mybatis.environment.id", "hello");
        Injector injector = Guice.createInjector(new MyBatisModule() {
            @Override
            protected void initialize() {
                Names.bindProperties(binder(), myBatisProperties);
                bindDataSourceProviderType(DruidDataSourceProvider.class);

                bindTransactionFactoryType(JdbcTransactionFactory.class);
                addMapperClasses("com.newbanker.fac.undertow.servlet.dao");
            }
        });

        UserMapper userMapper = injector.getInstance(UserMapper.class);
        User user = userMapper.selectByPrimaryKey(123);
        System.out.println(user);
    }

    @Test
    public void controller() {
        String name = AnimalDogController.class.getName();
        Pattern pattern = Pattern.compile(".*\\.controller\\..*Controller$");
        boolean matches = pattern.matcher(name).matches();
        System.out.println(matches);
    }

    @Test
    public void time(){
        long l = Math.floorMod(LocalDate.now().toEpochDay() + 3, 7);
        LocalDateTime startOfDay = LocalDate.now().minusDays(l).atStartOfDay();
    }
}
