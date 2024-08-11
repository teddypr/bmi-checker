package com.health.bmi_checker.service;

import com.health.bmi_checker.controller.exceptionHandler.DataNotFoundException;
import com.health.bmi_checker.controller.exceptionHandler.DuplicateNameException;
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
        return weight / ((height / 100) * (height / 100));
    }

    public List<BodyData> BMIs() {
        List<BodyData> bodyDatas = bmiMapper.findAll();
        for (BodyData bodyData : bodyDatas) {
            double bmi = calculateBmi(bodyData.getHeight(), bodyData.getWeight());
            bodyData.setBmi(bmi);
        }
        return bodyDatas;
    }

    /**
     * 従業員情報に関する Read 処理を行う
     */
    //全件取得
    public List<BodyData> findAll() {
        return bmiMapper.findAll();
    }

    //クエリ文字で部分一致検索
    public List<BodyData> findAcronym(String startsWith) {
        return bmiMapper.findByNameStartingWith(startsWith);
    }

    //Id で従業員を検索
    public BodyData findId(int id) {
        Optional<BodyData> bodyData = bmiMapper.findById(id);
        if (bodyData.isPresent()) {
            return bodyData.get();
        } else {
            throw new DataNotFoundException("存在しない従業員 ID です: " + id);
        }
    }

    /**
     * 従業員情報に関するCreate業務処理を行う
     */
    public BodyData insert(String name, int age, double height, double weight) {
        // 同姓同名の確認
        if (bmiMapper.findByName(name).isPresent()) {
            throw new DuplicateNameException("同姓同名の従業員が既に存在します");
        }
        BodyData bodyData = new BodyData(name, age, height, weight);
        bmiMapper.insert(bodyData);
        return bodyData;
    }

    /**
     * 従業員情報に関する Update 処理を行う
     */
    public BodyData update(int id, String name, Integer age, Double height, Double weight) {
        BodyData bodyData = this.bmiMapper.findById(id).orElseThrow(() -> new DataNotFoundException("存在しない従業員 ID です: " + id));

        // 名前が変更された場合の重複チェック
        if (name != null && !bodyData.getName().equals(name)) {
            boolean duplicateNameExists = bmiMapper.findByNameAndNotId(name, id)
                    .isPresent();

            if (duplicateNameExists) {
                throw new DuplicateNameException("同姓同名の従業員が既に存在します");
            }
        }

        if (name != null) {
            bodyData.setName(name);
        }
        if (age != null) {
            bodyData.setAge(age);
        }
        if (height != null) {
            bodyData.setHeight(height);
        }
        if (weight != null) {
            bodyData.setWeight(weight);
        }

        bmiMapper.update(bodyData);
        return bodyData;
    }

    /**
     * 従業員情報に関する Delete 処理を行う
     */
    public BodyData delete(int id) {
        BodyData bodyData = this.bmiMapper.findById(id).orElseThrow(() -> new DataNotFoundException("存在しない従業員 ID です: " + id));
        bmiMapper.delete(id);
        return bodyData;
    }

}
