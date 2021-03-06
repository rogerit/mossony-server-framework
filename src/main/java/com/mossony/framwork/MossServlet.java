package com.mossony.framwork;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.mossony.framwork.exception.MossException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @Author roger
 */
@Slf4j
@Singleton
public class MossServlet extends HttpServlet {
    public final static String APPLICATION_JSON_VALUE = "application/json;";

    public final static String DEFAULT_CHARSET = "UTF-8";

    private static Map<Class, Function<String, Object>> converterMap;

    {
        Map<Class, Function<String, Object>> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put(String.class, String::toString);
        objectObjectHashMap.put(Integer.class, Integer::valueOf);
        objectObjectHashMap.put(Long.class, Long::valueOf);
        objectObjectHashMap.put(Double.class, Double::valueOf);
        objectObjectHashMap.put(Boolean.class, Boolean::valueOf);
        objectObjectHashMap.put(LocalDate.class, LocalDate::parse);
        objectObjectHashMap.put(LocalDateTime.class, LocalDateTime::parse);
        converterMap = ImmutableMap.copyOf(objectObjectHashMap);
    }

    @Inject
    private ControllerMapper controllerMapper;

    @Inject
    HeaderInvoker invoker;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        Object result = null;
        try {
            String requestURI = req.getRequestURI().toLowerCase();

            ParameterMethod pm = controllerMapper.getRequest2MethodMap().get(req.getMethod()).get(requestURI);

            Object[] args = parseParams(req, resp, pm);

            Object data = invoker.invoke0(req, resp, pm, args);

            result = Response.success(data);
        } catch (Throwable e) {
            if (e instanceof InvocationTargetException) {
                e = ((InvocationTargetException) e).getTargetException();
            }
            Integer code = e instanceof MossException ? ((MossException) e).getCode() : -1;
            result = new Response(e.getMessage(), code, e.getMessage());
            e.printStackTrace();
        } finally {
            response(resp, result);
        }
    }


    private void response(HttpServletResponse resp, Object result) {
        try {
            if (!resp.isCommitted()) {
                resp.setCharacterEncoding(DEFAULT_CHARSET);
                resp.setContentType(APPLICATION_JSON_VALUE);
                OutputStream outputStream = resp.getOutputStream();
                String jsonString = JSONObject.toJSONString(result);
                outputStream.write(jsonString.getBytes(Charset.forName(DEFAULT_CHARSET)));
                outputStream.flush();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object[] parseParams(HttpServletRequest req, HttpServletResponse resp, ParameterMethod pm) throws IOException {
        Parameter[] parameters = pm.getParams();
        int paramLength = parameters.length;
        Object[] params = new Object[paramLength];
        for (int i = 0; i < paramLength; i++) {
            String name = parameters[i].getName();
            Class<?> type = parameters[i].getType();
            if (type.isAssignableFrom(HttpServletRequest.class)) {
                params[i] = req;
            } else if (type.isAssignableFrom(HttpServletResponse.class)) {
                params[i] = resp;
            } else if (name.equals("body")) {
                params[i] = JSONObject.parseObject(req.getInputStream(), parameters[i].getType());
            } else {
                String value = req.getParameter(name);
                if (value != null) {
                    params[i] = converterMap.get(parameters[i].getType()).apply(value);
                }
            }
        }
        return params;
    }

    @Getter
    @AllArgsConstructor
    static class Response {
        private String msg;
        private Integer code;
        private Object data;

        public static Response success(Object data) {
            return new Response("ok", 0, data);
        }

        public static Response error(MossException e) {
            return new Response(e.getMessage(), e.getCode(), null);
        }
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        doGet(req, resp);
    }

    @Override
    protected void doPut(final HttpServletRequest req, final HttpServletResponse resp) {
        doGet(req, resp);
    }

    @Override
    protected void doDelete(final HttpServletRequest req, final HttpServletResponse resp) {
        doGet(req, resp);
    }
}
