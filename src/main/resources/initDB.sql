CREATE SCHEMA `taxi` DEFAULT CHARACTER SET utf8;
CREATE TABLE `car` (
                       `car_id` bigint NOT NULL AUTO_INCREMENT,
                       `manufacturer_id` bigint NOT NULL,
                       `model` varchar(45) NOT NULL,
                       `deleted` tinyint NOT NULL DEFAULT '0',
                       PRIMARY KEY (`car_id`),
                       KEY `car_manufacturer_fk_idx` (`manufacturer_id`),
                       CONSTRAINT `car_manufacturer_fk` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturer` (`manufacturer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8;
CREATE TABLE `manufacturer` (
                                `manufacturer_id` bigint NOT NULL AUTO_INCREMENT,
                                `name` varchar(150) NOT NULL,
                                `country` varchar(45) NOT NULL,
                                `deleted` tinyint NOT NULL DEFAULT '0',
                                PRIMARY KEY (`manufacturer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8;
CREATE TABLE `driver` (
                          `driver_id` bigint NOT NULL AUTO_INCREMENT,
                          `name` varchar(70) NOT NULL,
                          `licence_number` varchar(45) NOT NULL,
                          `deleted` tinyint NOT NULL DEFAULT '0',
                          PRIMARY KEY (`driver_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
CREATE TABLE `car_driver` (
                              `car_id` bigint NOT NULL,
                              `driver_id` bigint NOT NULL,
                              PRIMARY KEY (`car_id`),
                              KEY `driver_id_idx` (`driver_id`),
                              CONSTRAINT `car_id` FOREIGN KEY (`car_id`) REFERENCES `car` (`car_id`),
                              CONSTRAINT `driver_id` FOREIGN KEY (`driver_id`) REFERENCES `driver` (`driver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


