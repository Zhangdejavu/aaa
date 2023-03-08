/*
 Navicat Premium Data Transfer

 Source Server         : ZIKOO
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : gp

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 08/03/2023 20:38:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for server
-- ----------------------------
DROP TABLE IF EXISTS `server`;
CREATE TABLE `server`  (
  `id` int(0) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '服务器名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网址',
  `enable` tinyint(1) NOT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of server
-- ----------------------------
INSERT INTO `server` VALUES (1, '本地', '127.0.0.1', 1);
INSERT INTO `server` VALUES (2, '阿里云', 'https://www.aliyun.com', 0);
INSERT INTO `server` VALUES (3, '腾讯云', 'https://cloud.tencent.com', 0);

-- ----------------------------
-- Table structure for ticket
-- ----------------------------
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket`  (
  `id` int(0) NOT NULL,
  `trainCode` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '火车编号',
  `trainNo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '车次',
  `fromStation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '出发地',
  `toStation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '目的地',
  `startStation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发车地',
  `endStation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '终点地',
  `fromTime` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '出发时间',
  `toTime` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '到达时间',
  `costTime` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '耗时',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ticket
-- ----------------------------

-- ----------------------------
-- Table structure for url_info
-- ----------------------------
DROP TABLE IF EXISTS `url_info`;
CREATE TABLE `url_info`  (
  `id` int(0) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'API名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'API路径',
  `method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方式',
  `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `headers` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求头',
  `body` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求体',
  `resource` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'API的来源',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '注释说明',
  `enable` tinyint(1) NOT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of url_info
-- ----------------------------
INSERT INTO `url_info` VALUES (1, '查询余票', 'https://kyfw.12306.cn/otn/leftTicket/query', 'GET', 'leftTicketDTO.train_date=value|leftTicketDTO.from_station=value|leftTicketDTO.to_station=value|purpose_codes=value', 'Cookie=_jc_save_+key+=+value|User-Agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36', NULL, '12306', NULL, 1);
INSERT INTO `url_info` VALUES (2, '查询车站代码', 'https://kyfw.12306.cn/otn/resources/js/framework/station_name.js', 'GET', NULL, NULL, NULL, '12306', NULL, 1);
INSERT INTO `url_info` VALUES (3, '登录', 'https://kyfw.12306.cn/passport/web/login', 'GET', NULL, NULL, NULL, '12306', NULL, 0);
INSERT INTO `url_info` VALUES (4, '查询车次信息', 'https://kyfw.12306.cn/otn/czxx/queryByTrainNo', 'GET', NULL, NULL, NULL, '12306', NULL, 0);
INSERT INTO `url_info` VALUES (5, '提交订单', 'https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest', 'GET', NULL, NULL, NULL, '12306', NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
