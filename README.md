# ぽけっとビルド - PocketBuild

ポケモンの構築を管理するアプリです。

## 特徴

- シングルバトルの構築管理
- 育成済みポケモンの管理

## 使用技術

- ktor
- exposed
- Next.js

## セットアップ方法

docker-composeを利用したセットアップが可能です。

1. このリポジトリをクローンします。
2. docker-compose.ymlのAUTH0_ISSUERとAUTH0_SECRETを環境に合わせて変更します。
3. docker-compose.yml があるディレクトリで"docker-compose up -d"を実行します。
4. ブラウザで http://localhost:3000 にアクセスします。

## ライセンス
MIT License