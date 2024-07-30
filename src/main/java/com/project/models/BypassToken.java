package com.project.models;

public class BypassToken {
    private final String path;
    private final String method;

    public BypassToken(String path, String method) {
        this.path = path;
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }
}

