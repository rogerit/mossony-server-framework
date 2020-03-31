package com.newbanker.fac.undertow.servlet;

import com.newbanker.fac.undertow.servlet.dao.UserMapper;
import com.newbanker.fac.undertow.servlet.dao.model.User;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.mossony.framwork.MossServer;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.druid.DruidDataSourceProvider;
import org.mybatis.guice.transactional.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author roger
 * @Date $ $
 */
@Slf4j

public class ServletServer extends MossServer {

    @Transactional
    public static void main(final String[] args) {
        long start = System.currentTimeMillis();

        Injector injector = Guice.createInjector(new MyBatisModule() {
            @Override
            protected void initialize() {
                try {
                    Properties properties = new Properties();
                    InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("application.properties");
                    properties.load(resourceAsStream);
                    Names.bindProperties(binder(), properties);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bindDataSourceProviderType(DruidDataSourceProvider.class);
                bindTransactionFactoryType(JdbcTransactionFactory.class);
                addMapperClasses("com.newbanker.fac.undertow.servlet.dao");
            }
        });

        injector.getInstance(ServletServer.class).startServer(8080);

        log.info("启动时间{}ms", System.currentTimeMillis() - start);
    }


}
