package com.huni.engineer.kokonutjava.proto;

import com.google.gson.annotations.SerializedName;

public class JSFoodInfo {
    @SerializedName("keep-alive-timeout")  private Integer keepAliveTimeout; //분단위
    @SerializedName("cauth-key-expire")    private Integer cAuthKeyExpire; //분단위
    public JSFoodInfo() { ; }

    public Integer getKeepAliveTimeout() {
        return keepAliveTimeout != null ? keepAliveTimeout : 0;
    }

    public void setKeepAliveTimeout(Integer keepAliveTimeout) {
        this.keepAliveTimeout = keepAliveTimeout;
    }

    public Integer getCAuthKeyExpire() {
        return cAuthKeyExpire != null ? cAuthKeyExpire : 0;
    }

    public void setCAuthKeyExpire(Integer cAuthKeyExpire) {
        this.cAuthKeyExpire = cAuthKeyExpire;
    }

    @Override
    public String toString() {
        return "JSAppConfig{" +
                "keepAliveTimeout=" + keepAliveTimeout +
                ", cAuthKeyExpire=" + cAuthKeyExpire +
                '}';
    }

}


