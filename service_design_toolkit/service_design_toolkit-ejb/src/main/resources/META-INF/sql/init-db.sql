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

CREATE USER 'iss_sdt'@'localhost' IDENTIFIED BY 'iss_sdt';

GRANT ALL PRIVILEGES ON * . * TO 'iss_sdt'@'localhost';

FLUSH PRIVILEGES;

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
-- Dumping data for table `channel`
--

LOCK TABLES `channel` WRITE;
/*!40000 ALTER TABLE `channel` DISABLE KEYS */;
INSERT INTO `channel` VALUES (1,'Face To Face'),(2,'Website'),(3,'Kiosk');
/*!40000 ALTER TABLE `channel` ENABLE KEYS */;
UNLOCK TABLES;

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
  `description` varchar(500) DEFAULT NULL,
  `is_sequence` char(1) DEFAULT NULL,
  `is_geo` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `journey_name` (`journey_name`)
) ENGINE=InnoDB AUTO_INCREMENT=363 DEFAULT CHARSET=latin1;
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
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `journey_id` (`journey_id`),
  KEY `field_researcher_id` (`field_researcher_id`),
  CONSTRAINT `journey_field_researcher_ibfk_1` FOREIGN KEY (`journey_id`) REFERENCES `journey` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `journey_field_researcher_ibfk_2` FOREIGN KEY (`field_researcher_id`) REFERENCES `field_researcher` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=428 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `master_data`
--

DROP TABLE IF EXISTS `master_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_data` (
  `id` varchar(50) NOT NULL,
  `data_value` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `master_data`
--

LOCK TABLES `master_data` WRITE;
/*!40000 ALTER TABLE `master_data` DISABLE KEYS */;
INSERT INTO `master_data` VALUES ('TOUCH_POINT_DURATION_UNIT_DAY','Day'),('TOUCH_POINT_DURATION_UNIT_HOUR','Hour'),('TOUCH_POINT_DURATION_UNIT_MINUTE','Minute');
/*!40000 ALTER TABLE `master_data` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` VALUES (1,'1'),(2,'2'),(3,'3'),(4,'4'),(5,'5');
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sdt_user`
--

DROP TABLE IF EXISTS `sdt_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sdt_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `user_role_id` int(11) NOT NULL,
  `is_active` char(1) NOT NULL DEFAULT 'Y',
  `password` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_role_id` (`user_role_id`),
  CONSTRAINT `sdt_user_ibfk_1` FOREIGN KEY (`user_role_id`) REFERENCES `user_role` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=321 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sdt_user`
--

LOCK TABLES `sdt_user` WRITE;
/*!40000 ALTER TABLE `sdt_user` DISABLE KEYS */;
INSERT INTO `sdt_user` VALUES (1,'USER_RESEARCH_OWNER',2,'Y','abcd1234');
UNLOCK TABLES;

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
  `duration_unit` varchar(50) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `sequence_no` int(11) DEFAULT NULL,
  `sub_seq_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `journey_id` (`journey_id`),
  KEY `touch_point_ibfk_2` (`channel_id`),
  KEY `duration_unit` (`duration_unit`),
  CONSTRAINT `touch_point_ibfk_1` FOREIGN KEY (`journey_id`) REFERENCES `journey` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `touch_point_ibfk_2` FOREIGN KEY (`channel_id`) REFERENCES `channel` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `touch_point_ibfk_3` FOREIGN KEY (`duration_unit`) REFERENCES `master_data` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=522 DEFAULT CHARSET=latin1;
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
  `rating_id` int(11) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `duration_unit` varchar(50) DEFAULT NULL,
  `photo_location` varchar(500) DEFAULT NULL,
  `action_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `field_researcher_id` (`field_researcher_id`),
  KEY `touchpoint_id` (`touchpoint_id`),
  KEY `touchpoint_field_researcher_ibfk_3_idx` (`rating_id`),
  KEY `duration_unit` (`duration_unit`),
  CONSTRAINT `touchpoint_field_researcher_ibfk_1` FOREIGN KEY (`field_researcher_id`) REFERENCES `field_researcher` (`id`),
  CONSTRAINT `touchpoint_field_researcher_ibfk_2` FOREIGN KEY (`touchpoint_id`) REFERENCES `touch_point` (`id`),
  CONSTRAINT `touchpoint_field_researcher_ibfk_3` FOREIGN KEY (`rating_id`) REFERENCES `rating` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `touchpoint_field_researcher_ibfk_4` FOREIGN KEY (`duration_unit`) REFERENCES `master_data` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1053 DEFAULT CHARSET=latin1;
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,'Field Researcher'),(2,'Research Owner');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-01 23:52:53
