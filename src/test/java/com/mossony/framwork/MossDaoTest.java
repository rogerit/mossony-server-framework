package com.mossony.framwork;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mossony.framwork.module.MybatisModule;
import com.mossony.framwork.module.PropertiesModule;

public class MossDaoTest {

    Injector injector = Guice.createInjector(new PropertiesModule(), new MybatisModule("com.newbanker.fac.undertow.servlet.dao"));

}
