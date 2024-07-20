package com.health.bmi_checker.service;

import com.health.bmi_checker.DataNotFoundException;
import com.health.bmi_checker.DuplicateNameException;
import com.health.bmi_checker.entity.BodyData;
import com.health.bmi_checker.mapper.BmiMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BmiService {

    //dependency injection
    private final BmiMapper bmiMapper;

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
    public List<BodyData> findAcronym(String startsWith) {
        return bmiMapper.findByNameStartingWith(startsWith);
    }

    //Id で分岐処理
    public BodyData findId(int id) {
        Optional<BodyData> bodyData = bmiMapper.findById(id);
        if (bodyData.isPresent()) {
            return bodyData.get();
        } else {
            throw new DataNotFoundException("Data not found");
        }
    }

    /**
     * ユーザーに関するCreate業務処理を担う
     */
    public BodyData insert(String name, int age, double height, double weight) {
        // 同姓同名の確認
        List<BodyData> existingData = findAll();
        for (BodyData data : existingData) {
            if (data.getName().equals(name)) {
                throw new DuplicateNameException("同じ名前のデータが既に存在します");
            }
        }

        BodyData bodyData = new BodyData(name, age, height, weight);
        bmiMapper.insert(bodyData);
        return bodyData;
    }

}
