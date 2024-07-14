package com.health.bmi_checker.controller;

import com.health.bmi_checker.DataNotFoundException;
import com.health.bmi_checker.entity.BodyData;
import com.health.bmi_checker.service.BmiService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class BmiController {

    //dependency injection
    private final BmiService bmiService;

    public BmiController(BmiService bmiService) {
        this.bmiService = bmiService;
    }

    /**
     * READ処理を実装
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

    //例外処理を実装
    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDataNotFoundException(
            DataNotFoundException e, HttpServletRequest request) {
        Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),
                "status", String.valueOf(HttpStatus.NOT_FOUND.value()),
                "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "message", e.getMessage(),
                "path", request.getRequestURI());
        return new ResponseEntity(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Create処理を実装 ユーザー登録に関してHTTPリクエストを受け付ける
     */
    @PostMapping("/BMIs")
    public ResponseEntity<BodyDataResponse> insert(@RequestBody BodyDataRequest request, UriComponentsBuilder uriBuilder) {
        BodyData bodyData = bmiService.insert(request.getName(), request.getAge(), request.getHeight(), request.getWeight());
        URI location = uriBuilder.path("/BMIs/{id}").buildAndExpand(bodyData.getId()).toUri();
        BodyDataResponse newData = new BodyDataResponse("New data is created");
        return ResponseEntity.created(location).body(newData);
    }

}
