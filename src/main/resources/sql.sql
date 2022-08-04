CREATE TABLE `user` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `age` tinyint(4) NOT NULL COMMENT '年龄',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `test` (
                        `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
                        `unit` bigint(20) DEFAULT NULL COMMENT '单位',
                        `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
                        `create_date` datetime DEFAULT NULL COMMENT '插入时间',
                        `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `index` (`unit`,`user_id`) USING BTREE COMMENT '单位用户唯一索引',
                        KEY `unit` (`create_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;