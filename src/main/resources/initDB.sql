CREATE SCHEMA `taxi` DEFAULT CHARACTER SET utf8;
CREATE TABLE `manufacturer` (
                                `manufacturer_id` bigint NOT NULL AUTO_INCREMENT,
                                `manufacturer_name` varchar(150) NOT NULL,
                                `manufacturer_country` varchar(45) NOT NULL,
                                `isDeleted` tinyint NOT NULL DEFAULT '0',
                                PRIMARY KEY (`manufacturer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;


