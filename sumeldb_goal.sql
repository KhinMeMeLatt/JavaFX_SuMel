-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sumeldb
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `goal`
--

DROP TABLE IF EXISTS `goal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goal` (
  `goalId` int NOT NULL AUTO_INCREMENT,
  `goalName` varchar(120) DEFAULT NULL,
  `goalImgName` varchar(120) DEFAULT NULL,
  `goalAmount` double DEFAULT NULL,
  `startDate` datetime(4) DEFAULT NULL,
  `endDate` datetime(4) DEFAULT NULL,
  `saveType` varchar(120) DEFAULT NULL,
  `amountToSave` double DEFAULT NULL,
  `isBreak` tinyint DEFAULT NULL,
  `userId` int DEFAULT NULL,
  PRIMARY KEY (`goalId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goal`
--

LOCK TABLES `goal` WRITE;
/*!40000 ALTER TABLE `goal` DISABLE KEYS */;
INSERT INTO `goal` VALUES (1,'BuyingPhone','Phone.png',300000,'2020-10-01 00:00:00.0000','2020-12-31 00:00:00.0000','daily',5000,0,11),(2,'shopping','clothes.png',45000,'2021-01-01 00:00:00.0000','2020-01-31 00:00:00.0000','daily',500,1,11),(3,'BuyingLaptop','laptop.png',1000000,'2020-01-01 00:00:00.0000','2021-08-31 00:00:00.0000','monthly',50000,1,11),(4,'Trip','taunggyi.jpg',150000,'2020-12-31 00:00:00.0000','2021-01-15 00:00:00.0000','daily',3000,0,11),(5,'buyingCosmetic','makeup.jpg',50000,'2021-05-01 00:00:00.0000','2021-06-15 00:00:00.0000','daily',500,1,4),(6,'JLPTexamfees','Japan.png',50000,'2018-03-01 00:00:00.0000','2018-04-15 00:00:00.0000','daily',500,1,11),(7,'Japanese Class','Japan.png',50000,'2019-05-01 00:00:00.0000','2019-06-15 00:00:00.0000','daily',500,1,11),(8,'JavaSE Class','Java.png',150000,'2019-09-01 00:00:00.0000','2019-10-15 00:00:00.0000','daily',3000,1,11),(9,'CCNA Class','CCNA.png',100000,'2020-01-31 00:00:00.0000','2020-02-15 00:00:00.0000','daily',2000,1,11),(10,'JavaEE Class','Java.png',100000,'2021-02-01 00:00:00.0000','2021-03-18 00:00:00.0000','daily',2000,1,11),(12,'Donation','JustSave.png',0,'2021-03-10 00:00:00.0000','2021-03-10 00:00:00.0000',NULL,0,0,11),(13,'General','JustSave.png',0,'2021-03-11 00:00:00.0000','2021-03-11 00:00:00.0000',NULL,0,0,11),(14,'Car','goal.png',50000,'2021-03-11 00:00:00.0000','2021-03-11 00:00:00.0000','Monthly',12500,0,11);
/*!40000 ALTER TABLE `goal` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-12  0:37:32
