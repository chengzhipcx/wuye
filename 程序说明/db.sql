-- 创建数据库
CREATE DATABASE PropertyManagerDB DEFAULT charset utf8;

USE PropertyManagerDB;

CREATE TABLE IF NOT EXISTS AdminInfo -- 管理员表
(
	id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,  -- 主键
	userName VARCHAR NOT NULL, -- 登录名
	userPwd VARCHAR NOT NULL, -- 登录密码
	userLevel INT NOT NULL, -- 管理员级别,"0"超级管理员,"1"普通管理员
	userStatus INT NOT NULL, -- 开启关闭状态,"1"开启,"0"关闭,默认开启
	createTime INT NOT NULL -- 创建时间
);

CREATE TABLE IF NOT EXISTS MenuInfo -- 菜单表
(
	id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,  -- 主键
	name VARCHAR NOT NULL, -- 菜单名
	parentId INT NOT NULL, -- 父菜单,"0"默认为父类
	url VARCHAR, -- 菜单打开url,父菜单为空
	switchStatus INT NOT NULL, -- 开关,"0"为关闭,"1"为开启
	delStatus INT NOT NULL, -- 逻辑删除,"0"为未删除,"1"为已删除
	orderBy INT, -- 排序规则
	createTime DATATIME NOT NULL -- 创建时间
);
ALTER TABLE MenuInfo ADD UNIQUE (name);  -- 创建唯一索引

CREATE TABLE IF NOT EXISTS MenuAndRole -- 管理员菜单权限表
(
	id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,  -- 主键
	menuId INT NOT NULL, -- 菜单表主键ID
	adminId INT NOT NULL -- 管理员表主键ID
);

-- 房产基本信息的录入，包括物业地址、使用面积、房屋结构、出售信息等；房产基本信息的修改；房产基本信息的删除；房产基本信息的查询。
CREATE TABLE IF NOT EXISTS PropertyInfo -- 房产表
(
	id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,  -- 主键
	address VARCHAR NOT NULL, -- 地址,唯一约束
	area INT NOT NULL, -- 使用面积
	layout VARCHAR NOT NULL, -- 房屋布局结构
	saleStatus INT NOT NULL, -- 出售信息,"0"未出售","1"已出售,默认"0"
	delStatus INT NOT NULL, -- 删除状态,"0"未删除,"1"已删除
	createTime DATETIME NOT NULL -- 创建日期
);
-- 小区收费管理功能:其中包括物业收费和仪表收费两大类信息的录入，包括收费住址、水费、电费、宽带费、物业管理费、收费时间等信息;
-- 收费基本信息的修改;收费基本信息的删除;收费基本信息的查询。  
CREATE TABLE IF NOT EXISTS ChargeInfo -- 小区收费表,水费,电费,宽带费,物业管理费,按房产收费
(
	id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,  -- 主键
	userId INT NOT NULL, -- 业主ID,UserInfo表主键ID
	propertyId INT NOT NULL, -- 缴费的房产,PropertyInfo表主键ID
	chargeType INT NOT NULL, -- 缴费类型,"1"水费,"2"电费,"3"宽带费,"4"物业管理费
	payment INT NOT NULL, -- 缴费金额
	remark VARCHAR NOT NULL, -- 缴费备注
	delStatus INT NOT NULL, -- 是否删除,"0"为未删除,"1"为已删除
	createTime DATETIME NOT NULL, -- 缴费日期
);

CREATE TABLE IF NOT EXISTS RepairAndComplainInfo -- 报修,投诉信息表
(
	id INT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 主键
	userId INT NOT NULL, -- UserInfo表主键ID
	propertyId INT NOT NULL, -- PropertyInfo表主键ID
	content VARCHAR NOT NULL, -- 报修,投诉内容
	infoType INT NOT NULL, -- 类型 "1"报修,"2"投诉
	dealUser VARCHAR, -- 经手人
	solveStatus INT NOT NULL, -- 是否解决,"0"未解决,"1"已解决,默认"0"
	delStatus INT NOT NULL, -- 是否删除,"0"为未删除,"1"为已删除,默认"0"
	createTime DATETIME NOT NULL, -- 创建时间
	solveTime DATETIME, -- 解决时间 
);

CREATE TABLE IF NOT EXISTS UserInfo -- 小区住户信息表
(
	id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,  -- 主键
	propertyId INT NOT NULL, -- Property房产表主键ID,一个客户对应一套房产
	userName VARCHAR NOT NULL, -- 登录名
	userPwd VARCHAR NOT NULL, -- 密码
	realName VARCHAR NOT NULL, -- 住户姓名
	phone VARCHAR NOT NULL, -- 联系方式
	idCard VARCHAR NOT NULL, -- 身份证号
	delStatus INT NOT NULL, -- 逻辑删除,"0"为未删除,"1"为已删除
	createTime DATETIME NOT NULL -- 入住时间
);
ALTER TABLE UserInfo ADD UNIQUE (userName);  -- 创建唯一索引
