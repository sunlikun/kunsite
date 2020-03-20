/*
 Navicat Premium Data Transfer

 Source Server         : hasee
 Source Server Type    : MySQL
 Source Server Version : 50642
 Source Host           : 39.98.164.226:3306
 Source Schema         : kun_cms

 Target Server Type    : MySQL
 Target Server Version : 50642
 File Encoding         : 65001

 Date: 11/08/2019 15:07:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for play_num_record
-- ----------------------------
DROP TABLE IF EXISTS `play_num_record`;
CREATE TABLE `play_num_record`  (
  `id` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `play_num_cureent` int(10) NULL DEFAULT NULL COMMENT '当前点播数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of play_num_record
-- ----------------------------
INSERT INTO `play_num_record` VALUES ('1', 40);

SET FOREIGN_KEY_CHECKS = 1;
