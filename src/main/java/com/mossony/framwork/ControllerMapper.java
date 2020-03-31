package com.mossony.framwork;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.inject.Injector;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Getter;

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
    public static final String CONTROLLER_PACKAGE_SUFFIX = "controller";
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
            String[] classNames = packageClassNames();
            mapControllers(classNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mapControllers(String[] controllerClassNames) throws ClassNotFoundException {
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

    private String[] packageClassNames() throws IOException {
        Pattern pattern = Pattern.compile(".*\\." + CONTROLLER_PACKAGE_SUFFIX + "\\..*" + CONTROLLER_CLASS_SUFFIX_REGEX);

        ImmutableSet<ClassPath.ClassInfo> allClasses = ClassPath.from(MossServlet.class.getClassLoader()).getAllClasses();

        return allClasses.stream().filter(x -> pattern.matcher(x.getName()).matches()).map(x -> x.getName()).toArray(String[]::new);
    }


}
