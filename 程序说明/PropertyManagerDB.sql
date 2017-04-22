/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50711
 Source Host           : localhost
 Source Database       : PropertyManagerDB

 Target Server Version : 50711
 File Encoding         : utf-8

 Date: 10/31/2016 09:30:50 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `AdminInfo`
-- ----------------------------
DROP TABLE IF EXISTS `AdminInfo`;
CREATE TABLE `AdminInfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(16) NOT NULL,
  `userPwd` varchar(16) NOT NULL,
  `userLevel` int(1) NOT NULL DEFAULT '0',
  `userStatus` int(1) NOT NULL DEFAULT '0',
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_usreName` (`userName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `AdminInfo`
-- ----------------------------
BEGIN;
INSERT INTO `AdminInfo` VALUES ('1', 'admin', 'admin', '0', '1', '2016-10-18 09:09:44'), ('41', '222', '222', '1', '1', '2016-10-23 21:46:37'), ('42', '333', '111', '1', '1', '2016-10-23 21:57:09'), ('43', '111', '111', '1', '1', '2016-10-24 08:27:54'), ('44', '444', '444', '1', '1', '2016-10-24 08:48:34'), ('46', '555', '555', '1', '1', '2016-10-24 09:30:55'), ('47', '666', '6666', '1', '1', '2016-10-26 18:48:23');
COMMIT;

-- ----------------------------
--  Table structure for `ChargeInfo`
-- ----------------------------
DROP TABLE IF EXISTS `ChargeInfo`;
CREATE TABLE `ChargeInfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `propertyId` int(11) NOT NULL,
  `chargeType` int(1) NOT NULL,
  `payment` int(8) NOT NULL,
  `remark` varchar(64) NOT NULL,
  `delStatus` int(1) NOT NULL DEFAULT '0',
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `ChargeInfo`
-- ----------------------------
BEGIN;
INSERT INTO `ChargeInfo` VALUES ('1', '12', '15', '3', '2000', '1月份', '1', '2016-10-30 11:34:46'), ('2', '13', '3', '2', '500', '12月份', '1', '2016-10-30 13:16:10'), ('3', '13', '3', '4', '2200', '16年8月', '1', '2016-10-30 15:13:22'), ('4', '12', '15', '2', '1000', '11月', '1', '2016-10-30 15:14:57'), ('5', '12', '15', '1', '300', '1月份', '1', '2016-10-30 15:15:51'), ('6', '13', '3', '1', '1000', '12月份', '0', '2016-10-30 19:16:36'), ('7', '13', '3', '3', '800', '2016年网费', '0', '2016-10-30 22:00:40');
COMMIT;

-- ----------------------------
--  Table structure for `MenuAndRole`
-- ----------------------------
DROP TABLE IF EXISTS `MenuAndRole`;
CREATE TABLE `MenuAndRole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menuId` int(11) NOT NULL,
  `adminId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `adminId` (`adminId`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `MenuAndRole`
-- ----------------------------
BEGIN;
INSERT INTO `MenuAndRole` VALUES ('1', '1', '1'), ('2', '3', '1'), ('3', '5', '1'), ('4', '7', '1'), ('5', '12', '1'), ('6', '14', '1'), ('26', '7', '42'), ('27', '3', '43'), ('28', '5', '43'), ('29', '7', '43'), ('30', '12', '43'), ('31', '14', '43'), ('32', '3', '44'), ('33', '3', '46'), ('34', '5', '46'), ('35', '5', '47'), ('36', '3', '47'), ('37', '7', '47'), ('38', '12', '47'), ('39', '14', '47'), ('68', '5', '41'), ('69', '3', '41'), ('70', '7', '41');
COMMIT;

-- ----------------------------
--  Table structure for `MenuInfo`
-- ----------------------------
DROP TABLE IF EXISTS `MenuInfo`;
CREATE TABLE `MenuInfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) NOT NULL,
  `parentId` int(11) NOT NULL DEFAULT '0',
  `url` varchar(32) DEFAULT NULL,
  `switchStatus` int(1) NOT NULL DEFAULT '0',
  `delStatus` int(1) NOT NULL DEFAULT '0',
  `orderBy` int(8) NOT NULL DEFAULT '0',
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `MenuInfo`
-- ----------------------------
BEGIN;
INSERT INTO `MenuInfo` VALUES ('1', '管理员管理', '0', null, '1', '0', '1', '2016-10-19 11:38:15'), ('2', '管理员列表', '1', 'adminIndex!adminList.action', '0', '0', '2', '2016-10-19 11:38:49'), ('3', '住户管理', '0', null, '1', '0', '5', '2016-10-21 10:55:17'), ('4', '住户列表', '3', 'adminIndex!userList.action', '0', '0', '4', '2016-10-21 10:55:50'), ('5', '房产信息管理', '0', '[Null]', '1', '0', '3', '2016-10-23 13:35:37'), ('6', '房产列表', '5', 'adminIndex!propertyList.action', '0', '0', '6', '2016-10-23 13:36:10'), ('7', '收费管理', '0', null, '1', '0', '7', '2016-10-23 13:36:44'), ('8', '收费列表', '7', 'adminIndex!chargeList.action', '0', '0', '8', '2016-10-23 13:38:06'), ('12', '故障管理', '0', null, '1', '0', '12', '2016-10-23 13:39:38'), ('13', '报修列表', '12', 'adminIndex!repairList.action', '0', '0', '13', '2016-10-23 13:39:52'), ('14', '投诉管理', '0', null, '1', '0', '14', '2016-10-23 13:40:11'), ('15', '投诉列表', '14', 'adminIndex!complainList.action', '0', '0', '15', '2016-10-23 13:40:27');
COMMIT;

-- ----------------------------
--  Table structure for `PropertyInfo`
-- ----------------------------
DROP TABLE IF EXISTS `PropertyInfo`;
CREATE TABLE `PropertyInfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(32) NOT NULL,
  `area` int(8) NOT NULL,
  `layout` varchar(32) NOT NULL,
  `saleStatus` int(1) NOT NULL DEFAULT '0',
  `delStatus` int(1) NOT NULL DEFAULT '0',
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_address` (`address`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `PropertyInfo`
-- ----------------------------
BEGIN;
INSERT INTO `PropertyInfo` VALUES ('1', '12栋1单元401', '92', '两室一厅', '0', '0', '2016-10-27 19:35:30'), ('2', '11栋1单元402', '66', '一室一厅', '0', '0', '2016-10-27 20:22:30'), ('3', '12栋1单元501', '100', '三室一厅', '1', '0', '2016-10-27 20:52:20'), ('15', '11栋3单元1104', '98', '两室一厅', '1', '0', '2016-10-28 20:03:52');
COMMIT;

-- ----------------------------
--  Table structure for `RepairAndComplainInfo`
-- ----------------------------
DROP TABLE IF EXISTS `RepairAndComplainInfo`;
CREATE TABLE `RepairAndComplainInfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `propertyId` int(11) NOT NULL,
  `content` varchar(128) NOT NULL,
  `infoType` int(1) NOT NULL,
  `dealUser` varchar(16) DEFAULT NULL,
  `solveStatus` int(1) NOT NULL DEFAULT '0',
  `delStatus` int(1) NOT NULL DEFAULT '0',
  `createTime` datetime NOT NULL,
  `solveTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `RepairAndComplainInfo`
-- ----------------------------
BEGIN;
INSERT INTO `RepairAndComplainInfo` VALUES ('1', '12', '15', '测试', '1', '临时工', '1', '0', '2016-10-30 17:54:35', '2016-10-30 18:41:00'), ('2', '13', '3', '这物业管理系统太不好使啦', '2', '隔壁老王', '1', '0', '2016-10-30 18:45:11', '2016-10-30 18:45:30'), ('3', '13', '3', '阳台玻璃坏了', '1', '隔壁老王', '1', '0', '2016-10-30 21:44:40', '2016-10-30 22:02:32'), ('4', '13', '3', '楼门坏了', '1', null, '0', '0', '2016-10-30 21:45:25', null), ('5', '13', '3', '三楼怎么没了', '1', null, '0', '0', '2016-10-30 21:47:16', null), ('6', '13', '3', '三楼怎么没了', '2', '临时工', '1', '0', '2016-10-30 21:47:31', '2016-10-30 21:49:06'), ('7', '13', '3', '油烟机坏了', '1', null, '0', '0', '2016-10-30 21:48:12', null), ('8', '13', '3', '', '2', null, '0', '0', '2016-10-30 21:48:44', null), ('9', '13', '3', '马桶坏了', '1', null, '0', '0', '2016-10-30 21:49:20', null);
COMMIT;

-- ----------------------------
--  Table structure for `UserInfo`
-- ----------------------------
DROP TABLE IF EXISTS `UserInfo`;
CREATE TABLE `UserInfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `propertyId` int(11) NOT NULL,
  `userName` varchar(16) NOT NULL,
  `userPwd` varchar(16) NOT NULL,
  `realName` varchar(16) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `idCard` varchar(18) NOT NULL,
  `delStatus` int(1) NOT NULL DEFAULT '0',
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_propertyId` (`propertyId`),
  UNIQUE KEY `un_userName` (`userName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `UserInfo`
-- ----------------------------
BEGIN;
INSERT INTO `UserInfo` VALUES ('12', '15', '111', '111', '李四', '133233233', '132333322230103022', '0', '2016-10-30 11:33:33'), ('13', '3', 'zhangsanfeng', '111', '张三丰', '14738493029', '103020444439304433', '0', '2016-10-30 13:15:23');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
