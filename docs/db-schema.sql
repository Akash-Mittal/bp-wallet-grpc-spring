CREATE DATABASE  IF NOT EXISTS `bp_wallet` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `bp_wallet`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: bp_wallet
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bp_currency`
--

DROP TABLE IF EXISTS `bp_currency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bp_currency` (
  `currency_id` bigint(10) NOT NULL,
  `currency_name` varchar(3) NOT NULL,
  PRIMARY KEY (`currency_id`),
  UNIQUE KEY `currency_name_UNIQUE` (`currency_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bp_currency`
--

LOCK TABLES `bp_currency` WRITE;
/*!40000 ALTER TABLE `bp_currency` DISABLE KEYS */;
INSERT INTO `bp_currency` VALUES (2,'EUR'),(3,'GBP'),(1,'USD');
/*!40000 ALTER TABLE `bp_currency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bp_user`
--

DROP TABLE IF EXISTS `bp_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bp_user` (
  `user_id` bigint(100) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bp_user`
--

LOCK TABLES `bp_user` WRITE;
/*!40000 ALTER TABLE `bp_user` DISABLE KEYS */;
INSERT INTO `bp_user` VALUES (1),(2);
/*!40000 ALTER TABLE `bp_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bp_user_currency`
--

DROP TABLE IF EXISTS `bp_user_currency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bp_user_currency` (
  `user_currency_id` bigint(100) NOT NULL,
  `currency_id` bigint(10) NOT NULL,
  `user_id` bigint(100) NOT NULL,
  PRIMARY KEY (`user_currency_id`,`currency_id`,`user_id`),
  KEY `bp_uc_user_idx` (`user_id`),
  KEY `bp_uc_currency_idx` (`currency_id`),
  CONSTRAINT `bp_uc_currency` FOREIGN KEY (`currency_id`) REFERENCES `bp_currency` (`currency_id`) ON UPDATE CASCADE,
  CONSTRAINT `bp_uc_user` FOREIGN KEY (`user_id`) REFERENCES `bp_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bp_user_currency`
--

LOCK TABLES `bp_user_currency` WRITE;
/*!40000 ALTER TABLE `bp_user_currency` DISABLE KEYS */;
INSERT INTO `bp_user_currency` VALUES (1,1,1),(2,2,1);
/*!40000 ALTER TABLE `bp_user_currency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bp_user_wallet`
--

DROP TABLE IF EXISTS `bp_user_wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bp_user_wallet` (
  `user_balance` decimal(10,0) NOT NULL,
  `user_currency_id` bigint(100) DEFAULT NULL,
  KEY `fk_idx` (`user_currency_id`),
  CONSTRAINT `fk_user_currency` FOREIGN KEY (`user_currency_id`) REFERENCES `bp_user_currency` (`user_currency_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bp_user_wallet`
--

LOCK TABLES `bp_user_wallet` WRITE;
/*!40000 ALTER TABLE `bp_user_wallet` DISABLE KEYS */;
INSERT INTO `bp_user_wallet` VALUES (100,1),(100,2);
/*!40000 ALTER TABLE `bp_user_wallet` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-28 10:10:47
