-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: k9a602.p.ssafy.io    Database: novelit
-- ------------------------------------------------------
-- Server version	11.1.2-MariaDB-1:11.1.2+maria~ubu2204

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
-- Table structure for table `plot`
--

DROP TABLE IF EXISTS `plot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plot` (
  `plot_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `beginning` varchar(2000) DEFAULT '',
  `climax` varchar(2000) DEFAULT '',
  `crisis` varchar(2000) DEFAULT '',
  `ending` varchar(2000) DEFAULT '',
  `plot_title` varchar(500) DEFAULT '제목 없음',
  `plot_uuid` varchar(255) NOT NULL,
  `rising` varchar(2000) DEFAULT '',
  `story` varchar(2000) DEFAULT '',
  `workspace_uuid` varchar(255) NOT NULL,
  PRIMARY KEY (`plot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plot`
--

LOCK TABLES `plot` WRITE;
/*!40000 ALTER TABLE `plot` DISABLE KEYS */;
INSERT INTO `plot` VALUES (51,'','','','','새 플롯','9f0ebde7-d4ef-41ca-a6d4-944a0845c6a1','','','ccfacf55-34c1-4212-b2fa-e77ab7eeaaa2'),(56,'','','','','새 플롯','f726fd08-e0d2-4bcd-b460-2cb5a9af9164','','','1e92db0a-2ad1-4153-83ab-4f133cfcc51f'),(57,'','','','','새 플롯','2b7315e1-1aec-49bf-8ad4-ee59cec3bb63','','','1e92db0a-2ad1-4153-83ab-4f133cfcc51f'),(58,'','','','','새 플롯','2f9452c9-0440-48e8-8a86-8ddfbef88ea6','','','1e92db0a-2ad1-4153-83ab-4f133cfcc51f'),(59,'주인공이 태어나서','싸우다가','빌런을 만나','죽음','주인공이 죽다','4ce3aa4c-d16f-40a1-9f2e-931d1f883080','잘먹고 잘살다','주인공이 ㅈ구었음ㄴㅇㅁ\nㅋㅋ\nㄴ아첩애ㅏ나므치킴니\nㅁㄴㅇ\n\nㅁㄴㅇㅁ\nㅇ\nㅁㄴ\nㅇㅁ\nㄴㅇㅁㄴ\nㅇㅁㅇ\nㅁㄴㅇ\nㅁㄴㅇ\nㅁㅇ','ce6d6c63-9c15-4fab-a269-08b1232851b5'),(60,'','','','','새 플롯','f6d9008e-9714-48c2-9ac2-49962af48029','','','1e92db0a-2ad1-4153-83ab-4f133cfcc51f'),(61,'','','','','새 플롯','99ba693a-d47f-48ab-b23a-f0ce79d364cb','','','1e92db0a-2ad1-4153-83ab-4f133cfcc51f'),(62,'','','','','새 플롯','fd4fbcc3-4b3e-4bcf-abf4-01af63be7e1b','','','1e92db0a-2ad1-4153-83ab-4f133cfcc51f'),(83,'','','','','1권 스토리 정리','4cada9a9-11d2-4faf-8b5f-4a8ab6733f61','','','e4220357-a8a2-4027-955c-412c499d20fd'),(84,'faew','awef','afwe','awef','이찬민','1cf0bb0a-d113-45f4-9a3c-c49ce0b8c056','afew','afwe','23fae2d9-06c4-40a5-bec2-bae999ed399f'),(85,'','','','','찬민','cde82de8-fd51-48d8-a561-a9d8afedbaa3','','','23fae2d9-06c4-40a5-bec2-bae999ed399f'),(86,'','','','','찬민이','96b66576-1ee1-47cd-932f-7579c4683ef9','','','23fae2d9-06c4-40a5-bec2-bae999ed399f'),(87,'','','','','이찬민이','2c120f48-6c30-419b-afae-448ec10c4a30','','','23fae2d9-06c4-40a5-bec2-bae999ed399f'),(88,'','','','','새 플ㄻㅈㄹㅈㄷㅁ롯','5890136c-3f6a-4f2b-9ab8-b8238c3e8765','','','23fae2d9-06c4-40a5-bec2-bae999ed399f'),(89,'','','','','새 플ㄻㄷㅈㄹ롯','964dc167-672a-417c-b2ea-cff436beb726','','','23fae2d9-06c4-40a5-bec2-bae999ed399f'),(90,'','','','','새 플ㄻㅈㄹㅈㅁ롯','69f377f0-42c7-4000-bc6f-73db678e189e','','','23fae2d9-06c4-40a5-bec2-bae999ed399f'),(91,'','','','','새 플ㅁㄹㄷㅈㄻㅈ롯','90ee8d03-a0d0-4455-b64e-336d11fe83c4','','','23fae2d9-06c4-40a5-bec2-bae999ed399f'),(92,'','','','','2권 스토리 정리','39d55f48-e181-4b71-b4b4-fbb93d71f3a7','','','e4220357-a8a2-4027-955c-412c499d20fd'),(93,'','','','','3화 스토리 정리','3f29a0fd-d6b4-4217-bc58-11932b7c5eb3','','','e4220357-a8a2-4027-955c-412c499d20fd'),(94,'','','','','4화 스토리 정리','e735c1d6-6786-4371-82d1-59176ecdceb3','','','e4220357-a8a2-4027-955c-412c499d20fd'),(95,'','','','','새 플롯','d294ca88-f848-4793-bb50-149112f6b012','','','23fae2d9-06c4-40a5-bec2-bae999ed399f'),(96,'','','','','새 플롯','2d57b890-d7b2-4a3c-9e89-4b2f1a041c4b','','','2aea0a4e-7c12-47d1-b971-6786ddd0c60b'),(97,'','','','','삼국지 1화','3e34739e-ebcd-4451-8b54-ed98501dc080','','한고조 유방이 천하를 통일한뒤로, 점점 국력이 쇠태해 나라가 기울고있었다. 이런 틈을타, 장각, 장보, 장량이란 삼형자가 난을 일으켰는데, 이른바 황건적의 난이였다. 황건적은 세력이 너무 커서','4685b152-5963-4e02-bc3b-f15b52ca947c');
/*!40000 ALTER TABLE `plot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(320) NOT NULL,
  `nickname` varchar(16) NOT NULL,
  `user_uuid` varchar(36) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'test@gmail.com','test','f72a8efc-99dc-4afd-a658-6f42073fb7a3'),(2,'wjswnsrb93@naver.com','즈하','4c711062-92cb-4919-9c39-e8f6ed7b249a'),(3,'pjh3433@naver.com','박재훈','c852bbb2-388c-4354-827f-106f80f1ae60'),(4,'dlwjddls0130@naver.com','이정인','60410da2-0a4f-4e43-82d5-8269bd28e3ff'),(5,'chemwang13@naver.com','찬민','30422611-a3fd-433c-8488-342128b7536a'),(6,'ssh102525@nate.com','송수현','e24875c5-3fda-40ee-9d93-d84c33b75b2e'),(7,'quso12358@naver.com','나건','dd8da9d9-1445-49af-b5ff-9bdc117f2b90'),(8,'tjrwns0407@naver.com','석준','82a51199-6b9b-4a61-a0ec-48189fa3598b');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `words`
--

DROP TABLE IF EXISTS `words`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `words` (
  `word_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_character` bit(1) DEFAULT NULL,
  `user_uuid` varchar(255) DEFAULT NULL,
  `word` varchar(255) DEFAULT NULL,
  `word_uuid` varchar(255) DEFAULT NULL,
  `workspace_uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`word_id`)
) ENGINE=InnoDB AUTO_INCREMENT=211 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `words`
--

LOCK TABLES `words` WRITE;
/*!40000 ALTER TABLE `words` DISABLE KEYS */;
INSERT INTO `words` VALUES (39,_binary '\0','c852bbb2-388c-4354-827f-106f80f1ae60','주인공','5adeb082-eb1f-4613-8296-14ccab60e69b','ce6d6c63-9c15-4fab-a269-08b1232851b5'),(40,_binary '\0','c852bbb2-388c-4354-827f-106f80f1ae60','빌런','ee4f47da-bb6c-465f-a4fb-76ee58f2c419','ce6d6c63-9c15-4fab-a269-08b1232851b5'),(41,_binary '\0','c852bbb2-388c-4354-827f-106f80f1ae60','멀티캠퍼스','2a13f4bc-b572-40d7-bdcd-7b0ba2fc5fd8','ce6d6c63-9c15-4fab-a269-08b1232851b5'),(68,_binary '\0','82a51199-6b9b-4a61-a0ec-48189fa3598b','윤석준','f7707990-a223-4ddf-a891-f913b1016f17','undefined'),(108,_binary '\0','82a51199-6b9b-4a61-a0ec-48189fa3598b','그룹1','857c8ba7-9d27-457a-bb12-f82a401ba075','295d4e4b-2472-4029-b713-1e2de8d0f1d9'),(109,_binary '\0','82a51199-6b9b-4a61-a0ec-48189fa3598b','그룹2','b6b32f7f-4b66-48f7-be65-434f422acee1','295d4e4b-2472-4029-b713-1e2de8d0f1d9'),(110,_binary '\0','82a51199-6b9b-4a61-a0ec-48189fa3598b','캐릭터1','c32c79ef-40d2-448b-af2c-39b5062bf360','295d4e4b-2472-4029-b713-1e2de8d0f1d9'),(111,_binary '\0','82a51199-6b9b-4a61-a0ec-48189fa3598b','캐릭터2','25969361-4643-4544-9a39-0d666b204996','295d4e4b-2472-4029-b713-1e2de8d0f1d9'),(122,_binary '\0','c852bbb2-388c-4354-827f-106f80f1ae60','SS','7bbd3111-f1c5-4dbb-a5dc-4692681c8e68','ce6d6c63-9c15-4fab-a269-08b1232851b5'),(123,_binary '\0','c852bbb2-388c-4354-827f-106f80f1ae60','ASDAD','b63d6abd-b88b-4c00-8e11-ac9648ce6e22','ce6d6c63-9c15-4fab-a269-08b1232851b5'),(124,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','8193fa69-2502-483c-a28c-9837f7926f31','e4220357-a8a2-4027-955c-412c499d20fd'),(125,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','158352c3-5040-4339-a451-30c85b323f58','e4220357-a8a2-4027-955c-412c499d20fd'),(126,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','2fa83b60-a79c-443a-b091-1d7ab065b6cc','e4220357-a8a2-4027-955c-412c499d20fd'),(127,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','4ec38806-673a-4a2d-b1d0-dee9c5e89508','e4220357-a8a2-4027-955c-412c499d20fd'),(128,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','aaa0fe8b-1066-49fb-8ac4-e924b82fc631','e4220357-a8a2-4027-955c-412c499d20fd'),(129,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','c5d92189-d6e4-4ae9-99d7-01197eba5bac','e4220357-a8a2-4027-955c-412c499d20fd'),(130,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','b4952d29-e5be-426d-8d67-716f7d9fadea','e4220357-a8a2-4027-955c-412c499d20fd'),(131,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','58fcb648-96dd-44f9-a631-29582c9536ca','e4220357-a8a2-4027-955c-412c499d20fd'),(132,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','d3bb4461-83c3-49be-8685-2f294ae8dccb','e4220357-a8a2-4027-955c-412c499d20fd'),(133,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','b92869be-c3bd-4094-be1b-6fb7b23e7bdd','e4220357-a8a2-4027-955c-412c499d20fd'),(134,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','442d6198-b9ac-40c0-96d2-6b2f4ae7c335','e4220357-a8a2-4027-955c-412c499d20fd'),(135,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','34155429-0439-441f-b5db-25b129825532','e4220357-a8a2-4027-955c-412c499d20fd'),(136,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','23d552e3-5fce-477f-a1a2-471d3e6cc17b','e4220357-a8a2-4027-955c-412c499d20fd'),(137,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','309ac6d8-971b-4831-bdf6-d0246e83e0ce','e4220357-a8a2-4027-955c-412c499d20fd'),(138,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','bc22d70d-8d40-440d-86b6-0a53ef2a996e','e4220357-a8a2-4027-955c-412c499d20fd'),(139,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','ed178049-2654-4ada-a14f-bc48f1004774','e4220357-a8a2-4027-955c-412c499d20fd'),(140,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','794daba5-faf1-4787-9c24-3f5a7e19949a','e4220357-a8a2-4027-955c-412c499d20fd'),(141,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','29e72bf5-51de-4488-9208-060fdb9e275e','e4220357-a8a2-4027-955c-412c499d20fd'),(142,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','c64db325-25fe-4a4e-b2a1-ad915a32f4cc','e4220357-a8a2-4027-955c-412c499d20fd'),(143,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 그룹','561d13aa-f0d1-432b-8969-5f9fe568979b','e4220357-a8a2-4027-955c-412c499d20fd'),(144,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','90d47c81-bef3-4755-b630-a9ffde8d9eda','e4220357-a8a2-4027-955c-412c499d20fd'),(145,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','57ff2306-2ed2-4d04-9fba-119200b96186','e4220357-a8a2-4027-955c-412c499d20fd'),(146,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','a6f08a7a-aae8-426e-806b-55d6e9a8376a','e4220357-a8a2-4027-955c-412c499d20fd'),(147,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','7a2a2e4c-8bf8-4c7f-ba79-b1e9147b3e9a','e4220357-a8a2-4027-955c-412c499d20fd'),(148,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','951fdcec-910d-46dd-b858-8753706b4d67','e4220357-a8a2-4027-955c-412c499d20fd'),(149,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','5992cdfe-bcbe-497c-81f5-a8fccd17268d','e4220357-a8a2-4027-955c-412c499d20fd'),(150,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','918452a1-f25e-47ac-bd26-38476bee3b8d','e4220357-a8a2-4027-955c-412c499d20fd'),(151,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','305190c7-ad39-41cc-a7b5-b3c55f31f3f1','e4220357-a8a2-4027-955c-412c499d20fd'),(152,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','248de636-cc1e-48ad-888f-ee56a5f8652a','e4220357-a8a2-4027-955c-412c499d20fd'),(153,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','bc88a5c4-1402-48e9-8011-de2c76182990','e4220357-a8a2-4027-955c-412c499d20fd'),(154,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','3c5e3aba-66ba-47e3-9abe-d3fbfc5b5d32','e4220357-a8a2-4027-955c-412c499d20fd'),(155,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','f9ff9bc5-d22f-4133-8a7d-c47298f89f28','e4220357-a8a2-4027-955c-412c499d20fd'),(156,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','d981fbca-4611-4051-89a1-be2d0ccaf262','e4220357-a8a2-4027-955c-412c499d20fd'),(157,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','b94e7e11-90d4-4d99-8276-676024fc2595','e4220357-a8a2-4027-955c-412c499d20fd'),(158,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','a767b67d-c546-456c-afdf-83b39c493c3e','e4220357-a8a2-4027-955c-412c499d20fd'),(159,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','edc984d3-f227-4b39-8099-e062f98ca510','e4220357-a8a2-4027-955c-412c499d20fd'),(160,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','41ff64b5-cd15-43f1-84c2-d0f1b1124684','e4220357-a8a2-4027-955c-412c499d20fd'),(161,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','c9723a5e-b92d-4f90-81f3-cda5129e2662','e4220357-a8a2-4027-955c-412c499d20fd'),(162,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','7a4200b5-3b5b-473a-845a-5829b54b8f3d','e4220357-a8a2-4027-955c-412c499d20fd'),(163,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','9f548f49-715f-4f40-a4a4-731fbdcd2e04','e4220357-a8a2-4027-955c-412c499d20fd'),(164,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','a60f17db-e58c-4b6f-88b1-7bf439f09960','e4220357-a8a2-4027-955c-412c499d20fd'),(165,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','7f70fef3-1a14-49b7-8d2f-817b056dd5e3','e4220357-a8a2-4027-955c-412c499d20fd'),(166,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','63ec1783-9baa-4f82-9d40-49f625e9da02','e4220357-a8a2-4027-955c-412c499d20fd'),(167,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','7e5db65f-f9a0-42e2-8d26-2a5ae238e650','e4220357-a8a2-4027-955c-412c499d20fd'),(168,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','c143d706-0e87-4a05-a79a-c7a87033602c','e4220357-a8a2-4027-955c-412c499d20fd'),(169,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','a4011571-d26f-4e6d-930d-9a760d05b052','e4220357-a8a2-4027-955c-412c499d20fd'),(170,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','0277b523-e03c-4904-81bc-e0b7d2a2dc2f','e4220357-a8a2-4027-955c-412c499d20fd'),(171,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','daf8b93e-845f-47da-ba93-444aa643991d','e4220357-a8a2-4027-955c-412c499d20fd'),(172,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','ea12c43a-e500-4db6-b1eb-8168ac494325','e4220357-a8a2-4027-955c-412c499d20fd'),(173,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','1b46b549-ec40-486f-b192-e3b2447eb811','e4220357-a8a2-4027-955c-412c499d20fd'),(174,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','9c8024a1-cc70-48bb-8096-628f677b384b','e4220357-a8a2-4027-955c-412c499d20fd'),(175,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','f8dc37ed-8a49-43ca-90a6-10fd40634eda','e4220357-a8a2-4027-955c-412c499d20fd'),(176,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','유유유','595d7156-141f-4135-9eb0-fed49a64c351','e4220357-a8a2-4027-955c-412c499d20fd'),(177,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','유유유','29ec3068-2fc0-4bbc-bd65-a10b6b50716d','e4220357-a8a2-4027-955c-412c499d20fd'),(178,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','유유유','1ecf91d7-a7f5-438a-92bc-4b4d30ab4a5e','e4220357-a8a2-4027-955c-412c499d20fd'),(179,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','유유유','2b7371d4-d12b-4bac-8ac6-494afdde6b9d','e4220357-a8a2-4027-955c-412c499d20fd'),(180,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','유유유','75a8cea7-e236-4e7d-ae76-b7e6355eabb9','e4220357-a8a2-4027-955c-412c499d20fd'),(181,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','유유유','de74ef8c-af0f-4b71-8e51-4fc39b259dee','e4220357-a8a2-4027-955c-412c499d20fd'),(182,_binary '\0','c852bbb2-388c-4354-827f-106f80f1ae60','ㅣ','e0b2d26a-8fa1-4cc2-b34e-b4d4694b2daf','ce6d6c63-9c15-4fab-a269-08b1232851b5'),(183,_binary '\0','c852bbb2-388c-4354-827f-106f80f1ae60','ㄴㅁㅇ','22f1ed03-49bc-438b-95da-e7bb427e4c74','ce6d6c63-9c15-4fab-a269-08b1232851b5'),(184,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 그룹','7bcc5d8c-d93f-49db-9470-7095e29e3ed5','e4220357-a8a2-4027-955c-412c499d20fd'),(185,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','6bc8e57a-740b-4ab8-93cb-466d90d015a9','e4220357-a8a2-4027-955c-412c499d20fd'),(186,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','b554f2f2-6d77-4716-bdbf-4fc443825b47','e4220357-a8a2-4027-955c-412c499d20fd'),(187,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','deb03154-fe43-49d7-b5b2-eb99118e0eea','e4220357-a8a2-4027-955c-412c499d20fd'),(188,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','53ecfbf9-06da-4d75-8d2f-61f53192ff50','e4220357-a8a2-4027-955c-412c499d20fd'),(189,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 캐릭터','4f298ec3-10cb-4fac-8534-a8315fed0617','e4220357-a8a2-4027-955c-412c499d20fd'),(190,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 그룹','28b1cbaa-e1ad-4959-bcbf-d4c5ac761504','e4220357-a8a2-4027-955c-412c499d20fd'),(191,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 그룹','cda787b1-96d2-4ef1-9c2c-d7d1fb85a49c','e4220357-a8a2-4027-955c-412c499d20fd'),(192,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 그룹','de84d9f2-16b2-4b66-bb47-ed0790b85a8e','e4220357-a8a2-4027-955c-412c499d20fd'),(193,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 그룹','72cc2141-5c08-40a0-b4de-e370c7116a51','e4220357-a8a2-4027-955c-412c499d20fd'),(194,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 그룹','310e8040-8371-4455-93e0-c1124b9895fb','e4220357-a8a2-4027-955c-412c499d20fd'),(195,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 그룹','21e27893-e9f1-4dfe-b877-b219cd2e64a8','e4220357-a8a2-4027-955c-412c499d20fd'),(196,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 그룹','ad6275a1-d5f7-4036-ac34-654bb0068095','e4220357-a8a2-4027-955c-412c499d20fd'),(197,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','슬리데린','d8c35c09-1f86-425b-a208-0ca5af6e0d86','e4220357-a8a2-4027-955c-412c499d20fd'),(198,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','호그와트','f46c39cd-45e8-4d98-a0f5-ca64e0ce4213','e4220357-a8a2-4027-955c-412c499d20fd'),(199,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','슬리데린','c6ee2b37-cd9d-4152-b2b2-7af3822cb044','e4220357-a8a2-4027-955c-412c499d20fd'),(200,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','그리핀도르','1d6b3360-6036-43b5-9614-14f503d225c7','e4220357-a8a2-4027-955c-412c499d20fd'),(201,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','후플푸프','2858a7cd-fb78-432e-8234-114eba38a8e9','e4220357-a8a2-4027-955c-412c499d20fd'),(202,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','래번클로','1359a27e-b398-498c-8a1b-b524b08072bf','e4220357-a8a2-4027-955c-412c499d20fd'),(203,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','살라자르 슬리데린','66f59df6-f980-4d77-a57b-ee5387ca6771','e4220357-a8a2-4027-955c-412c499d20fd'),(204,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','멀린','31906ebb-f0bb-4443-8284-c44e3c6b2ae3','e4220357-a8a2-4027-955c-412c499d20fd'),(205,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','말포이','590afdd1-a198-48ee-93e2-b30d5bd2a3a1','e4220357-a8a2-4027-955c-412c499d20fd'),(206,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','머글','8e5841fe-4cc2-42e9-9929-19831f880058','e4220357-a8a2-4027-955c-412c499d20fd'),(207,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','새 그룹','08f28310-e659-477e-aed4-46018c91d520','e4220357-a8a2-4027-955c-412c499d20fd'),(208,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','해리포터','66fdd97c-16a5-46f1-ad5e-e72354b8695a','e4220357-a8a2-4027-955c-412c499d20fd'),(209,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','헤르미온느','ac35f9c7-7d00-4f62-a304-f7d9cf11b1a4','e4220357-a8a2-4027-955c-412c499d20fd'),(210,_binary '\0','e24875c5-3fda-40ee-9d93-d84c33b75b2e','론','c6d3b11a-e700-4b65-9a60-10b14a35bee3','e4220357-a8a2-4027-955c-412c499d20fd');
/*!40000 ALTER TABLE `words` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workspace`
--

DROP TABLE IF EXISTS `workspace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workspace` (
  `workspace_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(60) NOT NULL,
  `user_uuid` varchar(36) NOT NULL,
  `workspace_uuid` varchar(36) NOT NULL,
  PRIMARY KEY (`workspace_id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workspace`
--

LOCK TABLES `workspace` WRITE;
/*!40000 ALTER TABLE `workspace` DISABLE KEYS */;
INSERT INTO `workspace` VALUES (64,'해리포터','c852bbb2-388c-4354-827f-106f80f1ae60','ce6d6c63-9c15-4fab-a269-08b1232851b5'),(65,'삼국지','4c711062-92cb-4919-9c39-e8f6ed7b249a','b452b630-afa3-426c-acc4-8535b5a1cac7'),(66,'수현\'s workspacedd','e24875c5-3fda-40ee-9d93-d84c33b75b2e','1e92db0a-2ad1-4153-83ab-4f133cfcc51f'),(67,'박재훈','30422611-a3fd-433c-8488-342128b7536a','cd87c664-0b5c-4582-ac17-dbf9572c3841'),(68,'안녕','30422611-a3fd-433c-8488-342128b7536a','5524a36e-139e-4c5b-9def-63f37ef021fa'),(69,'zz','c852bbb2-388c-4354-827f-106f80f1ae60','d809cd04-ef28-4635-bfb8-f4a5db0769bb'),(70,'asdf','60410da2-0a4f-4e43-82d5-8269bd28e3ff','23fae2d9-06c4-40a5-bec2-bae999ed399f'),(71,'삼국지','e24875c5-3fda-40ee-9d93-d84c33b75b2e','e4220357-a8a2-4027-955c-412c499d20fd'),(72,'인생이란?','dd8da9d9-1445-49af-b5ff-9bdc117f2b90','516252da-534c-4b21-a3bf-d2a84468e8c1'),(73,'작품','82a51199-6b9b-4a61-a0ec-48189fa3598b','295d4e4b-2472-4029-b713-1e2de8d0f1d9'),(74,'dqwdwq','60410da2-0a4f-4e43-82d5-8269bd28e3ff','7fe54301-afe3-427b-b335-1e2530f05a89'),(75,'faew','60410da2-0a4f-4e43-82d5-8269bd28e3ff','c03e31d6-614b-47d2-aa57-f983bf1d49d9'),(76,'fawe','60410da2-0a4f-4e43-82d5-8269bd28e3ff','387c5ef7-d438-4559-b40e-7d7d7546364f'),(77,'찐테스트','60410da2-0a4f-4e43-82d5-8269bd28e3ff','a634649b-ef9a-4ee5-ad9b-3440eae50a1f'),(78,'해리포터','60410da2-0a4f-4e43-82d5-8269bd28e3ff','2aea0a4e-7c12-47d1-b971-6786ddd0c60b'),(79,'삼국지','60410da2-0a4f-4e43-82d5-8269bd28e3ff','4685b152-5963-4e02-bc3b-f15b52ca947c');
/*!40000 ALTER TABLE `workspace` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-17 11:19:15
