/*
-- Query: SELECT * FROM sumeldb.withdraw
LIMIT 0, 1000

-- Date: 2021-03-05 23:34
*/
CREATE TABLE `sumeldb`.`withdraw` (
  `withdrawId` INT NOT NULL AUTO_INCREMENT,
  `withdrawAmount` DOUBLE NULL,
  `withdrawAt` DATETIME NULL,
  `goalId` INT NULL,
  PRIMARY KEY (`withdrawId`));
INSERT INTO `` (`withdrawId`,`withdrawAmount`,`withdrawAt`,`goalId`) VALUES (1,345,'2021-03-05 03:57:55',1);
INSERT INTO `` (`withdrawId`,`withdrawAmount`,`withdrawAt`,`goalId`) VALUES (2,2000,'2021-03-05 23:10:45',1);
