package com.huni.engineer.kokonutjava.proto;

import com.google.gson.annotations.SerializedName;

public class JSFoodInfo {
//    @SerializedName("userId")                   private String userId;
//    @SerializedName("Authentication")           private String sessionKey;
    @SerializedName("image")                    private String image;


    public JSFoodInfo() { ; }

//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getSessionKey() {
//        return sessionKey;
//    }
//
//    public void setSessionKey(String sessionKey) {
//        this.sessionKey = sessionKey;
//    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

//    @Override
//    public String toString() {
//        return "JSFoodInfo{" +
//                "userId='" + userId + '\'' +
//                ", sessionKey='" + sessionKey + '\'' +
//                ", image='" + image + '\'' +
//                '}';
//    }


    @Override
    public String toString() {
        return "JSFoodInfo{" +
                "image='" + image + '\'' +
                '}';
    }
}


