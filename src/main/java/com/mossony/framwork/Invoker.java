package com.mossony.framwork;

import com.google.inject.Injector;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public abstract class Invoker<T> {

    protected abstract boolean match(ParameterMethod method);

    public Invoker next;

    /**
     * @param request
     * @param pm
     * @param args
     * @return
     */
    Object invoke0(HttpServletRequest request, HttpServletResponse response, ParameterMethod pm, Object[] args) throws Exception {
        boolean match = match(pm);

        T beforeData = null;
        if (match) {
            beforeData = before(request, response, pm, args);
        }

        Object result = next == null ? pm.invoke(args) : next.invoke0(request, response, pm, args);

        if (match) {
            return after(request, response, beforeData, result);
        }

        return result;
    }

    protected T before(HttpServletRequest request, HttpServletResponse response, ParameterMethod pm, Object[] args) {
        return null;
    }

    protected Object after(HttpServletRequest request, HttpServletResponse response, T beforeData, Object invokeResult) {
        return invokeResult;
    }

    public static void insertAfterHeader(Injector injector, Class<? extends Invoker> invokerClazz) {

        HeaderInvoker headerInvoker = injector.getInstance(HeaderInvoker.class);

        Invoker authInterceptor = injector.getInstance(invokerClazz);

        authInterceptor.next = headerInvoker.next;

        headerInvoker.next = authInterceptor;

    }

}

