package com.health.BMI_check.controller;

import com.health.BMI_check.entity.BodyData;
import com.health.BMI_check.service.BmiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class BodyDataController {
    private final BmiService bmiService;

    public BodyDataController(BmiService bmiService) {
        this.bmiService = bmiService;
    }

    /**
     * Create処理実装 ユーザー情報に関してHTTPリクエストを受け付けるController
     */
    @PostMapping("/BMIs")
    public ResponseEntity<BodyDataResponse> insert(@RequestBody BodyDataRequest request, UriComponentsBuilder uriBuilder) {
        BodyData bodyData = bmiService.insert(request.getName(), request.getAge(), request.getHeight(), request.getWeight());
        URI location = uriBuilder.path("/BMIs/{id}").buildAndExpand(bodyData.getId()).toUri();
        BodyDataResponse newData = new BodyDataResponse("New data is created");
        return ResponseEntity.created(location).body(newData);
    }

}
