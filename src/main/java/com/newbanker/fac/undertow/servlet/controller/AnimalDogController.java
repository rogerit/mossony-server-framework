package com.newbanker.fac.undertow.servlet.controller;

import com.newbanker.fac.undertow.model.Animal;
import com.newbanker.fac.undertow.servlet.dao.UserMapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author roger
 * @Date $ $
 */
@Singleton
public class AnimalDogController {

    @Inject
    private UserMapper userMapper;

    public Animal get(Integer age, String name) {
        Animal build = Animal.builder().name(name).age(age).build();
        return build;
    }


    public Animal postItem(Animal body, Boolean happy) {
        body.setAge(body.getAge() + 1);
        body.setHappy(happy);
        return body;
    }

    public Animal putAge(String name, Integer body) {

        Animal build = Animal.builder().name(name).age(body).build();
        return build;

    }

    public boolean deleteItem(String name) {
        return "haha".equals(name);
    }


}
