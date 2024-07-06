package com.health.BMI_check;

import java.util.Scanner;

class BMIcheck {

    //dependency injection
    private BmiMapper bmiMapper;

    public BMIcheck(BmiMapper bmiMapper) {
        this.bmiMapper = bmiMapper;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String message = "";

        System.out.println("BMIを計算して、肥満度を表示します");

        System.out.println("身長を『m』で入力してください(150cmなら1.5と入力してください)");

        double height = scanner.nextDouble();
        System.out.println("体重を『Kg』で入力してください(42.5kgなら42.5と入力してください)");
        double weight = scanner.nextDouble();

        double bmi = weight / (height * height);

        if (bmi < 18.5) {
            message = "やせ型";
        } else if (bmi >= 18.5 && bmi < 25) {
            message = "標準体系";
        } else if (bmi >= 25 && bmi < 30) {
            message = "肥満1度";
        } else if (bmi >= 30 && bmi < 35) {
            message = "肥満2度";
        } else if (bmi >= 35 && bmi < 40) {
            message = "肥満3度";
        } else if (bmi >= 40)
            message = "肥満4度";


        System.out.println("あなたのBMIは" + bmi + "です。");
        System.out.println("あなたは" + message + "です。");

    }

}
