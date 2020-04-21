package com.mossony.framwork;

import com.google.inject.Injector;
import com.mossony.framwork.module.MybatisModule;
import com.mossony.framwork.module.PropertiesModule;
import com.newbanker.fac.undertow.servlet.controller.AnimalDogController;
import com.newbanker.fac.undertow.servlet.dao.UserMapper;
import com.newbanker.fac.undertow.servlet.dao.model.User;

import com.google.inject.Guice;

import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Pattern;

public class MossServletTest extends MossDaoTest {


    @Test
    public void mapping1() {
        Map<String, Map<String, ParameterMethod>> request2MethodMap = Guice.createInjector().getInstance(ControllerMapper.class).getRequest2MethodMap();
        System.out.println(request2MethodMap);
    }

    @Test
    public void mybatis() throws IOException {
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
