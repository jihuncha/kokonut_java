package com.huni.engineer.kokonutjava.common.data;

public class DailyFoodInfo {
    public static final String TAG = DailyFoodInfo.class.getSimpleName();

    //음식이름
    protected String foodName;
    //총 칼로리
    protected int calories;
    //탄수화물
    protected int carbohydrate;
    //단백
    protected int protein;
    //지방
    protected int fat;
    //imageKey
    protected String imageKey;
    //년월일 정보
    protected String date;
    //아침 점심 저녁 -> 아침 1, 점심 2, 저녁 3, ...
    protected int consumeTime;

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

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
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
                '}';
    }
}
