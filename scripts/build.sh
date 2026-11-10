#!/usr/bin/env bash
# 编译源码并打包 WAR（依赖 JDK 1.8，路径可在下方修改）
set -e
cd "$(dirname "$0")/.."

JDK8="/c/Program Files/Java/jdk1.8.0_333"
JAVAC="$JDK8/bin/javac"
JAR="$JDK8/bin/jar"

rm -rf build/classes build/war
mkdir -p build/classes build/war

echo "[1/4] 生成源码清单..."
find src -name "*.java" > build/sources.txt
echo "      共 $(wc -l < build/sources.txt) 个 Java 文件"

echo "[2/4] 编译..."
"$JAVAC" -encoding UTF-8 -cp "WebContent/WEB-INF/lib/*" -d build/classes @build/sources.txt

echo "[3/4] 复制资源文件..."
(cd src && find . -type f ! -name "*.java" -exec cp --parents {} ../build/classes \;)

echo "[4/4] 组装 WAR..."
cp -r WebContent/. build/war/
cp -r build/classes build/war/WEB-INF/classes
CLASS_COUNT=$(find build/war/WEB-INF/classes -name "*.class" | wc -l)
if [ "$CLASS_COUNT" -eq 0 ]; then
    echo "错误: WEB-INF/classes 中没有任何 class 文件，构建中止"
    exit 1
fi
(cd build/war && "$JAR" cf ../PropertyManagerSystem.war .)

echo "构建完成: build/PropertyManagerSystem.war ($(du -sh build/PropertyManagerSystem.war | cut -f1), 包含 $CLASS_COUNT 个 class)"
