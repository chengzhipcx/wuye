#!/usr/bin/env bash
# Maven 构建 Spring Boot WAR（依赖 JDK 1.8+ 与 Maven 3.x）
set -e
cd "$(dirname "$0")/.."

# 若本机默认 JDK 过新, 可指定 JAVA_HOME 后再运行
if [ -z "$JAVA_HOME" ]; then
    export JAVA_HOME="/c/Program Files/Java/jdk1.8.0_333"
fi

echo "[1/2] Maven 打包(跳过测试)..."
mvn package -DskipTests -q

echo "[2/2] 校验产物..."
WAR=target/wuye.war
if [ ! -f "$WAR" ]; then
    echo "错误: 未找到 $WAR, 构建失败"
    exit 1
fi

echo "构建完成: $WAR ($(du -sh "$WAR" | cut -f1))"
echo "启动方式: scripts/start-app.bat 或 java -jar target/wuye.war"
