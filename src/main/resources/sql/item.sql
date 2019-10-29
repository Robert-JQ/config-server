DROP TABLE IF EXISTS `item`;

CREATE TABLE `item` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `appid` varchar(500) NOT NULL COMMENT 'appid',
  `envid` int(10) unsigned NOT NULL COMMENT '环境id',
  `key` varchar(128) NOT NULL COMMENT '配置项key',
  `value` longtext NOT NULL COMMENT '配置项值',
  `description` varchar(500) COMMENT '描述',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT 0 COMMENT '软删除 1:是 0:否',
  `is_public` tinyint unsigned NOT NULL DEFAULT 0 COMMENT '是否为公共 1:是 0:否',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `appid` (`appid`(191)),
  KEY `envid` (`envid`),
  KEY `item_key` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='配置项表';