package com.health.BMI_check.controller;

import com.health.BMI_check.BmiMapper;
import com.health.BMI_check.entity.BodyData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BmiController {

    private BmiMapper bmiMapper;

    //constructorを作成（dependency injectionによりNameMapperインスタンスが渡される）
    public BmiController(BmiMapper bmiMapper) {
        this.bmiMapper = bmiMapper;
    }

    /**
     * READ処理を実装
     */
    //BMIsテーブルの情報を全件取得するAPIを実装
    @GetMapping("/BMIs")
    public List<BodyData> getBodyDatas() {
        List<BodyData> bodydatas = bmiMapper.findAll();
        return bodydatas;
    }

    //クエリ文字列を指定して検索するAPIを実装
    @GetMapping("/findNames")
    public List<BodyData> getBodyDatas(@RequestParam String startsWith) {
        List<BodyData> bodydatas = bmiMapper.findByNameStartingWith(startsWith);
        return bmiMapper.findByNameStartingWith(startsWith);
    }

}

//service クラス
//        if (bmi.BodyData() < 18.5) {
//            System.out.println("評価：低体重");
//
//        } else if (getBodyData() < 25) {
//            System.out.println("評価：普通体重");
//
//        } else {
//            System.out.println("評価：肥満");
//        }

