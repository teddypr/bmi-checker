package com.health.BMI_check;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BmiController {

    //constructorを作成（dependency injectionによりNameMapperインスタンスが渡される）
    private BmiMapper bmiMapper;

    public BmiController(BmiMapper bmiMapper) {
        this.bmiMapper = bmiMapper;
    }

    @GetMapping("/BMIs")
    public List<BodyData> getBodyData() {
        List<BodyData> bodydatas = bmiMapper.findAll();
        return bodydatas;
    }

    @GetMapping("/bodyMathIndexes") //URLのパスに影響
    public List<BodyData> findByBodies(@RequestParam String startsWith) {
        List<BodyData> bodydatas = bmiMapper.findByNameStartingWith(startsWith);
        return bodydatas;
    }

}
