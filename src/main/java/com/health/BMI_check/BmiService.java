package com.health.BMI_check;

import com.health.BMI_check.entity.BodyData;
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

    //method
    //全件取得
    public List<BodyData> findAll() {
        return bmiMapper.findAll();
    }

    // 各BodyDataオブジェクトにBMIを計算してセット
    public List<BodyData> getAllPersons() {
        List<BodyData> bodyDatas = bmiMapper.findAll();
        for (BodyData bodyData : bodyDatas) {
            double bmi = calculateBmi(bodyData.getHeight(), bodyData.getWeight());
        }
        return bodyDatas;
    }

    //bmi の計算
    public double calculateBmi(double height, double weight) {
        return (weight / (height / 100 * height / 100));
    }


    //クエリ文字で部分一致検索
    public List<BodyData> findByNamesStartingWith(String startsWith) {
        return bmiMapper.findByNameStartingWith(startsWith);
    }

    //Id で分岐処理（実在するIdを持つユーザー情報を表示、実在しなければ例外処理）
    public BodyData findUser(int id) {
        //Optional は値を持っているかどうか分からない時に入れておく箱
        Optional<BodyData> bodyData = bmiMapper.findById(id);
        if (bodyData.isPresent()) {
            return bodyData.get();
        } else {
            throw new DataNotFoundException("Data not found");
        }

    }

}

