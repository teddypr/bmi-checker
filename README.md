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
```

## クエリ文字列で指定した name のレコードを取得するAPI

- リクエスト
    - Method:GET
    - URL:/findNames?startsWith={name}
- レスポンス
    - ステータスコード：200
    - ボディ：ユーザーのリストを Json 形式で返す

```curl
curl --location 'http://localhost:8080/findNames?startsWith=%E3%82%BF'
```

## 指定した ID のレコードを取得するAPI

- リクエスト
    - Method:GET
    - URL:/BMIs/{id}
- レスポンス
    - ステータスコード：200
    - ボディ：ユーザーのリストを Json 形式で返す
    - ID が存在しない場合はステータスコード 404 を返す

```curl
curl --location 'http://localhost:8080/BMIs/1'
```

200 の場合のレスポンス

```json
{
  "id": 1,
  "name": "タナカ　イチロウ",
  "age": 20,
  "height": 171.5,
  "weight": 60.2
}
```

404 の場合のレスポンス

```json
{
  "message": "user not found",
  "timestamp": "2024-07-01T07:19:14.393+00:00[Asia/Tokyo]",
  "error": "Not Found",
  "path": "/BMIs/100",
  "status": 404
}
```

