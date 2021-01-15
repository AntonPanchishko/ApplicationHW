CREATE SCHEMA `taxi` DEFAULT CHARACTER SET utf8;
CREATE TABLE `manufacturer` (
                                `manufacturer_id` bigint NOT NULL AUTO_INCREMENT,
                                `name` varchar(150) NOT NULL,
                                `country` varchar(45) NOT NULL,
                                `deleted` tinyint NOT NULL DEFAULT '0',
                                PRIMARY KEY (`manufacturer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;




