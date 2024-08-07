# 従業員肥満度管理システム

## 使用技術一覧

## サービス概要

- このプロジェクトでは 従業員の身体情報を管理します。
- 名前、年齢、身長、体重をセットに id で紐づけます。
- 上記の内容について、読み取り、登録、更新、削除を行うことができます。
- 登録された身長、体重に対してBMI値が自動で算出され、読み取りで確認することができます。

## 開発環境

- Java 17
- Spring Boot 3.3.1
- MySQL 8

## 機能一覧

Open Api を使用して、下記にまとめました。
[BMIチェッカー/仕様書](https://teddypr.github.io/bmi-checker/)

## DBについて

### テーブル名： BMIs

| column |  data type   | Nou null | description |    memo    |
|:------:|:------------:|:--------:|:-----------:|:----------:|
|   id   |     int      | NOTNULL  |   id自動採番    | primaryKey |
|  name  | VARCHAR(255) | NOTNULL  |     名前      |   UNIQUE   |
|  age   |     int      | NOTNULL  |     年齢      |            |
| height |  FLOAT(5,1)  | NOTNULL  |     身長      |            |
| weight |  FLOAT(5,1)  | NOTNULL  |     体重      |            |

## シーケンス図

```mermaid

sequenceDiagram
    Client ->> WebAPI: ユーザーを全件取得(/BMIs)
    WebAPI ->> DB: DBにアクセス
    DB -->> WebAPI: ユーザー情報をリストで取得
    WebAPI -->> Client: 200 OK ユーザー情報をリストで取得
    Client ->> WebAPI: ユーザー情報を指定検索(/userNames/{条件})
    participant Client
    Note right of Client: query-search: /userNames/?startsWith={name} <br> path-search: /userNames/{id}
    WebAPI ->> DB: DBにアクセス
    DB -->> WebAPI: 指定検索をもとにユーザー情報を取得
    WebAPI -->> Client: 200 OK 指定されたユーザーを返す
    break
        DB -->> WebAPI: 指定したユーザーが存在しない
    end
    break
        WebAPI -->> Client: 404 NotFoundを返す
    end

    Client ->> WebAPI: ユーザー情報を新規登録(/BMIs)
    WebAPI ->> DB: ユーザーを登録
    break
        WebAPI -->> Client: 400 Bad Request Validationエラー
    end
    DB -->> WebAPI: IDを自動採番して登録
    WebAPI -->> Client: 201 create 登録されたことを返す
    break
        DB -->> WebAPI: 指定したユーザーが存在しない
    end
    break
        WebAPI -->> Client: 404 NotFound を返す
    end
    break
        DB -->> WebAPI: 既存のユーザーと同じ name を登録
    end
    break
        WebAPI -->> Client: 409 Conflict を返す
    end
    Client ->> WebAPI: 更新したいユーザー情報を送信(/BMIs/{id})
    break
        WebAPI -->> Client: 400 Bad Request Validationエラー
    end
    WebAPI ->> DB: DBにアクセス
    DB -->> WebAPI: 指定したidのユーザーを更新
    WebAPI -->> Client: 200 OK 更新されたことを返す
    break
        DB -->> WebAPI: 指定したユーザーが存在しない
    end
    break
        WebAPI -->> Client: 404 NotFound を返す
    end

    Client ->> WebAPI: 削除したいユーザーidを送信(/BMIs/{id})
    WebAPI ->> DB: DBにアクセス
    DB -->> WebAPI: 指定したidのユーザーを削除
    WebAPI -->> Client: 200 OK 削除されたことを返す
    break
        DB -->> WebAPI: 指定したユーザーが存在しない
    end
    break
        WebAPI -->> Client: 404 NotFound を返す
    end

```

## 自動テスト

以下の自動テストを実装

- BmiService の単体テスト
- BmiMapperのDBテスト
- Integrationテスト（結合テスト）
- GitHub Actions
