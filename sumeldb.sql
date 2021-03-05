-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: sumeldb
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `expense`
--

DROP TABLE IF EXISTS `expense`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expense` (
  `expenseId` int NOT NULL AUTO_INCREMENT,
  `expenseName` varchar(200) DEFAULT NULL,
  `expenseCategory` varchar(200) DEFAULT NULL,
  `expenseAmount` int DEFAULT NULL,
  `spendAt` date DEFAULT NULL,
  `userId` int DEFAULT NULL,
  PRIMARY KEY (`expenseId`),
  KEY `userId` (`userId`),
  CONSTRAINT `expense_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense`
--

LOCK TABLES `expense` WRITE;
/*!40000 ALTER TABLE `expense` DISABLE KEYS */;
INSERT INTO `expense` VALUES (3,'Taxi','Travel Expense',2500,'2021-03-05',1),(4,'Lunch','Food',3000,'2021-03-03',1),(5,'Bus','Travel Expense',200,'2021-03-04',2),(6,'Breakfast','Food',2000,'2021-03-04',2),(7,'Lunch','Food',3000,'2021-03-04',2),(8,'Shirt','Clothes',15000,'2021-03-04',2),(9,'Bus','Travel Expense',200,'2021-03-04',2),(10,'Breakfast','Food',2000,'2021-03-10',2),(11,'Lunch','Food',3000,'2021-03-10',2),(12,'Shirt','Clothes',15000,'2021-03-10',2);
/*!40000 ALTER TABLE `expense` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goal`
--

DROP TABLE IF EXISTS `goal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goal` (
  `goalId` int NOT NULL AUTO_INCREMENT,
  `goalName` varchar(60) DEFAULT NULL,
  `goalImgName` varchar(200) DEFAULT NULL,
  `goalAmount` varchar(60) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `saveType` varchar(45) DEFAULT NULL,
  `amountToSave` int DEFAULT NULL,
  `isBreak` tinyint(3) unsigned zerofill DEFAULT NULL,
  `userId` int DEFAULT NULL,
  PRIMARY KEY (`goalId`),
  KEY `userId_idx` (`userId`),
  CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goal`
--

LOCK TABLES `goal` WRITE;
/*!40000 ALTER TABLE `goal` DISABLE KEYS */;
INSERT INTO `goal` VALUES (4,'Iphone','img.png','20000','2021-02-28 00:00:00','2021-03-01 00:00:00','Daily',20000,000,1),(5,'goal1','img.png','500000','2021-02-28 00:00:00','2021-03-18 00:00:00','Daily',27778,000,1),(6,'goal1','img.png','500000','2021-02-28 00:00:00','2021-03-18 00:00:00','Daily',27778,000,1),(7,'JustSave1','img.png','0','2021-03-01 00:00:00',NULL,NULL,0,000,1),(8,'Donation','img.png','100000','2021-03-03 00:00:00','2021-04-21 00:00:00','Weekly',16666,000,1),(9,'jnenf','LibraryManagementSys(v-2).jpg','10000','2021-03-03 00:00:00','2021-03-18 00:00:00','Daily',714,000,1),(10,'laptop','img.png','3000000','2021-03-03 00:00:00','2022-03-17 00:00:00','Monthly',250000,000,2),(11,'knfo','Thread Life Cycle.png','3232','2021-03-03 00:00:00','2021-03-17 00:00:00','Weekly',3232,000,2),(12,'jih','img.png','6979','2021-03-03 00:00:00','2021-03-05 00:00:00','Daily',6979,000,2),(13,'rgr','img.png','3213','2021-03-03 00:00:00','2021-03-05 00:00:00','Daily',3213,000,2);
/*!40000 ALTER TABLE `goal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(60) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  `targetExpense` int DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'aaa','aaa@gmail.com','1234',1000),(2,'john','john@gmail.com','1234',0),(3,'Martha','Simth','1234',0),(4,'Suzy','Smith','1234',0),(5,'bbb','bbb@gmail.com','1234',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-05 22:23:22
