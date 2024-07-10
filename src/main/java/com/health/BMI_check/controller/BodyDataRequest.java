package com.health.BMI_check.controller;

public class BodyDataRequest {

    /**
     * ユーザー情報登録時に使うリクエストパラメータ
     */
    private String name;
    private int age;
    private double height;
    private double weight;

    public BodyDataRequest(String name, int age, double height, double weight) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }
}
