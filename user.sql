CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phoneNum` varchar(20) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` (`id`, `username`, `password`, `email`, `phoneNum`, `status`)
VALUES
	(1,'xiaom','a9cd8b1a681a5dd377ace29f6c8105b2','xiaom@qq.com','020-8888888',1),
	(2,'Nesteast','a9cd8b1a681a5dd377ace29f6c8105b2','ee@163.com','020-8888888',0),
	(4,'user','ee11cbb19052e40b07aac0ca060c23ee','longporo@163.com','020-66666666',1);