package com.mossony.framwork;

import org.junit.Test;

public class ControllerRequestPathTest {


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