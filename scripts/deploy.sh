#!/usr/bin/env bash
# 停止 Tomcat -> 重新部署 WAR -> 启动 Tomcat（需先运行 scripts/build.sh）
set -e
cd "$(dirname "$0")/.."

export JRE_HOME="C:\\Program Files\\Java\\jdk1.8.0_333"
CATALINA_BIN="runtime/tomcat/bin"
DEPLOY_DIR="runtime/tomcat/webapps/PropertyManagerSystem"

echo "[1/4] 停止 Tomcat..."
"$CATALINA_BIN/shutdown.bat" >/dev/null 2>&1 || true

# 等待 8088 端口真正释放(最多30秒),避免旧进程残留导致部署不完整
for i in $(seq 1 30); do
    if ! netstat -ano 2>/dev/null | grep -q ":8088.*LISTENING"; then
        break
    fi
    sleep 1
done

if netstat -ano 2>/dev/null | grep -q ":8088.*LISTENING"; then
    echo "警告: 端口仍被占用,尝试强制结束 java 进程..."
    taskkill //F //IM java.exe >/dev/null 2>&1 || true
    sleep 3
fi

echo "[2/4] 清理旧部署..."
rm -rf "$DEPLOY_DIR"
mkdir -p "$DEPLOY_DIR"

echo "[3/4] 部署新 WAR（解压目录方式）..."
cp -r build/war/. "$DEPLOY_DIR/"
CLASS_COUNT=$(find "$DEPLOY_DIR/WEB-INF/classes" -name "*.class" | wc -l)
if [ "$CLASS_COUNT" -eq 0 ]; then
    echo "错误: 部署目录中没有 class 文件,部署中止"
    exit 1
fi

echo "[4/4] 启动 Tomcat（部署了 $CLASS_COUNT 个 class）..."
cd "$CATALINA_BIN"
./startup.bat
cd - >/dev/null

# 等待应用就绪(最多60秒)
for i in $(seq 1 60); do
    CODE=$(curl -s -o /dev/null -w "%{http_code}" --max-time 5 "http://localhost:8088/PropertyManagerSystem/" 2>/dev/null)
    if [ "$CODE" = "200" ]; then
        echo "应用已就绪: http://localhost:8088/PropertyManagerSystem/ (HTTP $CODE)"
        exit 0
    fi
    sleep 1
done

echo "警告: 60秒内应用未就绪,请检查 runtime/tomcat/logs/catalina 日志"
exit 1
