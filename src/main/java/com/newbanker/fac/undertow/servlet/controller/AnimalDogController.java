package com.newbanker.fac.undertow.servlet.controller;

import com.newbanker.fac.undertow.model.Animal;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    public void getHello(HttpServletResponse resp) throws IOException {
        resp.getWriter().write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h3>hello world</h3>\n" +
                "</body>\n" +
                "</html>");
        resp.flushBuffer();
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
