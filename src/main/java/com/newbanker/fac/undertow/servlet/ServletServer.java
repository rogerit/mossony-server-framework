package com.newbanker.fac.undertow.servlet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mossony.framwork.MossServer;
import com.mossony.framwork.module.MybatisModule;
import com.mossony.framwork.module.PropertiesModule;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.guice.transactional.Transactional;

/**
 * @Author roger
 * @Date $ $
 */
@Slf4j
public class ServletServer extends MossServer {

    @Transactional
    public static void main(final String[] args) {
        long start = System.currentTimeMillis();

        Injector injector = Guice.createInjector(new PropertiesModule(), new MybatisModule("com.newbanker.fac.undertow.servlet.dao"));

        log.info("start");
        injector.getInstance(ServletServer.class).startServer(8080);

        log.info("启动时间{}ms", System.currentTimeMillis() - start);
    }


}
