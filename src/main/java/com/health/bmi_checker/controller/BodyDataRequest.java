package com.health.bmi_checker.controller;

import com.health.bmi_checker.controller.ValidationConstraint.RoundDownConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * ユーザー情報登録時に使うリクエストパラメータ
 */

public class BodyDataRequest {

    @NotBlank(message = "名前は必須項目です")
    @Size(max = 20, message = "名前は {max} 字以内で入力してください")
    private String name;

    @NotNull(message = "年齢は必須項目です")
    @Positive(message = "年齢は正の整数でなければなりません")
    private int age;

    @NotNull(message = "身長は必須項目です")
    @Positive(message = "身長は単位メートルで正の数でなければなりません")
    @RoundDownConstraint(decimalPoint = 1, message = "身長は小数点第 {decimalPoint} 位までを入力してください")
    private double height;

    @NotNull(message = "体重は必須項目です")
    @Positive(message = "体重はキログラム単位で正の数でなければなりません")
    @RoundDownConstraint(decimalPoint = 1, message = "体重は小数点第 {decimalPoint} 位までを入力してください")
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


    // Update処理 に使用する setter
    public void setName(@NotBlank(message = "名前は必須項目です") @Size(max = 20, message = "名前は {max} 字以内で入力してください") String name) {
        this.name = name;
    }

    public void setAge(@NotNull(message = "年齢は必須項目です") @Positive(message = "年齢は正の整数でなければなりません") int age) {
        this.age = age;
    }

    public void setHeight(@NotNull(message = "身長は必須項目です") @Positive(message = "身長は単位メートルで正の数でなければなりません") double height) {
        this.height = height;
    }

    public void setWeight(@NotNull(message = "体重は必須項目です") @Positive(message = "体重はキログラム単位で正の数でなければなりません") double weight) {
        this.weight = weight;
    }

}
