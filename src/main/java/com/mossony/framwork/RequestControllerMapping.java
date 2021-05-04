package com.mossony.framwork;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RequestControllerMapping {

    public List<String> packageClassNames(String basePackage) {
        String pkgPath = basePackage.replace('.', '/');

        URL ctrlUrl = this.getClass().getResource("/" + pkgPath + "/controller");

        String[] controllers = new File(ctrlUrl.getPath()).list();

        List<String> ctrlClasses = Arrays.asList(controllers).stream()
                .filter(ctrl -> ctrl.endsWith("Controller.class"))
                .map(ctrl -> basePackage + ".controller." + ctrl.replace(".class",""))
                .collect(Collectors.toList());

        return ctrlClasses;
    }
}
