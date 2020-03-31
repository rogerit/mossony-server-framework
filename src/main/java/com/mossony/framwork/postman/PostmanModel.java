package com.mossony.framwork.postman;

import java.util.List;

import lombok.Data;

@Data
public class PostmanModel {

    private Info info;
    private List<Dir> item;
    private ProtocolProfileBehavior protocolProfileBehavior;

    @Data
    public static class Info {
        private String postmanID;
        private String name;
        private String schema;
    }


    @Data
    public static class Dir {
        private String name;
        private List<Item> item;
    }

    @Data
    public static class Item {
        private String name;
        private Request request;
        private Object[] response;
    }


    @Data
    public static class Request {
        private String method;
        private List<Header> header;
        private Body body;
        private URL url;

    }

    @Data
    public static class Body {
        private String mode;
        private String raw;
        private Options options;
    }

    @Data
    public static class Options {
        private Raw raw;
    }


    @Data
    public static class Raw {
        private String language;
    }


    @Data
    public static class Header {
        private String key;
        private String name;
        private String value;
        private String type;
    }

    @Data
    public static class URL {
        private String raw;
        private String protocol;
        private List<String> host;
        private Integer port;
        private String[] path;
        private List<Query> query;
    }

    @Data
    public static class Query {
        private String key;
        private String value;
    }

    public static class ProtocolProfileBehavior {
    }
}
