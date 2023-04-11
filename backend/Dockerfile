# ベースイメージ
FROM gradle:7.3.0-jdk11 AS build

# 作業ディレクトリの設定
WORKDIR /app

# ソースコードのコピー
COPY . .

# Ktor アプリケーションのビルド
RUN gradle build --no-daemon

# 実行用イメージ
FROM openjdk:11-jre-slim

# アプリケーションのコピー
COPY --from=build /app/build/libs/*.jar /app.jar

# ポートのエクスポート
EXPOSE 8080

# アプリケーションの実行
CMD ["java", "-jar", "/app.jar"]