package com.mossony.framwork;

import com.mossony.framwork.example.DemoServer;
import org.junit.Test;

import java.util.List;

public class ControllerRequestPathTest {

    @Test
    public void testRestfullMapping() throws ClassNotFoundException {

        List<String> controllers = new RequestControllerMapping().packageClassNames(DemoServer.class.getPackage().getName());
        System.out.println(controllers);
        String ctrl = controllers.get(0);

        Class<?> aClass = Class.forName(ctrl);

        System.out.println(aClass);
        String c = pathingCamelName(ctrl);

        System.out.println(c);

    }

    @Test
    public void testPathingCamelName() {
        String ctrl = "TestPathingCamelNameController";
        String c = ctrl.substring(ctrl.lastIndexOf('.') + 1, ctrl.lastIndexOf('C'));
        System.out.println(pathingCamelName(c));
    }

    private String pathingCamelName(String camelStr) {
        String s = camelStr.toLowerCase();
        StringBuilder sb = new StringBuilder();
        int start = 0;
        for (int i = 0; i < camelStr.length(); i++) {
            if (camelStr.charAt(i) != s.charAt(i)) {
                sb.append(s.substring(start, i)).append('/');
                start = i;
            }
        }
        sb.append(s.substring(start));
        return sb.toString();
    }

}