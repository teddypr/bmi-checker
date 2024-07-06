package com.health.BMI_check.controller;

import com.health.BMI_check.DataNotFoundException;
import com.health.BMI_check.entity.BodyData;
import com.health.BMI_check.service.BmiService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/findNames")
    public List<BodyData> getBodyDatas(@RequestParam String startsWith) {
        List<BodyData> bodydatas = bmiService.findByNamesStartingWith(startsWith);
        return bmiService.findByNamesStartingWith(startsWith);
    }

    //Idを指定して検索するAPIを実装
    @GetMapping("/findNames/{id}")
    public BodyData findById(@PathVariable("id") int id) {
        return bmiService.findUser(id);
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

}
