package com.mossony.framwork;

import com.google.inject.Injector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import lombok.Getter;

/**
 * @Author roger
 */
@Getter
public class ParameterMethod {
    private Parameter[] params;
    private Method method;
    private Class clazz;
    private Object instance;
    private Injector injector;

    public ParameterMethod(Injector injector, Method method) {
        this.method = method;
        this.params = method.getParameters();
        this.injector = injector;
        this.clazz = method.getDeclaringClass();
    }

    public Object invoke(Object[] args) throws InvocationTargetException, IllegalAccessException {
        return this.method.invoke(instance(), args);
    }

    private Object instance() {
        return instance == null ? this.instance = injector.getInstance(this.clazz) : instance;
    }

}