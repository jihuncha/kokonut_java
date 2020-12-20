package com.huni.engineer.kokonutjava.proto.response;

import com.google.gson.annotations.SerializedName;

public class JSresponseAnalyze {
    public JSresponseAnalyze() {}

    @SerializedName("name")
    protected String name;

    @SerializedName("probability")
    protected float probability;

    @SerializedName("calories")
    protected float calories;

    @SerializedName("carbohydrate")
    protected float carbohydrate;

    @SerializedName("protein")
    protected float protein;

    @SerializedName("fat")
    protected float fat;

    @SerializedName("imageKey")
    protected String imageKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(float carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    //    public long getProbability() {
//        return probability;
//    }
//
//    public void setProbability(long probability) {
//        this.probability = probability;
//    }
//
//    public long getCalories() {
//        return calories;
//    }
//
//    public void setCalories(long calories) {
//        this.calories = calories;
//    }
//
//    public long getCarbohydrate() {
//        return carbohydrate;
//    }
//
//    public void setCarbohydrate(long carbohydrate) {
//        this.carbohydrate = carbohydrate;
//    }
//
//    public long getProtein() {
//        return protein;
//    }
//
//    public void setProtein(long protein) {
//        this.protein = protein;
//    }
//
//    public long getFat() {
//        return fat;
//    }
//
//    public void setFat(long fat) {
//        this.fat = fat;
//    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    @Override
    public String toString() {
        return "JSresponseAnalyze{" +
                "name='" + name + '\'' +
                ", probability=" + probability +
                ", calories=" + calories +
                ", carbohydrate=" + carbohydrate +
                ", protein=" + protein +
                ", fat=" + fat +
                ", imageKey='" + imageKey + '\'' +
                '}';
    }
}
