package com.huni.engineer.kokonutjava.proto.response;

import com.google.gson.annotations.SerializedName;

public class JSresponseLoginInfo {
    public JSresponseLoginInfo() {}

    @SerializedName("sessionKey")
    protected String sessionKey;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    @Override
    public String toString() {
        return "JSresponseLoginInfo{" +
                "sessionKey='" + sessionKey + '\'' +
                '}';
    }
}
