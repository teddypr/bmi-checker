package com.health.bmi_checker.entity;

import java.util.Objects;

public class BodyData {

    // field (id, name, age, height, weight, bmi の変数を定義)
    private Integer id;
    private String name;
    private int age;
    private double height;
    private double weight;
    private double bmi;

    // Read 処理用の Constructor
    public BodyData(Integer id, String name, int age, double height, double weight) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.bmi = calculateBmi(height, weight); // BMIを計算してセット
    }

    // Create 処理用の Constructor (DBに登録する前にインスタンス化する時に使う)
    public BodyData(String name, int age, double height, double weight) {
        this.id = null;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.bmi = calculateBmi(height, weight);
    }


    // getter
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

    // setter
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    // 別のインスタンスでも、同じものとして扱うメソットequal
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BodyData bodyData = (BodyData) o;
        return age == bodyData.age && Double.compare(height, bodyData.height) == 0 && Double.compare(weight, bodyData.weight) == 0 && Double.compare(bmi, bodyData.bmi) == 0 && Objects.equals(id, bodyData.id) && Objects.equals(name, bodyData.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, height, weight, bmi);
    }

    @Override
    public String toString() {
        return "{\"id\":" + id +
                ",\"name\":\"" + name + "\"" +
                ",\"age\":" + age +
                ",\"height\":" + height +
                ",\"weight\":" + weight +
                ",\"bmi\":" + bmi + "}";
    }

}
