# 最終課題

このプロジェクトでは Spring Boot と MySQL を使った CRUD 処理を持つ RESTful な Web API を作成します

# 開発環境

- Java 17
- Spring Boot 3.3.1
- MySQL 8

# API の概要

## 名前を全件取得するAPI

- リクエスト
    - Method:GET
    - URL:/bodyDatas
- レスポンス
    - ステータスコード：200
    - ボディ：ユーザーのリストを Json 形式で返す

```curl
curl --location 'http://localhost:8080/BMIs'

