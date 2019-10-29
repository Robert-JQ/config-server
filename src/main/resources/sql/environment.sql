DROP TABLE IF EXISTS `environment`;

CREATE TABLE `environment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(500) NOT NULL COMMENT '环境名',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT 0 COMMENT '软删除 1:是 0:否',
  `description` varchar(500) COMMENT '描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='应用环境表';