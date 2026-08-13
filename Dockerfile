# ---- ビルドステージ（アプリをビルドしてjarファイルを作成する） ----
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Gradle関連のファイルを先にコピー
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 実行権限を付与（Linux環境でのエラー防止）
RUN chmod +x ./gradlew

# ソースコードをコピーしてビルド（テストはスキップ）
COPY src src
RUN ./gradlew build -x test

# ---- 実行ステージ（ビルドされたアプリを動かす） ----
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# ビルドステージで作成されたjarファイルをコピー
COPY --from=build /app/build/libs/*.jar app.jar

# Renderの環境変数PORTを使ってアプリケーションを起動
ENTRYPOINT ["java", "-jar", "app.jar"]