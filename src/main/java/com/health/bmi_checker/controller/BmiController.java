package com.health.bmi_checker.controller;

import com.health.bmi_checker.entity.BodyData;
import com.health.bmi_checker.service.BmiService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class BmiController {

    //dependency injection
    private final BmiService bmiService;

    public BmiController(BmiService bmiService) {
        this.bmiService = bmiService;
    }

    /**
     * READ処理
     */
    //テーブルのレコードを全件取得するAPIを実装
    @GetMapping("/BMIs")
    public List<BodyData> getBodyDatas() {
        List<BodyData> bodydatas = bmiService.findAll();
        return bodydatas;

    }

    //クエリ文字列を指定して検索するAPIを実装 @RequestParam＋型＋クエリ文字
    @GetMapping("/userNames")
    public List<BodyData> getBodyDatas(@RequestParam String startsWith) {
        List<BodyData> bodydatas = bmiService.findAcronym(startsWith);
        return bmiService.findAcronym(startsWith);
    }

    //Idを指定して検索するAPIを実装
    @GetMapping("/userNames/{id}")
    public BodyData findById(@PathVariable("id") int id) {
        return bmiService.findId(id);
    }


    /**
     * Create処理 ユーザー登録に関してHTTPリクエストを受け付ける
     */
    @PostMapping("/BMIs")
    public ResponseEntity<BodyDataResponse> insert(@RequestBody @Valid BodyDataRequest request, UriComponentsBuilder uriBuilder) {
        BodyData bodyData = bmiService.insert(request.getName(), request.getAge(), request.getHeight(), request.getWeight());
        URI location = uriBuilder.path("/BMIs/{id}").buildAndExpand(bodyData.getId()).toUri();
        BodyDataResponse newData = new BodyDataResponse("New data is created");
        return ResponseEntity.created(location).body(newData);
    }

}
