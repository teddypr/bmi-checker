package com.health.bmi_checker.controller;

/**
 * ユーザー情報取得時に使うレスポンスパラメータ
 */
public class BodyDataResponse {

    private String message;

    public BodyDataResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    // Update用
    public void setMessage(String message) {
        this.message = message;
    }
}
