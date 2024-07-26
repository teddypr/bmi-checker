package com.health.bmi_checker.controller;

import com.health.bmi_checker.entity.BodyData;
import com.health.bmi_checker.service.BmiService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public List<BodyData> findByNameStartingWith(@RequestParam String startsWith) {
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
        BodyDataResponse newData = new BodyDataResponse("従業員情報　登録完了");
        return ResponseEntity.created(location).body(newData);
    }

    /**
     * Update処理
     */
    @PatchMapping("/BMIs/{id}")
    public ResponseEntity<BodyDataResponse> update(@PathVariable int id, @RequestBody @Valid BodyDataRequest request, UriComponentsBuilder uriBuilder) {
        BodyData bodyData = bmiService.update(id, request.getName(), request.getAge(), request.getHeight(), request.getWeight());
        URI location = uriBuilder.path("/BMIs/{id}").buildAndExpand(bodyData.getId()).toUri();
        BodyDataResponse updatedData = new BodyDataResponse("従業員情報　更新完了");
        return ResponseEntity.status(HttpStatus.OK).header("Location", location.toString()).body(updatedData);
    }

    /**
     * Delete処理
     */
    @DeleteMapping("BMIs/{id}")
    public ResponseEntity<BodyDataResponse> delete(@PathVariable("id") int id) {
        BodyData bodyData = bmiService.delete(id);
        BodyDataResponse deleteData = new BodyDataResponse("従業員情報　削除完了");
        return ResponseEntity.ok(deleteData);
    }

}
