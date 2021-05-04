package com.mossony.framwork.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mossony.framwork.MossServer;
import com.mossony.framwork.module.MvcConfigModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoServer extends MossServer {

    public static void main(final String[] args) {


        long start = System.currentTimeMillis();

        Injector injector = Guice.createInjector(new MvcConfigModule());

        injector.getInstance(DemoServer.class).startServer();

        log.info("启动时间{}ms", System.currentTimeMillis() - start);
    }

}
