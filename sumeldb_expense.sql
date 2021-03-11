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
-- Table structure for table `expense`
--

DROP TABLE IF EXISTS `expense`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expense` (
  `expenseId` int NOT NULL AUTO_INCREMENT,
  `expenseName` varchar(120) DEFAULT NULL,
  `expenseCategory` varchar(120) DEFAULT NULL,
  `expenseAmount` double DEFAULT NULL,
  `spendAt` date DEFAULT NULL,
  `userId` int DEFAULT NULL,
  PRIMARY KEY (`expenseId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense`
--

LOCK TABLES `expense` WRITE;
/*!40000 ALTER TABLE `expense` DISABLE KEYS */;
INSERT INTO `expense` VALUES (1,'teashop','teashopbill',3000,'2021-01-02',11),(2,'makeup','beauty',15000,'2021-01-10',4),(3,'taxi','visiting',3000,'2021-01-11',11),(4,'phonebill','phone',5000,'2021-01-15',11),(5,'clothes','shopping',20000,'2021-01-31',11),(6,'wifi','Communicationbill',25000,'2021-02-05',11),(7,'snacks','eating',3000,'2021-02-02',11),(8,'album','kpop',15000,'2021-02-10',4),(9,'edmfestival','festivalticket',30000,'2018-12-31',11),(10,'bubbletea','eatingandDrinking',2500,'2020-01-10',4),(11,'Got7 Ticket','kpop',20000,'2021-03-10',11),(12,'mpt','Communicationbill',1000,'2021-03-10',11),(13,'telenor','Communicationbill',2000,'2021-03-10',11),(14,'Taxi','Taxii',1000,'2021-03-11',11),(15,'Bus','Bus',200,'2021-03-11',11);
/*!40000 ALTER TABLE `expense` ENABLE KEYS */;
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
