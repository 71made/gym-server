CREATE DATABASE IF NOT EXISTS `gym`;

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`(
    `id` INT(11) PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    `account` VARCHAR(32) NOT NULL UNIQUE COMMENT '账号',
    `password` VARCHAR(32) NOT NULL COMMENT '密码',
    `status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '状态: 0-正常 1-禁用',
    `create_time` DATETIME DEFAULT NOW() COMMENT '创建时间',
    INDEX (`account`) COMMENT 'idx_account',
    INDEX (`create_time`) COMMENT 'idx_create_time'
);

DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`(
    `id` INT(11) PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    `card_number` VARCHAR(32) NOT NULL UNIQUE COMMENT '会员卡号',
    `password` VARCHAR(32) NOT NULL COMMENT '密码',
    `name` NVARCHAR(32) NOT NULL COMMENT '会员姓名',
    `status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '状态: 0-正常 1-异常标记 2-逾期禁用',
    `phone` VARCHAR(11) NOT NULL UNIQUE COMMENT '联系方式',
    `type` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '会员卡类型: 0-普通卡 1-银卡 2-金卡',
    `amount` DECIMAL(11, 2) NOT NULL DEFAULT 0.00 COMMENT '会员卡余额',
    `create_time` DATETIME DEFAULT NOW() COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NOW() COMMENT '更新时间',
    INDEX (`card_number`) COMMENT 'idx_account',
    INDEX (`create_time`) COMMENT 'idx_create_time'
);

DROP TABLE IF EXISTS `member_trade`;
CREATE TABLE `member_trade`(
    `id` INT(11) PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    `trade_code` VARCHAR(64) NOT NULL UNIQUE COMMENT '交易流水号',
    `type` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '类型: 0-收入 1-支出',
    `status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '状态: 0-正常交易 1-撤销交易 2-异常标记',
    `last_amount` DECIMAL(11, 2) NOT NULL DEFAULT 0.00 COMMENT '上次金额',
    `amount` DECIMAL(11, 2) NOT NULL DEFAULT 0.00 COMMENT '交易金额',
    `notes` VARCHAR(255) COMMENT '备注',
    `create_time` DATETIME DEFAULT NOW() COMMENT '创建时间',
    `update_time` DATETIME DEFAULT NOW() COMMENT '更新时间',
    INDEX (`trade_code`) COMMENT 'idx_trade_code',
    INDEX (`create_time`) COMMENT 'idx_create_time'
);


DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff`(
    `id` INT(11) PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
#     `code` VARCHAR(8) NOT NULL UNIQUE COMMENT '员工工号',
    `name` VARCHAR(16) NOT NULL COMMENT '员工姓名',
    `sex` TINYINT(1) NOT NULL COMMENT '性别',
    `id_card` VARCHAR(18) NOT NULL COMMENT '身份证号',
    `status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '状态: 0-在职 1-离职 2-删除',
    `position` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '职位: 0-管理员 1-教练 2-前台 3-保洁',
    `birthday_time` DATETIME COMMENT '出生日期',
    `entry_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '入职时间',
    `create_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '更新时间',
#     INDEX (`code`) COMMENT 'idx_code',
    INDEX (`create_time`) COMMENT 'idx_create_time',
    INDEX (`update_time`) COMMENT 'idx_update_time'
);

DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`
(
    `id` INT(11) PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    `staff_id` INT(11) NOT NULL COMMENT '教练 id',
    `name` VARCHAR(32) NOT NULL COMMENT '课程名称',
    `status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '状态: 0-进行中 1-停课/删除',
    `member_count` INT NOT NULL DEFAULT 0 COMMENT '报名人数',
    `period` DECIMAL(11, 2) NOT NULL DEFAULT 0.0 COMMENT '课程学时',
    `amount` DECIMAL(11, 2) NOT NULL DEFAULT 0.00 COMMENT '报名金额',
    `start_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '开始时间',
    `end_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '结束时间',
    `create_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '更新时间',
    FOREIGN KEY (`staff_id`) REFERENCES `staff`(`id`),
    INDEX (`staff_id`) COMMENT 'idx_staff',
    INDEX (`create_time`) COMMENT 'idx_create_time'
);

DROP TABLE IF EXISTS `member_course`;
CREATE TABLE `member_course`
(
    `id` INT(11) PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    `course_id` INT(11) NOT NULL COMMENT '课程 id',
    `member_id` INT(11) NOT NULL COMMENT '会员 id',
    `staff_id` INT(11) NOT NULL COMMENT '教练 id',
    `status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '状态: 0-报名 1-取消报名',
    `create_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '更新时间',
    FOREIGN KEY (`course_id`) REFERENCES `course`(`id`),
    FOREIGN KEY (`member_id`) REFERENCES `member`(`id`),
    FOREIGN KEY (`staff_id`) REFERENCES `staff`(`id`),
    INDEX (`member_id`) COMMENT 'idx_member',
    INDEX (`staff_id`) COMMENT 'idx_staff',
    INDEX (`course_id`) COMMENT 'idx_course',
    INDEX (`create_time`) COMMENT 'idx_create_time'
);

DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment`(
    `id` INT(11) PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    `name` VARCHAR(32) NOT NULL COMMENT '器材名称',
    `status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '状态: 0-使用中 1-已移除 2-丢失',
    `location` VARCHAR(64) COMMENT '地点位置',
    `create_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '更新时间',
    INDEX (`name`) COMMENT 'idx_name',
    INDEX (`create_time`) COMMENT 'idx_create_time'
);

DROP TABLE IF EXISTS `lost_item`;
CREATE TABLE `lost_item`(
    `id` INT(11) PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    `name` VARCHAR(64) NOT NULL COMMENT '遗失物品名称',
    `status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '状态: 0-认领中 1-已认领 2-删除',
    `notes` VARCHAR(256) COMMENT '备注',
    `location` VARCHAR(64) COMMENT '地点位置',
    `phone` VARCHAR(11) COMMENT '捡拾人电话',
    `pickup_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '捡拾时间',
    `create_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT NOW() COMMENT '更新时间',
    INDEX (`name`) COMMENT 'idx_name',
    INDEX (`create_time`) COMMENT 'idx_create_time'
);



