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
    - URL:/BMIs
- レスポンス
    - ステータスコード：200
    - ボディ：ユーザーのリストを Json 形式で返す

```curl
curl --location 'http://localhost:8080/BMIs'
```

## クエリ文字列で指定した name のレコードを取得するAPI

- リクエスト
    - Method:GET
    - URL:/userNames?startsWith={name}
- レスポンス
    - ステータスコード：200
    - ボディ：ユーザーのリストを Json 形式で返す

```curl
curl --location 'http://localhost:8080/userNames?startsWith=%E3%82%BF'
```

## 指定した ID のレコードを取得するAPI

- リクエスト
    - Method:GET
    - URL:/userNames/{id}
- レスポンス
    - ステータスコード：200
    - ボディ：ユーザーのリストを Json 形式で返す
    - ID が存在しない場合はステータスコード 404 を返す

```curl
curl --location 'http://localhost:8080/userNames/1'
```

200 の場合のレスポンス

```json
{
  "id": 1,
  "name": "タナカ　イチロウ",
  "age": 20,
  "height": 171.5,
  "weight": 60.2,
  "bmi": 20.46766228357232
}
```

404 の場合のレスポンス

```json
{
  "error": "Not Found",
  "timestamp": "2024-07-07T22:23:44.657353+09:00[Asia/Tokyo]",
  "message": "Data not found",
  "status": "404",
  "path": "/userNames/100"
}
```

## ユーザー情報を登録するAPI

- リクエスト
    - Method:POST
    - URL:/BMIs
- レスポンス
    - ステータスコード：201
    - ボディ：Json形式で "New data is created" を返す
    - 同姓同名のユーザーが登録された場合は、ステータスコード500サーバーエラーを返す

```curl
curl 
--location 'http://localhost:8080/BMIs' \
--header 'Content-Type: application/json' \
--data '{
        "name": "小野　千代",
        "age": 30,
        "height": 157.9,
        "weight": 55.3
}'
```
