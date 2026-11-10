# 小区物业管理系统

基于 **SSH（Struts2 + Spring + Hibernate3）** 的小区物业管理系统，后台管理界面使用 EasyUI，业主前端使用 Bootstrap。

原项目地址：<https://github.com/chengzhipcx/wuye>

本项目在原仓库基础上完成了**本地可运行化改造**，并修复了原仓库的若干缺陷（见下文"本次完善内容"）。

## 功能

**管理员端**（超管可管理普通管理员）

- 小区房产信息管理：录入/修改/删除/查询房产地址、面积、户型、出售状态
- 小区住户信息管理：住户电话、姓名、物业地址、身份证号、入住时间等
- 小区收费管理：物业收费与仪表收费（水费/电费/宽带费/物业费），缴费状态跟踪
- 小区故障报修处理：报修录入、经办人、处理时间、解决状态
- 用户投诉管理：投诉列表与处理
- 用量管理（本次补全）：住户水/电/燃气用量录入与查询

**业主前端**

- 登录、报修申请、网上投诉
- 缴费查询（未缴费记录提醒）
- 历史用量查看（Highcharts 3D 饼图统计）
- 未缴费邮件通知（管理员端触发，SMTP 配置后启用）

## 快速开始

本仓库自带便携运行时（`runtime/` 目录，不入库），无需安装 MySQL/Tomcat：

| 依赖 | 版本 | 说明 |
|---|---|---|
| JDK | 1.8 | 本机已装 `C:\Program Files\Java\jdk1.8.0_333` |
| MariaDB | 10.6.28 | 便携版，位于 `runtime/mariadb`，端口 **3307** |
| Tomcat | 9.0.123 | 便携版，位于 `runtime/tomcat`，端口 **8088** |

### 一键启动

```bash
# 1. 启动数据库（保持窗口开启）
scripts\start-db.bat

# 2. 构建并部署应用（需要 JDK 1.8 + Git Bash）
bash scripts/build.sh     # 编译源码并打包 WAR
bash scripts/deploy.sh    # 部署到 runtime/tomcat 并启动
```

启动完成后访问：

| 入口 | 地址 |
|---|---|
| 业主前端 | <http://localhost:8088/PropertyManagerSystem/> |
| 管理员后台 | <http://localhost:8088/PropertyManagerSystem/views/admin/login.jsp> |

### 测试账号

| 端 | 账号 | 密码 | 备注 |
|---|---|---|---|
| 管理员后台 | admin | admin | 超级管理员 |
| 业主前端 | zhangsanfeng | 111 | 绑定房产"12栋1单元501" |
| 业主前端 | 111 | 111 | 绑定房产"11栋3单元1104" |

> 注意：原 README 中 `zhangsanfeng/zhangsanfeng` 已过时，数据库 dump 中该用户密码为 `111`。

### 停止

```bash
scripts\stop-db.bat                    # 停数据库
runtime\tomcat\bin\shutdown.bat        # 停应用
```

## 从零初始化数据库

`程序说明/PropertyManagerDB.sql` 为完整建库脚本（含测试数据），已修复原版缺陷：

```bash
# 数据库服务启动后（端口3307）
runtime/mariadb/bin/mariadb.exe -u root -P 3307 -e "CREATE DATABASE IF NOT EXISTS PropertyManagerDB DEFAULT CHARSET utf8"
runtime/mariadb/bin/mariadb.exe -u root -P 3307 --default-character-set=utf8 PropertyManagerDB < 程序说明/PropertyManagerDB.sql
```

数据库连接配置在 `src/db.properties`（默认指向 `localhost:3307`，密码为空）。

## 项目结构

```
├── src/                      # Java 源码 + Spring/Struts 配置
│   ├── com/onlymvp/
│   │   ├── action/           # Struts2 控制器(注解式, JSON 输出)
│   │   ├── dao/              # DAO 接口与 Hibernate 实现
│   │   ├── service/          # 业务层
│   │   ├── entity/           # Hibernate 注解实体
│   │   ├── dto/              # 多表联查数据传输对象
│   │   ├── interceptor/      # 管理员拦截器
│   │   └── filter/           # 统一认证过滤器(本次新增)
│   ├── applicationContext.xml / struts.xml / db.properties / mail.properties
├── WebContent/               # Web 资源(JSP/EasyUI/Bootstrap/库文件)
│   └── WEB-INF/lib/          # 依赖 jar(含补充的 mail/connector)
├── 程序说明/                  # 数据库脚本 PropertyManagerDB.sql(已修复补全)
├── scripts/                  # 构建与部署脚本(build.sh / deploy.sh / start-db.bat)
├── runtime/                  # 本地运行时(便携 Tomcat/MariaDB, 不入库)
└── build/                    # 构建产物(不入库)
```

## 本次完善内容

原仓库存在多处导致**无法运行**的问题，本次全部修复：

### 缺失代码补全

| 缺失 | 处理 |
|---|---|
| `SendMail` 工具类（缴费邮件通知，仓库未提交） | 按调用方约定重写实现，SMTP 参数走 `src/mail.properties`，未配置时自动跳过不影响主流程 |
| `MailUtils` 误引用 | 移除无用 import |
| `dosage`（历史用量）整块后端：实体/DAO/Service/Action、`DosageInfo` 表、后台页面 `views/admin/dosage/dosageList.jsp` | 全部按项目既有风格补全，并在菜单/角色表中注册"用量管理" |
| 业主端 `highcharts1/`、`kindeditor/` 前端库目录（仓库未提交） | 下载 jQuery 1.12.4、Highcharts 4.2.7、KindEditor 4.1.10 补齐，并为文件上传接口提供简化实现 |

### 阻塞性 Bug 修复

| Bug | 修复 |
|---|---|
| `web.xml` 扫描包指向不存在的 `com.onlymvp.action.manager`，所有注解 Action 无法注册（首页外全部 404） | 改为 `com.onlymvp.action` |
| 前端全部依赖 `action!method.action` 动态方法调用，但 Struts 2.2 默认关闭 DMI | `struts.xml` 显式开启 |
| `UserInfo` 表缺 `qq`/`email` 列、`ChargeInfo` 表缺 `status` 列（实体已加字段但 SQL 脚本未同步），登录/缴费接口直接报错 | 运行库 ALTER 补列，`PropertyManagerDB.sql` 同步修复 |
| 后台登录页直接访问时 jQuery 404（无 `<base>` 标签，相对路径从 `/views/admin/` 解析） | 补 `<base href>` |

### 安全加固

- **新增统一认证过滤器 `AuthFilter`**：原项目所有后台页面与数据接口均无登录校验（`AdminInterceptor` 逻辑写反且未注册），任何人可直接调 `charge!list.action` 拿全部数据。现在：后台资源要求管理员会话、业主页面要求业主会话、其余接口要求任一已登录身份，登录/登出与静态资源放行。
- 修正 `AdminInterceptor` 反向逻辑（未登录放行、登录反而拦截）。
- `struts.devMode` 关闭（不再向前端泄露堆栈）。
- MySQL 驱动由 5.0.8 升级到 5.1.49（兼容 MariaDB 10.6 与更高版本 MySQL）。

### 工程化

- 新增 `scripts/build.sh`（编译+打包 WAR，含产物校验）、`scripts/deploy.sh`（停应用→完整部署→启动→健康检查）、`scripts/start-db.bat` / `stop-db.bat`。
- 邮件配置从 `db.properties` 拆分出 `src/mail.properties`。
- `.gitignore` 补充构建与运行时目录。

## 已知限制

- 管理员/业主密码为明文存储与比较（2016 年原设计），建议后续改造为加盐哈希。
- 部分模糊查询使用 HQL 字符串拼接，存在注入风险，建议后续参数化。
- 邮件通知需在 `src/mail.properties` 配置 SMTP 后生效。
- 若 3306/8088 端口被占用，请修改 `scripts/start-db.bat`、`runtime/tomcat/conf/server.xml` 与 `src/db.properties`。

## 关于作者

- 个人网站：[codercheng.xyz](https://codercheng.xyz)
- 微信公众号：**coder程**（微信搜一搜，或扫码关注）

<p align="center">
  <a href="https://codercheng.xyz"><img src="wechat-codercheng.png" width="560" alt="微信公众号：coder程"></a>
</p>
