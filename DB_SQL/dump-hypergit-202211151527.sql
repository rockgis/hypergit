-- MySQL dump 10.13  Distrib 8.0.30, for macos12.4 (x86_64)
--
-- Host: localhost    Database: hypergit
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `GITBOARD`
--

DROP TABLE IF EXISTS `GITBOARD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GITBOARD` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime DEFAULT NULL,
  `content` text NOT NULL,
  `title` varchar(100) NOT NULL,
  `writer` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITBOARD`
--

LOCK TABLES `GITBOARD` WRITE;
/*!40000 ALTER TABLE `GITBOARD` DISABLE KEYS */;
INSERT INTO `GITBOARD` VALUES (20,'2022-07-13 23:38:57',NULL,'                  공지 내용을 작성 해 주세요.ㅛ','ㅛ','admin'),(21,'2022-07-13 23:39:04',NULL,'                  공지 내용을 작성 해 주세ㅛ요.\r\n                ','ㅛ','admin'),(22,'2022-07-13 23:39:09',NULL,'                  공지 내용을 작성 해 주세요.ㅛ','ㅛ','admin'),(23,'2022-07-13 23:39:15',NULL,'                  공지 내용을 작성 해 주세요.ㅛ','ㅛ','admin'),(24,'2022-07-13 23:39:20',NULL,'                  공지 내용을 작성 해 주세요.ㅛ','ㅛ','admin'),(26,'2022-07-13 23:39:31',NULL,'                  공지 내용을 작성 해 주세요.ㅛ','ㅛ','admin'),(27,'2022-07-13 23:39:37',NULL,'                  공지 내용을 작성 해 주세요.ㅛ','ㅛ','admin');
/*!40000 ALTER TABLE `GITBOARD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GITMEMBER`
--

DROP TABLE IF EXISTS `GITMEMBER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GITMEMBER` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITMEMBER`
--

LOCK TABLES `GITMEMBER` WRITE;
/*!40000 ALTER TABLE `GITMEMBER` DISABLE KEYS */;
INSERT INTO `GITMEMBER` VALUES (1,'admin','$2a$10$wku9tPEO8oTwpSlInntqJ.t8ded9VA2DKYBPI0J.DXQOHDtrG8ifa');
/*!40000 ALTER TABLE `GITMEMBER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GITTA0001`
--

DROP TABLE IF EXISTS `GITTA0001`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GITTA0001` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '등록순번',
  `USR_EN` varchar(30) DEFAULT NULL COMMENT '사번',
  `USR_NM` varchar(30) DEFAULT NULL COMMENT '사용자명',
  `DCD` varchar(50) DEFAULT NULL COMMENT '부서명',
  `EM_NM` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '이메일',
  `NR_IP_AR` varchar(50) DEFAULT NULL COMMENT '관리공간_IP',
  `EAR_EHF` char(1) DEFAULT NULL COMMENT '공용계정 USE_YN',
  `AD_TF` char(1) DEFAULT NULL COMMENT '관리자여부',
  `RG_EN` varchar(30) DEFAULT NULL COMMENT '등록사번',
  `CREATED_DATE` timestamp NULL DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='사용자관리';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTA0001`
--

LOCK TABLES `GITTA0001` WRITE;
/*!40000 ALTER TABLE `GITTA0001` DISABLE KEYS */;
INSERT INTO `GITTA0001` VALUES (1,'admin','관리자','DATA서비스팀','lhlee@hcs.co.kr','0.0.0.0','Y','Y','admin','2022-07-18 02:26:14'),(2,'450192','설재동','DATA서비스팀','jaedong.seol@hcs.co.kr','10.210.241.222','Y','Y','admin','2022-07-18 02:27:37'),(3,'450262','김종은','DATA서비스팀','lhlee@hcs.co.kr','10.210.241.126','Y','Y','admin','2022-07-18 02:27:39'),(4,'416937','이승구','DATA서비스팀','lhlee@hcs.co.kr','10.210.241.163','Y','Y','admin','2022-07-18 02:27:42');
/*!40000 ALTER TABLE `GITTA0001` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GITTA0002`
--

DROP TABLE IF EXISTS `GITTA0002`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GITTA0002` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '등록순번',
  `USR_EN` varchar(30) DEFAULT NULL COMMENT '사용자 ID',
  `US_TF` char(1) DEFAULT NULL COMMENT '사용구분',
  `RG_EN` varchar(30) DEFAULT NULL COMMENT '소유자 사번',
  `USR_NM` varchar(30) DEFAULT NULL COMMENT '사용자명',
  `RULE_ID` int DEFAULT NULL COMMENT 'DATA_권한',
  `SE_TF` char(1) DEFAULT NULL COMMENT '사용유무',
  `ALT_EN` varchar(30) DEFAULT NULL COMMENT '수정사번',
  `CREATED_DATE` timestamp NULL DEFAULT NULL COMMENT '등록일시',
  `MODIFIED_DATE` timestamp NULL DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='공용계정 관리 테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTA0002`
--

LOCK TABLES `GITTA0002` WRITE;
/*!40000 ALTER TABLE `GITTA0002` DISABLE KEYS */;
INSERT INTO `GITTA0002` VALUES (1,'bdpadmin','Y','416937','이승구',1,'Y','admin','2022-07-18 03:36:56','2022-07-18 03:36:56'),(2,'etladmin','Y','416937','이승구',1,'Y','admin','2022-07-18 03:37:00','2022-07-18 03:37:00'),(3,'radmin','Y','450192','이경식',1,'Y','admin','2022-07-18 03:37:04','2022-07-18 03:37:04'),(4,'cdswadmin','Y','450192','설재동',1,'Y','admin','2022-07-18 03:37:07','2022-07-18 03:37:07'),(5,'cdsw','S','450192','설재동',1,'Y','admin','2022-07-18 03:37:12','2022-07-18 03:37:12');
/*!40000 ALTER TABLE `GITTA0002` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GITTB0001`
--

DROP TABLE IF EXISTS `GITTB0001`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GITTB0001` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '등록순번',
  `APP_SN` varchar(50) DEFAULT NULL COMMENT '앱순서',
  `APP_NM` varchar(50) DEFAULT NULL COMMENT '앱명',
  `CL_NM` varchar(100) DEFAULT NULL COMMENT '클러스터명',
  `PGE_URL_AR` varchar(200) DEFAULT NULL COMMENT '링크 URL',
  `DESC_TT` varchar(4000) DEFAULT NULL COMMENT '설명',
  `SE_TF` char(1) DEFAULT NULL COMMENT '사용여부',
  `BS_TF` char(1) DEFAULT NULL COMMENT '기본사용여부',
  `RG_EN` varchar(30) DEFAULT NULL COMMENT '등록사번',
  `CREATED_DATE` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `ALT_EN` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '수정사번',
  `MODIFIED_DATE` timestamp NULL DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='앱관리';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTB0001`
--

LOCK TABLES `GITTB0001` WRITE;
/*!40000 ALTER TABLE `GITTB0001` DISABLE KEYS */;
INSERT INTO `GITTB0001` VALUES (7,'1','HUE','서비스 BDP','http://hue.uiscloud.com','                  앱 설명을 넣어 주세요.\r\n\r\n                ','Y','Y','admin','2022-07-13 10:39:32','admin',NULL),(8,'1','HUE','통합 BDP','http://hue.uiscloud.com','                  앱 설명을 넣어 주세요.\r\n\r\n                ','Y','Y','admin','2022-07-13 10:39:56','admin',NULL),(9,'1','HUE','서비스 BDP','http://hue.uiscloud.com','                  앱 설명을 넣어 주세요.\r\n\r\n                ','Y','Y','admin','2022-07-13 10:43:26','admin',NULL);
/*!40000 ALTER TABLE `GITTB0001` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GITTC0001`
--

DROP TABLE IF EXISTS `GITTC0001`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GITTC0001` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '등록순번',
  `GSN_ID` int NOT NULL COMMENT '앱코드',
  `APP_NM` varchar(50) DEFAULT NULL COMMENT '앱명',
  `CL_NM` varchar(100) DEFAULT NULL COMMENT '클러스터명',
  `PGE_URL_AR` varchar(200) DEFAULT NULL COMMENT '링크 URL',
  `ROLE_ID` int DEFAULT NULL COMMENT '역할ID',
  `USR_DCD` varchar(50) DEFAULT NULL COMMENT '부서명',
  `USR_EN` varchar(30) DEFAULT NULL COMMENT '사용자ID',
  `USR_NM` varchar(30) DEFAULT NULL COMMENT '사용자명',
  `RG_EN` varchar(30) DEFAULT NULL COMMENT 'P아이디',
  `EM_NM` char(50) DEFAULT NULL COMMENT '이메일',
  `USE_YN` char(1) DEFAULT NULL COMMENT '사용유부',
  `ALT_EN` varchar(30) DEFAULT NULL COMMENT '수정사번',
  `START_DATE` char(10) DEFAULT NULL COMMENT '시작 일자 ',
  `END_DATE` char(10) DEFAULT NULL COMMENT '종료 일자',
  `CREATED_DATE` timestamp NULL DEFAULT NULL COMMENT '생성 일자',
  `MODIFIED_DATE` timestamp NULL DEFAULT NULL COMMENT '변경 일자',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='사용자권한관리';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTC0001`
--

LOCK TABLES `GITTC0001` WRITE;
/*!40000 ALTER TABLE `GITTC0001` DISABLE KEYS */;
INSERT INTO `GITTC0001` VALUES (1,1,'서비스 BDP HUE','서비스 BDP','https://hue.uiscloud.com',1,'DATA 서비스 팀','450192','설재동','bdpadmin','jaedong.seol@hcs.co.kr','Y','admin','2022-07-01','2022-08-30','2022-07-18 15:03:05','2022-07-18 15:03:05'),(2,1,'통합 BDP HUE','통합 BDP','https://hue.uiscloud.com',1,'DATA 서비스 팀','450192','설재동','bdpadmin','jaedong.seol@hcs.co.kr','Y','admin','2022-07-01','2022-08-30','2022-07-18 15:03:10','2022-07-18 15:03:10'),(3,1,'서비스 BDP CDSW','서비스 BDP','https://cdsw.uiscloud.com',1,'DATA 서비스 팀','450192','설재동','bdpadmin','jaedong.seol@hcs.co.kr','Y','admin','2022-07-01','2022-08-30','2022-07-18 15:03:13','2022-07-18 15:03:13'),(4,1,'통합 BDP CDSW','통합 BDP','https://cdsw.uiscloud.com',1,'DATA 서비스 팀','450192','설재동','bdpadmin','jaedong.seol@hcs.co.kr','Y','admin','2022-07-01','2022-08-30','2022-07-18 15:03:17','2022-07-18 15:03:17');
/*!40000 ALTER TABLE `GITTC0001` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GITTD0001`
--

DROP TABLE IF EXISTS `GITTD0001`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GITTD0001` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '등록순번',
  `CREATED_DATE` timestamp NULL DEFAULT NULL COMMENT '시간',
  `DCD` varchar(50) DEFAULT NULL COMMENT '부서코드',
  `USR_NM` varchar(30) DEFAULT NULL COMMENT '사용자명',
  `USR_EN` varchar(30) DEFAULT NULL COMMENT '사용자ID',
  `EM_NM` char(20) DEFAULT NULL COMMENT '이메일',
  `UG_NM` char(20) DEFAULT NULL COMMENT '활동',
  `REF_URL_AR` varchar(200) DEFAULT NULL COMMENT 'ReferURL',
  `USR_IP` char(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='인증감사';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTD0001`
--

LOCK TABLES `GITTD0001` WRITE;
/*!40000 ALTER TABLE `GITTD0001` DISABLE KEYS */;
INSERT INTO `GITTD0001` VALUES (1,'2022-07-06 23:39:11','03','관리자','admin','lhlee@goodmit.co.kr','관리자 페이지 접속','https://hue.uiscloud.com','10.200.101.100');
/*!40000 ALTER TABLE `GITTD0001` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GITTD0002`
--

DROP TABLE IF EXISTS `GITTD0002`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GITTD0002` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '등록순번',
  `CREATED_DATE` timestamp NULL DEFAULT NULL COMMENT '시간',
  `DCD` varchar(50) DEFAULT NULL COMMENT '부서코드',
  `USR_NM` varchar(30) DEFAULT NULL COMMENT '사용자명',
  `USR_EN` varchar(30) DEFAULT NULL COMMENT '사용자ID',
  `UG_ID` varchar(50) DEFAULT NULL COMMENT '사용자그룹ID',
  `USR_IP` char(20) DEFAULT NULL COMMENT '접근 IP',
  `EM_NM` char(20) DEFAULT NULL COMMENT '이메일',
  `UG_NM` char(20) DEFAULT NULL COMMENT '상태',
  `LF_DT` char(20) DEFAULT NULL COMMENT '마지막로그인실패시간',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='계정감사';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTD0002`
--

LOCK TABLES `GITTD0002` WRITE;
/*!40000 ALTER TABLE `GITTD0002` DISABLE KEYS */;
INSERT INTO `GITTD0002` VALUES (1,'2022-07-08 12:04:46','03','관리자','admin','관리자 그룹','10.200.101.100','lhlee@goodmit.co.kr','페이지 접속','2022-07-08 11:00:57');
/*!40000 ALTER TABLE `GITTD0002` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GITTD0003`
--

DROP TABLE IF EXISTS `GITTD0003`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GITTD0003` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '등록순번',
  `CREATED_DATE` timestamp NULL DEFAULT NULL COMMENT '시간',
  `WR_TY` char(20) DEFAULT NULL COMMENT '타입',
  `DCD` varchar(50) DEFAULT NULL COMMENT '부서코드',
  `USR_NM` varchar(30) DEFAULT NULL COMMENT '사용자명',
  `USR_EN` varchar(30) DEFAULT NULL COMMENT '사용자ID',
  `UG_ID` int DEFAULT NULL COMMENT '사용권한 부여 이력',
  `RG_EN` varchar(30) DEFAULT NULL COMMENT '등록사번',
  `ALT_EN` varchar(30) DEFAULT NULL COMMENT '수정사번',
  `MODIFIED_DATE` timestamp NULL DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='권한감사';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTD0003`
--

LOCK TABLES `GITTD0003` WRITE;
/*!40000 ALTER TABLE `GITTD0003` DISABLE KEYS */;
INSERT INTO `GITTD0003` VALUES (1,'2022-07-08 12:13:33','통합계정 관리 시스템','03','관리자','admin',1,'admin','admin','2022-07-08 12:13:33');
/*!40000 ALTER TABLE `GITTD0003` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GITTD0004`
--

DROP TABLE IF EXISTS `GITTD0004`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GITTD0004` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '등록순번',
  `CREATED_DATE` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '시간',
  `DCD` varchar(50) DEFAULT NULL COMMENT '부서코드',
  `USR_NM` varchar(30) DEFAULT NULL COMMENT '사용자명',
  `USR_EN` varchar(30) DEFAULT NULL COMMENT '사용자ID',
  `APP_NM` varchar(50) DEFAULT NULL COMMENT '접속 앱명 ',
  `PGE_URL_AR` varchar(200) DEFAULT NULL COMMENT '접속 앱 URL',
  `UG_CT` int DEFAULT NULL COMMENT '접속상태',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='앱점속감사';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTD0004`
--

LOCK TABLES `GITTD0004` WRITE;
/*!40000 ALTER TABLE `GITTD0004` DISABLE KEYS */;
INSERT INTO `GITTD0004` VALUES (1,'2022-07-08 15:11:50','03','관리자','admin','서비스 BDP HUE','https://hue.uiscloud.com',1),(4,'2022-07-12 06:50:40','02','관리자','admin','HUE','https://hue.uiscloud.com',1),(5,'2022-07-13 11:01:05','02','관리자','admin','HUE','https://hue.uiscloud.com',1),(6,'2022-07-13 11:01:25','02','관리자','admin','HUE','https://hue.uiscloud.com',1);
/*!40000 ALTER TABLE `GITTD0004` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GITTE0001`
--

DROP TABLE IF EXISTS `GITTE0001`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GITTE0001` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '등록순번',
  `DCD` varchar(30) DEFAULT NULL COMMENT '부서코드',
  `USR_EN` varchar(30) DEFAULT NULL COMMENT '사번',
  `USR_NM` varchar(30) DEFAULT NULL COMMENT '성명',
  `REQ_NM` varchar(200) DEFAULT NULL COMMENT '연동 정보',
  `MNU_ID` char(1) DEFAULT NULL COMMENT '결과',
  `CREATED_DATE` timestamp(6) NULL DEFAULT NULL COMMENT '등록일시',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='연동로그';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTE0001`
--

LOCK TABLES `GITTE0001` WRITE;
/*!40000 ALTER TABLE `GITTE0001` DISABLE KEYS */;
INSERT INTO `GITTE0001` VALUES (1,'03','관리자','admin','통합 계정관리 시스템 연동 ','Y','2022-07-08 15:23:41.000000'),(2,'03','관리자','admin','통합 계정관리 시스템 연동 ','Y','2022-07-09 01:26:46.000000');
/*!40000 ALTER TABLE `GITTE0001` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GITTF0001`
--

DROP TABLE IF EXISTS `GITTF0001`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GITTF0001` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '등록순번',
  `ROLE_ID` varchar(50) DEFAULT NULL COMMENT '역할ID',
  `ROLE_NM` varchar(50) DEFAULT NULL COMMENT '역할명',
  `DESC_TT` varchar(4000) DEFAULT NULL COMMENT '설명',
  `RG_EN` varchar(30) DEFAULT NULL COMMENT '등록사번',
  `CREATED_DATE` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `ALT_EN` varchar(30) DEFAULT NULL COMMENT '수정사번',
  `MODIFIED_DATE` timestamp NULL DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ROLE관리';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTF0001`
--

LOCK TABLES `GITTF0001` WRITE;
/*!40000 ALTER TABLE `GITTF0001` DISABLE KEYS */;
INSERT INTO `GITTF0001` VALUES (5,'AD02','HUE 관리자','                  앱 설명을 넣어 주세요.\r\n\r\n                ','admin','2022-07-13 13:09:58','admin',NULL),(6,'AD02','HUE 관리자','                  앱 설명을 넣어 주세요.\r\n\r\n                ','admin','2022-07-13 14:52:23','admin',NULL);
/*!40000 ALTER TABLE `GITTF0001` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GITTF0002`
--

DROP TABLE IF EXISTS `GITTF0002`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GITTF0002` (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '등록순번',
  `DCD` varchar(50) DEFAULT NULL COMMENT '부서코드',
  `USR_NM` varchar(30) DEFAULT NULL COMMENT '사용자명',
  `USR_EN` varchar(30) DEFAULT NULL COMMENT '사용자ID',
  `NR_IP_AR` varchar(50) DEFAULT NULL COMMENT 'IP주소',
  `DESC_TT` varchar(4000) DEFAULT NULL COMMENT '설명',
  `SE_TF` char(1) DEFAULT NULL COMMENT '사용유무',
  `RG_EN` varchar(30) DEFAULT NULL COMMENT '등록사번',
  `CREATED_DATE` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `ALT_EN` varchar(30) DEFAULT NULL COMMENT '수정사번',
  `MODIFIED_DATE` timestamp NULL DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='접속허용IP관리';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTF0002`
--

LOCK TABLES `GITTF0002` WRITE;
/*!40000 ALTER TABLE `GITTF0002` DISABLE KEYS */;
INSERT INTO `GITTF0002` VALUES (1,'AD01','관리자','admin','0.0.0.0','관리자 사용 IP등록 사용','Y','admin','2022-07-09 01:39:06','admin','2022-07-09 01:39:06'),(4,'AD01','DDDD','DDDD','0.0.0.0','                  앱 설명을 넣어 주세요.\r\n\r\n                ','Y','admin','2022-07-13 13:27:46','admin',NULL),(6,'AD01','DDDD','DDDD','0.0.0.0','                  앱 설명을 넣어 주세요.\r\n\r\n                ','Y','admin','2022-07-13 14:52:38','admin',NULL);
/*!40000 ALTER TABLE `GITTF0002` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'hypergit'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-15 15:27:06
