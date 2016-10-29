-- MySQL dump 10.13  Distrib 5.7.11, for osx10.9 (x86_64)
--
-- Host: servicedesigntoolkit.c0yhynxdv06j.ap-southeast-1.rds.amazonaws.com    Database: service_design_toolkit
-- ------------------------------------------------------
-- Server version	5.6.27-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `service_design_toolkit`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `service_design_toolkit` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `service_design_toolkit`;

--
-- Table structure for table `channel`
--

DROP TABLE IF EXISTS `channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `field_researcher`
--

DROP TABLE IF EXISTS `field_researcher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `field_researcher` (
  `id` int(11) NOT NULL,
  `current_latitude` varchar(20) NOT NULL,
  `current_longitude` varchar(20) NOT NULL,
  `last_active` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `field_researcher_ibfk_1` FOREIGN KEY (`id`) REFERENCES `sdt_user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `journey`
--

DROP TABLE IF EXISTS `journey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journey` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `journey_name` varchar(100) DEFAULT NULL,
  `no_of_field_researcher` int(11) NOT NULL,
  `is_active` char(1) NOT NULL DEFAULT 'Y',
  `start_date` date NOT NULL,
  `can_be_registered` char(1) NOT NULL,
  `end_date` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `journey_name` (`journey_name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `journey_field_researcher`
--

DROP TABLE IF EXISTS `journey_field_researcher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journey_field_researcher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `journey_id` int(11) NOT NULL,
  `field_researcher_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `journey_id` (`journey_id`),
  KEY `field_researcher_id` (`field_researcher_id`),
  CONSTRAINT `journey_field_researcher_ibfk_1` FOREIGN KEY (`journey_id`) REFERENCES `journey` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `journey_field_researcher_ibfk_2` FOREIGN KEY (`field_researcher_id`) REFERENCES `field_researcher` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sdt_user`
--

DROP TABLE IF EXISTS `sdt_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sdt_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `user_role_id` int(11) NOT NULL,
  `is_active` char(1) NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`id`),
  KEY `user_role_id` (`user_role_id`),
  CONSTRAINT `sdt_user_ibfk_1` FOREIGN KEY (`user_role_id`) REFERENCES `user_role` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `touch_point`
--

DROP TABLE IF EXISTS `touch_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `touch_point` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `journey_id` int(11) NOT NULL,
  `touch_point_desc` varchar(500) DEFAULT NULL,
  `latitude` varchar(20) DEFAULT NULL,
  `longitude` varchar(20) DEFAULT NULL,
  `radius` varchar(10) NOT NULL DEFAULT '2',
  `action` varchar(200) DEFAULT NULL,
  `channel_description` varchar(200) DEFAULT NULL,
  `channel_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `journey_id` (`journey_id`),
  KEY `touch_point_ibfk_2` (`channel_id`),
  CONSTRAINT `touch_point_ibfk_1` FOREIGN KEY (`journey_id`) REFERENCES `journey` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `touch_point_ibfk_2` FOREIGN KEY (`channel_id`) REFERENCES `channel` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `touchpoint_field_researcher`
--

DROP TABLE IF EXISTS `touchpoint_field_researcher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `touchpoint_field_researcher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `field_researcher_id` int(11) NOT NULL,
  `touchpoint_id` int(11) NOT NULL,
  `comments` varchar(200) DEFAULT NULL,
  `reaction` varchar(200) DEFAULT NULL,
  `rating_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `field_researcher_id` (`field_researcher_id`),
  KEY `touchpoint_id` (`touchpoint_id`),
  KEY `touchpoint_field_researcher_ibfk_3_idx` (`rating_id`),
  CONSTRAINT `touchpoint_field_researcher_ibfk_1` FOREIGN KEY (`field_researcher_id`) REFERENCES `field_researcher` (`id`),
  CONSTRAINT `touchpoint_field_researcher_ibfk_2` FOREIGN KEY (`touchpoint_id`) REFERENCES `touch_point` (`id`),
  CONSTRAINT `touchpoint_field_researcher_ibfk_3` FOREIGN KEY (`rating_id`) REFERENCES `rating` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-10-28 15:44:09
