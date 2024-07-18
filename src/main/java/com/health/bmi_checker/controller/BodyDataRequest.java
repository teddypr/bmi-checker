package com.health.bmi_checker.controller;

import com.health.bmi_checker.DecimalConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * ユーザー情報登録時に使うリクエストパラメータ
 */

public class BodyDataRequest {

    @NotBlank(message = "名前は必須項目です")
    @Size(max = 20, message = "名前は20字以内で入力してください")
    private String name;

    @NotNull(message = "年齢は必須項目です")
    @Positive(message = "年齢は正の整数でなければなりません")
    private int age;

    @NotNull(message = "身長は必須項目です")
    @Positive(message = "身長の単位はメートルで正の数でなければなりません")
    @DecimalConstraint(message = "身長は小数点以下第一位までで入力してください")
    private double height;

    @NotNull(message = "体重は必須項目です")
    @Positive(message = "体重はキログラム単位で正の数でなければなりません")
    @DecimalConstraint(message = "体重は小数点以下第一位までで入力してください")
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