package com.health.bmi_checker.controller;

/**
 * ユーザー情報登録時に使うリクエストパラメータ
 */
public class BodyDataRequest {

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
