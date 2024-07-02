package com.health.BMI_check;

import com.health.BMI_check.entity.BodyData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BmiService {

    //field
    private BmiMapper bmiMapper;

    //constructor
    public BmiService(BmiMapper bmiMapper) {
        this.bmiMapper = bmiMapper;
    }

    //method

    //全件取得
    public List<BodyData> findAll() {
        return bmiMapper.findAll();
    }

    //クエリ文字で部分一致検索
    public List<BodyData> findByNamesStartingWith(String startsWith) {
        return bmiMapper.findByNameStartingWith(startsWith);
    }

    //Id で分岐処理（実在するIdを持つユーザー情報の検索 と 例外処理）
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
