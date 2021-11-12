/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.5.5-10.1.16-MariaDB : Database - libreriaspring
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`libreriaspring` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci */;

USE `libreriaspring`;

/*Table structure for table `autor` */

DROP TABLE IF EXISTS `autor`;

CREATE TABLE `autor` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `alta` bit(1) NOT NULL,
  `creacion` datetime DEFAULT NULL,
  `eliminacion` datetime DEFAULT NULL,
  `modificacion` datetime DEFAULT NULL,
  `nombre` varchar(255) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*Table structure for table `editorial` */

DROP TABLE IF EXISTS `editorial`;

CREATE TABLE `editorial` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `alta` bit(1) NOT NULL,
  `creacion` datetime DEFAULT NULL,
  `eliminacion` datetime DEFAULT NULL,
  `modificacion` datetime DEFAULT NULL,
  `nombre` varchar(255) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*Table structure for table `libro` */

DROP TABLE IF EXISTS `libro`;

CREATE TABLE `libro` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `alta` bit(1) DEFAULT NULL,
  `anio` int(11) DEFAULT NULL,
  `creacion` datetime DEFAULT NULL,
  `ejemplares` int(11) DEFAULT NULL,
  `ejemplares_prestados` int(11) DEFAULT NULL,
  `ejemplares_restantes` int(11) DEFAULT NULL,
  `eliminacion` datetime DEFAULT NULL,
  `isbn` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL,
  `modificacion` datetime DEFAULT NULL,
  `titulo` varchar(255) COLLATE utf8_spanish_ci DEFAULT NULL,
  `autor_id` bigint(20) unsigned DEFAULT NULL,
  `editorial_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe1ss87ymon6qj17bhr6jfh0c4` (`autor_id`),
  KEY `FK79q7g2604hcmfdxw6ek3jt4el` (`editorial_id`),
  CONSTRAINT `FK79q7g2604hcmfdxw6ek3jt4el` FOREIGN KEY (`editorial_id`) REFERENCES `editorial` (`id`),
  CONSTRAINT `FKe1ss87ymon6qj17bhr6jfh0c4` FOREIGN KEY (`autor_id`) REFERENCES `autor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*Table structure for table `usuario` */

DROP TABLE IF EXISTS `usuario`;

CREATE TABLE `usuario` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `alta` datetime DEFAULT NULL,
  `baja` datetime DEFAULT NULL,
  `login` varchar(255) COLLATE utf8_spanish_ci NOT NULL,
  `nombre` varchar(255) COLLATE utf8_spanish_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
