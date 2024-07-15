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
    - 同姓同名のユーザーが登録された場合は、ステータスコード 409 を、
    - 空のデータが登録された場合は、ステータスコード 400 を返す

200 の場合のレスポンス

```json
{
  "message": "New data is created"
}
```

409 の場合のレスポンス

```json
{
  "status": "409",
  "path": "/BMIs",
  "error": "Conflict",
  "timestamp": "2024-07-15T22:46:24.616755+09:00[Asia/Tokyo]",
  "message": "同じ名前のデータが既に存在します"
}
```

400 の場合のレスポンス

- 以下の条件いずれかを満たさずに入力した時

```json
{
  "name": "名前は必須項目です",
  "weight": "体重はキログラム単位で正の数でなければなりません",
  "age": "年齢は正の整数でなければなりません",
  "height": "身長の単位はメートルで正の数でなければなりません"
}
```

- json の形式が誤っている時（Postman に赤線が入る時）

```json
{
  "timestamp": "2024-07-15T13:53:24.763+00:00",
  "status": 400,
  "error": "Bad Request",
  "path": "/BMIs"
}
```
