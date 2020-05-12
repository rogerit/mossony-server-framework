package com.mossony.framwork;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mossony.framwork.module.MybatisModule;
import com.mossony.framwork.module.MvcConfigModule;

public class MossDaoTest {

    Injector injector = Guice.createInjector(new MvcConfigModule(), new MybatisModule("com.newbanker.fac.undertow.servlet.dao"));

}
