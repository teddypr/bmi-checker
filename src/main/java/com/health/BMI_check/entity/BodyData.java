package com.health.BMI_check.entity;

public class BodyData {

    //field作成(id, name, height, weight, birthday の変数を定義)
    private Integer id;
    private String name;
    private int age;
    private double height;
    private double weight;
    private double bmi;

    //Constructor作成
    public BodyData(Integer id, String name, int age, double height, double weight, double bmi) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
    }

    //getter作成
    public Integer getId() {
        return id;
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

    public double getBmi() {
        return bmi;
    }
}
