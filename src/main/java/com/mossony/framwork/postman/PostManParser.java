package com.mossony.framwork.postman;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mossony.framwork.ControllerMapper;
import com.mossony.framwork.ParameterMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @Author roger
 * @Date $ $
 */
@Singleton
public class PostManParser {

    @Inject
    private ControllerMapper controllerMapper;


    public String parse(String name, String host) {
        PostmanModel postmanModel = new PostmanModel();
        PostmanModel.Info info = new PostmanModel.Info();
        info.setName(name);
        info.setPostmanID("postman_id_" + name.hashCode());
        info.setSchema("https://schema.getpostman.com/json/collection/v2.1.0/collection.json");
        postmanModel.setInfo(info);
        postmanModel.setItem(new ArrayList<>());

        Map<Class, PostmanModel.Dir> dirs = new HashMap<>();
        PostmanModel.Header jsonHeader = new PostmanModel.Header();
        jsonHeader.setKey("Content-Type");
        jsonHeader.setName("Content-Type");
        jsonHeader.setValue("application/json");
        jsonHeader.setType("text");
        Map<String, Map<String, ParameterMethod>> mappingController = controllerMapper.getRequest2MethodMap();
        mappingController.forEach((reqType, parameterMethodMap) -> {
            parameterMethodMap.forEach((path, method) -> {
                PostmanModel.Dir dir = dirs.get(method.getClazz());
                if (dir == null) {
                    dir = new PostmanModel.Dir();
                    dir.setName(method.getClazz().getSimpleName());
                    dirs.put(method.getClazz(), dir);
                }

                List<PostmanModel.Item> items = dir.getItem();
                if (items == null) {
                    items = new ArrayList<>();
                    dir.setItem(items);
                }

                PostmanModel.Item item = new PostmanModel.Item();
                items.add(item);

                item.setName(method.getMethod().getName());

                PostmanModel.Body body = new PostmanModel.Body();

                PostmanModel.Request request = new PostmanModel.Request();

                request.setMethod(reqType.toUpperCase());

                PostmanModel.URL url = new PostmanModel.URL();
                url.setHost(Arrays.asList(host));
                url.setPath(path.split("/"));
                url.setProtocol("http");
                url.setQuery(new ArrayList<>());

                request.setUrl(url);

                Parameter[] params = method.getParams();
                for (Parameter parameter : params) {
                    if (parameter.getName().equals("body")) {
                        request.setHeader(Arrays.asList(jsonHeader));
                        body.setMode("raw");
                        body.setRaw(getBody(parameter));
                        PostmanModel.Options options = new PostmanModel.Options();
                        PostmanModel.Raw raw = new PostmanModel.Raw();
                        raw.setLanguage("json");
                        options.setRaw(raw);
                        body.setOptions(options);
                    } else {
                        PostmanModel.Query query = new PostmanModel.Query();
                        query.setKey(parameter.getName());
                        query.setValue(getDefaultValue(parameter.getType()).toString());
                        url.getQuery().add(query);
                    }
                }

                request.setBody(body);

                StringBuilder sb = new StringBuilder();
                sb.append(host).append(path).append("?");
                url.getQuery().forEach(x -> {
                    sb.append(x.getKey()).append("=").append(x.getValue()).append("&");
                });
                sb.deleteCharAt(sb.length() - 1);
                url.setRaw(sb.toString());

                item.setRequest(request);
                item.setResponse(new Object[0]);
            });
        });

        postmanModel.setItem(new ArrayList<>(dirs.values()));
        postmanModel.setProtocolProfileBehavior(new PostmanModel.ProtocolProfileBehavior());

        return JSONObject.toJSONString(postmanModel, SerializerFeature.PrettyFormat);

    }

    private String getBody(Parameter parameter) {
        try {
            Object value = getDefaultValue(parameter.getType());
            if (value == null) {
                value = parameter.getType().newInstance();
                Field[] fields = parameter.getType().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(value, getDefaultValue(field.getType()));
                }
            }
            return JSONObject.toJSONString(value, SerializerFeature.PrettyFormat);
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object getDefaultValue(Class clazz) {
        if (Number.class.isAssignableFrom(clazz))
            return 0;
        if (boolean.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz))
            return Boolean.TRUE;
        if (CharSequence.class.isAssignableFrom(clazz))
            return "hello";
        if (List.class.isAssignableFrom(clazz))
            return Collections.EMPTY_LIST;
        if (Map.class.isAssignableFrom(clazz))
            return Collections.EMPTY_MAP;
        if (Set.class.isAssignableFrom(clazz))
            return Collections.EMPTY_SET;
        if (LocalDateTime.class.isAssignableFrom(clazz))
            return LocalDateTime.now();
        if (LocalDate.class.isAssignableFrom(clazz))
            return LocalDate.now();
        if (URI.class.isAssignableFrom(clazz))
            return URI.create("http://mossony.com");
        return "null";
    }
}
