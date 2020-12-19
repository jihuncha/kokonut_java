package com.huni.engineer.kokonutjava.proto;

import com.google.gson.annotations.SerializedName;

public class JSLoginInfo {

    @SerializedName("loginId")          private String loginId;
    @SerializedName("loginPassword")    private String loginPassword;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    @Override
    public String toString() {
        return "JSLoginInfo{" +
                "loginId='" + loginId + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                '}';
    }
}
