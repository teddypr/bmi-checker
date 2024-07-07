package com.health.BMI_check.entity;

public class BodyData {

    //field (id, name, age, height, weight, bmi の変数を定義)
    private Integer id;
    private String name;
    private int age;
    private double height;
    private double weight;
    private double bmi; //BMIを追加

    //Constructor
    public BodyData(Integer id, String name, int age, double height, double weight, double bmi) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.bmi = calculateBmi(height, weight); // BMIを計算してセット
    }

    //getter
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

    private double calculateBmi(double height, double weight) {
        return weight / (height * height / 10000);
    }

    //setter
    public void setBmi(double bmi) {
        this.bmi = bmi;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "BodyData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", bmi=" + bmi +
                '}';
    }

}
