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
-- Dumping data for table `channel`
--

LOCK TABLES `channel` WRITE;
/*!40000 ALTER TABLE `channel` DISABLE KEYS */;
INSERT INTO `channel` VALUES (1,'person to person'),(2,'website'),(3,'kiosk');
/*!40000 ALTER TABLE `channel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `field_researcher`
--

LOCK TABLES `field_researcher` WRITE;
/*!40000 ALTER TABLE `field_researcher` DISABLE KEYS */;
INSERT INTO `field_researcher` VALUES (1,'0','0','2016-10-28 08:20:01'),(2,'0','0','2016-10-21 05:17:37'),(3,'0','0','2016-10-19 07:40:41'),(4,'1.3056065','103.7730211','2016-09-21 14:40:37'),(5,'1.3488132','103.7491629','2016-09-21 14:43:22'),(6,'1.2922409','103.7764204','2016-09-22 02:24:33'),(7,'1.2920798','103.776712','2016-09-22 02:53:39'),(8,'1.2899023','103.7761687','2016-09-22 04:41:36'),(9,'20.02','40.30','2016-10-04 08:14:29'),(10,'20.02','40.30','2016-10-04 11:34:24'),(11,'20.02','40.30','2016-10-04 09:44:08'),(12,'20.02','40.30','2016-10-04 10:04:14'),(13,'20.02','40.30','2016-10-04 11:44:32'),(14,'6','6','2016-10-14 12:46:28'),(15,'20.02','40.30','2016-10-24 13:47:16'),(16,'6','6','2016-10-15 14:53:55'),(17,'0','0','2016-10-18 14:42:46'),(18,'0','0','2016-10-18 14:50:52'),(19,'0','0','2016-10-18 14:53:30'),(20,'0','0','2016-10-19 06:24:26'),(21,'6','6','2016-10-21 23:57:22'),(22,'6','6','2016-10-21 17:16:41'),(23,'0','0','2016-10-25 14:25:29'),(24,'0','0','2016-10-26 15:32:56'),(25,'0','0','2016-10-28 08:01:44'),(26,'0','0','2016-10-26 15:58:48'),(27,'0','0','2016-10-27 15:14:47'),(28,'0','0','2016-10-27 15:19:20'),(29,'0','0','2016-10-27 15:32:13'),(30,'0','0','2016-10-27 15:37:54');
/*!40000 ALTER TABLE `field_researcher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `journey`
--

LOCK TABLES `journey` WRITE;
/*!40000 ALTER TABLE `journey` DISABLE KEYS */;
INSERT INTO `journey` VALUES (1,'Hostels',20,'Y','2016-09-20','N','2016-10-20'),(2,'Departments',20,'Y','2016-09-20','Y','2016-10-20'),(3,'Library',20,'Y','2016-09-20','Y','2016-10-20'),(6,'abf fd',21,'Y','2016-10-19','Y','2016-10-19'),(7,'Manish',21,'Y','2016-10-20','Y','2016-10-20'),(8,'Manish1',21,'Y','2016-10-25','Y','2016-10-25'),(9,'Sam',21,'Y','2016-10-25','Y','2016-10-25'),(10,'Gunjan1',2,'Y','2016-10-25','Y','2016-10-25'),(11,'Gunjan2',21,'Y','2016-10-25','Y','2016-10-25');
/*!40000 ALTER TABLE `journey` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `journey_field_researcher`
--

LOCK TABLES `journey_field_researcher` WRITE;
/*!40000 ALTER TABLE `journey_field_researcher` DISABLE KEYS */;
INSERT INTO `journey_field_researcher` VALUES (50,2,1),(51,3,1),(52,9,1),(53,11,1);
/*!40000 ALTER TABLE `journey_field_researcher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` VALUES (1,'1'),(2,'2'),(3,'3'),(4,'4'),(5,'5');
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sdt_user`
--

LOCK TABLES `sdt_user` WRITE;
/*!40000 ALTER TABLE `sdt_user` DISABLE KEYS */;
INSERT INTO `sdt_user` VALUES (1,'Gunjan',1,'Y'),(2,'Sam',1,'Y'),(3,'Prasan',1,'Y'),(4,'Aditya',1,'Y'),(5,'shubhankar',1,'Y'),(6,'Mohan',1,'Y'),(7,'zm',1,'Y'),(8,'Stuart',1,'Y'),(9,'aden',1,'Y'),(10,'Bhomik',1,'Y'),(11,'bhomikk',1,'Y'),(12,'alo',1,'Y'),(13,'adam',1,'Y'),(14,'fr05',1,'Y'),(15,'longnguyen',1,'Y'),(16,'langnguyen',1,'Y'),(17,'Pathak',1,'Y'),(18,'Zoozoo',1,'Y'),(19,'sanjay',1,'Y'),(20,'kesha',1,'Y'),(21,'priya',1,'Y'),(22,'anhnguyen',1,'Y'),(23,'long',1,'Y'),(24,'parth',1,'Y'),(25,'qwerty',1,'Y'),(26,'dhc',1,'Y'),(27,'vshs',1,'Y'),(28,'gbg',1,'Y'),(29,'hdk',1,'Y'),(30,'hdhd',1,'Y');
/*!40000 ALTER TABLE `sdt_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `touch_point`
--

LOCK TABLES `touch_point` WRITE;
/*!40000 ALTER TABLE `touch_point` DISABLE KEYS */;
INSERT INTO `touch_point` VALUES (1,1,'Utown','1.3048824519497078','103.77392649650574','1',NULL,NULL,1),(2,1,'Raffles Hall','1.2998713685250385','103.77334713935852','1',NULL,NULL,1),(3,1,'Eusof Hall','1.2939076634901174','103.77038061618805','1',NULL,NULL,1),(4,1,'PGPR','1.290636200766921','103.78049790859222','1',NULL,NULL,1),(5,2,'Research Tower','1.3040029151681025','103.77394795417786','1',NULL,NULL,1),(6,2,'Engineering','1.3000986272037942','103.77104043960571','1',NULL,NULL,1),(7,2,'Science','1.2966341028820958','103.78118991851807','1',NULL,NULL,1),(8,2,'Arts','1.2949930107581433','103.77104043960571','1',NULL,NULL,1),(9,2,'Computing','1.29492865418261','103.77395868301392','1',NULL,NULL,1),(10,2,'Business','1.293652248430222','103.77463459968567','1',NULL,NULL,1),(11,2,'ISS','1.291989702159707','103.77687692642212','1',NULL,NULL,1),(12,3,'Central Library','1.2970738724862483','103.77349734306335','1',NULL,NULL,1),(13,3,'Arts Library','1.2948106671232098','103.77122282981873','1',NULL,NULL,1),(14,3,'Memorial Library','1.2929443256352346','103.77452731132507','1',NULL,NULL,1),(15,8,'p2',NULL,NULL,'2','a2','c2',1),(16,8,'p2',NULL,NULL,'2','a2','c2',1),(17,9,'p3',NULL,NULL,'1','a3','c3',2),(18,9,'p3',NULL,NULL,'1','a3','c3',2),(19,9,'p3',NULL,NULL,'1','a3','c3',2),(20,10,'p3',NULL,NULL,'3','a3','c3',3),(21,10,'p3',NULL,NULL,'3','a3','c3',3),(22,10,'p3',NULL,NULL,'3','a3','c3',3),(23,11,'p1',NULL,NULL,'1','a1','cq',2),(24,11,'p2',NULL,NULL,'2','a2','c2',2);
/*!40000 ALTER TABLE `touch_point` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `touchpoint_field_researcher`
--

LOCK TABLES `touchpoint_field_researcher` WRITE;
/*!40000 ALTER TABLE `touchpoint_field_researcher` DISABLE KEYS */;
INSERT INTO `touchpoint_field_researcher` VALUES (2,15,1,'I love it','I work on it',1),(3,16,1,'I love it','I work on it',1),(4,1,1,'I love it','I work on it',1),(5,1,1,NULL,NULL,1),(6,16,1,'I love it','I work on it',1),(7,1,1,'I love it','I work on it',1),(8,1,1,'I love it','I work on it',1),(9,1,1,'I love it','I work on it',1),(10,1,1,'I love it','I work on it',1),(11,1,2,'I love it','I work on it',1),(12,1,2,'I love it','I work on it',1),(13,16,1,'I love it','I work on it',1),(14,1,2,'I love it','I work on it',4),(15,2,1,'success','trial',5),(16,1,2,'I love it','I work on it',4),(17,1,2,'I really love it','I work on it',4),(18,2,2,'I really love it','Sam work on it',4),(19,2,1,'Arts','its library',5),(20,23,1,'I made comment on emulator','I made reaction on emulator',2),(21,23,1,'','',3),(22,1,1,'',' ',2);
/*!40000 ALTER TABLE `touchpoint_field_researcher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,'Field Researcher');
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

-- Dump completed on 2016-10-28 15:48:58
