package com.newbanker.fac.undertow.servlet.controller;

import com.newbanker.fac.undertow.model.Animal;

import javax.inject.Singleton;

/**
 * @Author roger
 * @Date $ $
 */
@Singleton
public class AnimalDogController {

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
