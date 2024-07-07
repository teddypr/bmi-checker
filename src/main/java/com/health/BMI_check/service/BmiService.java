package com.health.BMI_check.service;

import com.health.BMI_check.DataNotFoundException;
import com.health.BMI_check.entity.BodyData;
import com.health.BMI_check.mapper.BmiMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BmiService {

    //dependency injection
    private BmiMapper bmiMapper;

    public BmiService(BmiMapper bmiMapper) {
        this.bmiMapper = bmiMapper;
    }


    // bmi 計算
    public double calculateBmi(double height, double weight) {
        return (weight / (height / 100 * height / 100));
    }

    public List<BodyData> BMIs() {
        List<BodyData> bodyDatas = bmiMapper.findAll();
        for (BodyData bodyData : bodyDatas) {
            double bmi = calculateBmi(bodyData.getHeight(), bodyData.getWeight());
        }
        return bodyDatas;
    }

    //全件取得
    public List<BodyData> findAll() {
        return bmiMapper.findAll();
    }

    //クエリ文字で部分一致検索
    public List<BodyData> findByNamesStartingWith(String startsWith) {
        return bmiMapper.findByNameStartingWith(startsWith);
    }

    //Id で分岐処理
    public BodyData findUser(int id) {
        Optional<BodyData> bodyData = bmiMapper.findById(id);
        if (bodyData.isPresent()) {
            return bodyData.get();
        } else {
            throw new DataNotFoundException("Data not found");
        }
    }

}

