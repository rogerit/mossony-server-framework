package com.mossony.framwork;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Injector;
import lombok.Getter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author roger
 * @Date $ $
 */
@Singleton
public class ControllerMapper {

    public static final String REQ_GET = "GET";
    public static final String REQ_POST = "POST";
    public static final String REQ_PUT = "PUT";
    public static final String REQ_DELETE = "DELETE";
    public static final String CONTROLLER_CLASS_SUFFIX_REGEX = "Controller$";

    @Getter
    private Map<String, Map<String, ParameterMethod>> request2MethodMap;

    private Injector parameterMethodInjector;

    @Inject
    public ControllerMapper(Injector parameterMethodInjector) {
        this.parameterMethodInjector = parameterMethodInjector;
        this.request2MethodMap = ImmutableMap.of(REQ_GET, new HashMap<>(),
                                                 REQ_POST, new HashMap<>(),
                                                 REQ_PUT, new HashMap<>(),
                                                 REQ_DELETE, new HashMap<>());
        try {
            List<String> classNames = packageClassNames();
            mapControllers(classNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mapControllers(List<String> controllerClassNames) throws ClassNotFoundException {
        for (String cname : controllerClassNames) {
            mapperController(cname);
        }
    }

    private void mapperController(String cname) throws ClassNotFoundException {
        Class<?> cClass = Class.forName(cname);

        String controllerPath = controllerClassPath(cname);

        Method[] declaredMethods = cClass.getDeclaredMethods();

        for (Method method : declaredMethods) {
            if (Modifier.isPublic(method.getModifiers())) {
                mapMethod(cClass, controllerPath, method);
            }
        }
    }

    private void mapMethod(Class<?> cClass, String controllerPath, Method method) {
        String reqType = method.getName().split("(?=[A-Z])")[0].toUpperCase();
        Map map = request2MethodMap.get(reqType);
        if (map != null) {
            String methodPath = controllerMethodPath(method);
            map.put("/" + controllerPath + methodPath, new ParameterMethod(parameterMethodInjector, method));
        }
    }


    private String controllerMethodPath(Method method) {
        String[] ss = method.getName().split("(?=[A-Z])");
        ss[0] = "";
        return String.join("/", ss).toLowerCase();
    }

    private String controllerClassPath(String cname) {
        String controllerName = cname.substring(cname.lastIndexOf('.') + 1);
        String cc = controllerName.replaceFirst(CONTROLLER_CLASS_SUFFIX_REGEX, "");
        String[] split = cc.split("(?=[A-Z])");
        String pathPrefix = String.join("/", split).toLowerCase();
        return pathPrefix;
    }

    private String getMainPackage() {
        for (StackTraceElement ste:Thread.currentThread().getStackTrace()){
            if (ste.getMethodName().equals("main")){
                String className = ste.getClassName();
                return className.substring(0,className.lastIndexOf('.'));
            }
        }
        return null;
    }

    private List<String> packageClassNames() {
        String basePackage = getMainPackage();

        String pkgPath = basePackage.replace('.', '/');

        URL ctrlUrl = this.getClass().getResource("/" + pkgPath + "/controller");

        String[] controllers = new File(ctrlUrl.getPath()).list();

        List<String> ctrlClasses = Arrays.asList(controllers).stream()
                .filter(ctrl -> ctrl.endsWith("Controller.class"))
                .map(ctrl -> basePackage + ".controller." + ctrl.replace(".class", ""))
                .collect(Collectors.toList());

        return ctrlClasses;
    }

}
