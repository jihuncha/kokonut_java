package com.huni.engineer.kokonutjava.common.data;

import com.huni.engineer.kokonutjava.proto.response.JSresponseAnalyze;

public class DailyFoodInfo {
    public static final String TAG = DailyFoodInfo.class.getSimpleName();

    //음식이름
    protected String foodName;
    //총 칼로리
    protected float calories;
    //탄수화물
    protected float carbohydrate;
    //단백
    protected float protein;
    //지방
    protected float fat;
    //imageKey
    protected String imageKey;
    //년월일 정보
    protected String date;
    //아침 점심 저녁 -> 아침 1, 점심 2, 저녁 3, ...
    protected int consumeTime;
    //path 추가
    protected String path;

    public DailyFoodInfo(){}

    public DailyFoodInfo(String foodName, int calories,
                         int carbohydrate, int protein,
                         int fat, String imageKey, String date, int consumeTime) {
        this.foodName = foodName;
        this.calories = calories;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.imageKey = imageKey;
        this.date = date;
        this.consumeTime = consumeTime;
    }

    public void set(JSresponseAnalyze input, String date, int consumeTime) {
        this.foodName = input.getName();
        this.calories = input.getCalories();
        this.carbohydrate = input.getCarbohydrate();
        this.protein = input.getProtein();
        this.fat = input.getFat();
        this.imageKey = input.getImageKey();
        this.date = date;
        this.consumeTime = consumeTime;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
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

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(int consumeTime) {
        this.consumeTime = consumeTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "DailyFoodInfo{" +
                "foodName='" + foodName + '\'' +
                ", calories=" + calories +
                ", carbohydrate=" + carbohydrate +
                ", protein=" + protein +
                ", fat=" + fat +
                ", imageKey='" + imageKey + '\'' +
                ", date='" + date + '\'' +
                ", consumeTime=" + consumeTime +
                ", path=" + path +
                '}';
    }
}
