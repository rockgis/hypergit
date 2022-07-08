-- MySQL dump 10.13  Distrib 8.0.29, for macos12.2 (x86_64)
--
-- Host: localhost    Database: hypergit
-- ------------------------------------------------------
-- Server version	8.0.29

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
) COMMENT='공지사항';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITBOARD`
--

LOCK TABLES `GITBOARD` WRITE;
/*!40000 ALTER TABLE `GITBOARD` DISABLE KEYS */;
INSERT INTO `GITBOARD` VALUES (1,'2022-06-27 16:05:30','2022-06-27 16:05:38','test','test','test'),(2,'2022-06-27 16:05:44','2022-06-27 16:06:25','3','3','3'),(3,'2022-06-27 16:05:52','2022-06-27 16:06:29','2','2','2'),(4,'2022-06-27 16:05:59','2022-06-27 16:06:33','3','3','3'),(5,'2022-06-27 16:06:07','2022-06-27 16:06:37','4','3','4'),(6,'2022-06-27 16:06:15','2022-06-27 16:06:40','d','d','d'),(7,'2022-06-27 16:27:03',NULL,'g','g','g'),(8,'2022-06-28 01:22:26',NULL,'g','g','g'),(9,'2022-06-28 01:23:06',NULL,'f','f','f'),(10,'2022-06-28 01:26:04',NULL,'fff','f','ff'),(11,'2022-06-28 01:33:33',NULL,'f','f','f'),(12,'2022-06-28 01:34:19',NULL,'fff','f','ff'),(13,'2022-06-28 01:57:28',NULL,'f','f','f'),(14,'2022-06-28 02:02:57',NULL,'t','t','t'),(15,'2022-06-28 12:01:13',NULL,'ㅆ','ㅅㄷㄴㅅ','ㅆ');
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
) COMMENT='Local 사용자관리';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITMEMBER`
--

LOCK TABLES `GITMEMBER` WRITE;
/*!40000 ALTER TABLE `GITMEMBER` DISABLE KEYS */;
INSERT INTO `GITMEMBER` VALUES (1,'admin','$2a$10$wku9tPEO8oTwpSlInntqJ.t8ded9VA2DKYBPI0J.DXQOHDtrG8ifa');
INSERT INTO `GITMEMBER` VALUES (2,'user','$2a$10$wku9tPEO8oTwpSlInntqJ.t8ded9VA2DKYBPI0J.DXQOHDtrG8ifa');
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
  `DCD` varchar(50) DEFAULT NULL COMMENT '부서코드',
  `USR_NM` varchar(30) DEFAULT NULL COMMENT '사용자명',
  `USR_EN` varchar(30) DEFAULT NULL COMMENT '사용자ID',
  `USR_PIN` varchar(30) DEFAULT NULL COMMENT '사용자비밀번호',
  `EM_NM` char(20) DEFAULT NULL COMMENT '이메일',
  `AD_TF` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '관리자여부',
  `USD` varchar(50) DEFAULT NULL COMMENT '사용자여부',
  `SE_TF` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '사용유무',
  `EAR_EHF` char(1) DEFAULT NULL COMMENT '초기설정여부',
  `RG_EN` varchar(30) DEFAULT NULL COMMENT '등록사번',
  `CREATED_DATE` timestamp NULL DEFAULT NULL COMMENT '등록일시',
  `ALT_EN` varchar(30) DEFAULT NULL COMMENT '수정사번',
  `MODIFIED_DATE` timestamp NULL DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`ID`)
) COMMENT='사용자관리';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTA0001`
--

LOCK TABLES `GITTA0001` WRITE;
/*!40000 ALTER TABLE `GITTA0001` DISABLE KEYS */;
/*!40000 ALTER TABLE `GITTA0001` ENABLE KEYS */;
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
  `CREATED_DATE` timestamp NULL DEFAULT NULL COMMENT '등록일시',
  `ALT_EN` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '수정사번',
  `MODIFIED_DATE` timestamp NULL DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`ID`)
) COMMENT='앱관리';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTB0001`
--

LOCK TABLES `GITTB0001` WRITE;
/*!40000 ALTER TABLE `GITTB0001` DISABLE KEYS */;
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
  `ROLE_ID` varchar(50) DEFAULT NULL COMMENT '역할ID',
  `UG_ID` varchar(50) DEFAULT NULL COMMENT '사용자그룹ID',
  `USR_DCD` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '부서코드',
  `USR_NM` varchar(30) DEFAULT NULL COMMENT '사용자명',
  `USR_EN` varchar(30) DEFAULT NULL COMMENT '사용자ID',
  `EM_NM` char(20) DEFAULT NULL COMMENT '이메일',
  `RG_EN` varchar(30) DEFAULT NULL COMMENT '등록사번',
  `CREATED_DATE` timestamp NULL DEFAULT NULL COMMENT '등록일시',
  `ALT_EN` varchar(30) DEFAULT NULL COMMENT '수정사번',
  `MODIFIED_DATE` timestamp NULL DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`ID`)
)  COMMENT='사용자권한관리';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTC0001`
--

LOCK TABLES `GITTC0001` WRITE;
/*!40000 ALTER TABLE `GITTC0001` DISABLE KEYS */;
/*!40000 ALTER TABLE `GITTC0001` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GITTC0002`
--

DROP TABLE IF EXISTS `GITTC0002`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GITTC0002` (
  `UG_ID` varchar(50) DEFAULT NULL COMMENT '사용자그룹ID',
  `UG_NM` varchar(50) DEFAULT NULL COMMENT '사용자그룹명',
  `DESC_TT` varchar(4000) DEFAULT NULL COMMENT '설명',
  `UG_AUZ_ID` varchar(20) DEFAULT NULL COMMENT '사용자그룹권한ID',
  `QEE_SN` int DEFAULT NULL COMMENT '정렬순서',
  `SE_TF` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '사용유무',
  `EAR_EH_F` char(1) DEFAULT NULL COMMENT '초기설정여부',
  `RG_EN` varchar(30) DEFAULT NULL COMMENT '등록사번',
  `CREATED_DATE` timestamp NULL DEFAULT NULL COMMENT '등록일시',
  `ALT_EN` varchar(30) DEFAULT NULL COMMENT '수정사번',
  `MODIFIED_DATE` timestamp NULL DEFAULT NULL COMMENT '수정일시'
)  COMMENT='사용자그룹관리';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTC0002`
--

LOCK TABLES `GITTC0002` WRITE;
/*!40000 ALTER TABLE `GITTC0002` DISABLE KEYS */;
/*!40000 ALTER TABLE `GITTC0002` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GITTC0003`
--

DROP TABLE IF EXISTS `GITTC0003`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GITTC0003` (
  `ROLE_ID` varchar(50) DEFAULT NULL COMMENT '역할ID',
  `ROLE_NM` varchar(50) DEFAULT NULL COMMENT '역할명',
  `DESC_TT` varchar(4000) DEFAULT NULL COMMENT '설명',
  `RG_EN` varchar(30) DEFAULT NULL COMMENT '등록사번',
  `CREATED_DATE` timestamp NULL DEFAULT NULL COMMENT '등록일시',
  `ALT_EN` varchar(30) DEFAULT NULL COMMENT '수정사번',
  `MODIFIED_DATE` timestamp NULL DEFAULT NULL COMMENT '수정일시'
)  COMMENT='ROLE관리';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTC0003`
--

LOCK TABLES `GITTC0003` WRITE;
/*!40000 ALTER TABLE `GITTC0003` DISABLE KEYS */;
/*!40000 ALTER TABLE `GITTC0003` ENABLE KEYS */;
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
  PRIMARY KEY (`ID`)
)  COMMENT='인증감사';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTD0001`
--

LOCK TABLES `GITTD0001` WRITE;
/*!40000 ALTER TABLE `GITTD0001` DISABLE KEYS */;
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
) COMMENT='계정감사';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTD0002`
--

LOCK TABLES `GITTD0002` WRITE;
/*!40000 ALTER TABLE `GITTD0002` DISABLE KEYS */;
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
)  COMMENT='권한감사';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTD0003`
--

LOCK TABLES `GITTD0003` WRITE;
/*!40000 ALTER TABLE `GITTD0003` DISABLE KEYS */;
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
  `CREATED_DATE` timestamp NULL DEFAULT NULL COMMENT '시간',
  `DCD` varchar(50) DEFAULT NULL COMMENT '부서코드',
  `USR_NM` varchar(30) DEFAULT NULL COMMENT '사용자명',
  `USR_EN` varchar(30) DEFAULT NULL COMMENT '사용자ID',
  `APP_NM` varchar(50) DEFAULT NULL COMMENT '접속 앱명 ',
  `PGE_URL_AR` varchar(200) DEFAULT NULL COMMENT '접속 앱 URL',
  `UG_CT` int DEFAULT NULL COMMENT '접속상태',
  PRIMARY KEY (`ID`)
)  COMMENT='앱점속감사';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTD0004`
--

LOCK TABLES `GITTD0004` WRITE;
/*!40000 ALTER TABLE `GITTD0004` DISABLE KEYS */;
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
) COMMENT='연동로그';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTE0001`
--

LOCK TABLES `GITTE0001` WRITE;
/*!40000 ALTER TABLE `GITTE0001` DISABLE KEYS */;
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
  `CREATED_DATE` timestamp NULL DEFAULT NULL COMMENT '등록일시',
  `ALT_EN` varchar(30) DEFAULT NULL COMMENT '수정사번',
  `MODIFIED_DATE` timestamp NULL DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`ID`)
)  COMMENT='ROLE관리';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTF0001`
--

LOCK TABLES `GITTF0001` WRITE;
/*!40000 ALTER TABLE `GITTF0001` DISABLE KEYS */;
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
  `CREATED_DATE` timestamp NULL DEFAULT NULL COMMENT '등록일시',
  `ALT_EN` varchar(30) DEFAULT NULL COMMENT '수정사번',
  `MODIFIED_DATE` timestamp NULL DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`ID`)
)  COMMENT='접속허용IP관리';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GITTF0002`
--

LOCK TABLES `GITTF0002` WRITE;
/*!40000 ALTER TABLE `GITTF0002` DISABLE KEYS */;
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

-- Dump completed on 2022-07-05  9:15:32
