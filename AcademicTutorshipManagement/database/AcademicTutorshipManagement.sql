-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: academictutorshipmanagement
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `academicofferings`
--

DROP TABLE IF EXISTS `academicofferings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academicofferings` (
  `idAcademicOfferings` int NOT NULL AUTO_INCREMENT,
  `nrc` int NOT NULL,
  `idEducationalExperience` int NOT NULL,
  `idAcademicPersonnel` int NOT NULL,
  `idSchoolPeriod` int NOT NULL,
  PRIMARY KEY (`idAcademicOfferings`),
  KEY `FK_AcademicOfferings_EducationalExperience_idx` (`idEducationalExperience`),
  KEY `FK_AcademicOfferings_AcademicPersonnel_idx` (`idAcademicPersonnel`),
  KEY `FK_AcademicOfferings_SchoolPeriod_idx` (`idSchoolPeriod`),
  CONSTRAINT `FK_AcademicOfferings_AcademicPersonnel` FOREIGN KEY (`idAcademicPersonnel`) REFERENCES `academicpersonnel` (`idAcademicPersonnel`),
  CONSTRAINT `FK_AcademicOfferings_EducationalExperience` FOREIGN KEY (`idEducationalExperience`) REFERENCES `educationalexperience` (`idEducationalExperience`),
  CONSTRAINT `FK_AcademicOfferings_SchoolPeriod` FOREIGN KEY (`idSchoolPeriod`) REFERENCES `schoolperiod` (`idSchoolPeriod`)
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academicofferings`
--

LOCK TABLES `academicofferings` WRITE;
/*!40000 ALTER TABLE `academicofferings` DISABLE KEYS */;
INSERT INTO `academicofferings` VALUES (1,81756,10,1,1),(2,81761,11,2,1),(3,81764,12,3,1),(4,81767,13,4,1),(5,81744,8,5,1),(6,81752,9,6,1),(7,81768,5,7,1),(8,81851,4,8,1),(9,86634,7,9,1),(10,81774,10,10,1),(11,81778,11,2,1),(12,81779,12,11,1),(13,81781,13,12,1),(14,81771,8,1,1),(15,86525,20,12,1),(16,86530,21,16,1),(17,86536,22,13,1),(18,86547,23,3,1),(19,86567,24,17,1),(20,89510,15,4,1),(21,86527,20,18,1),(22,86532,21,14,1),(23,86551,23,1,1),(24,86579,24,16,1),(25,90614,20,19,1),(26,90631,21,16,1),(27,90640,24,20,1),(28,89513,30,18,1),(29,89515,31,21,1),(30,89520,32,22,1),(31,89521,33,19,1),(32,89522,34,12,1),(33,89505,28,19,1),(34,90018,30,20,1),(35,89532,31,14,1),(36,89545,32,23,1),(37,94045,33,20,1),(38,89550,34,18,1),(39,94018,39,4,1),(40,93958,35,24,1),(41,94028,36,25,1),(42,12157,35,24,1),(43,12231,39,26,1),(44,17386,35,24,1),(45,17391,35,24,1),(46,69377,61,27,1),(47,74415,63,28,1),(48,69380,62,29,1),(49,69387,64,2,1),(50,69393,66,30,1),(51,69389,65,31,1),(52,71936,57,32,1),(53,70171,55,33,1),(54,13915,60,34,1),(55,69402,61,35,1),(56,69405,63,36,1),(57,69403,62,37,1),(58,69407,64,30,1),(59,69409,66,38,1),(60,69408,65,39,1),(61,78263,62,37,1),(62,78270,65,40,1),(63,78278,66,38,1),(64,74282,73,94,1),(65,74284,73,41,1),(66,72486,74,42,1),(67,74465,75,43,1),(68,74289,76,44,1),(69,74467,68,41,1),(70,93069,67,45,1),(71,74290,73,46,1),(72,74291,72,58,1),(73,74293,74,71,1),(74,74470,75,69,1),(75,74298,76,95,1),(76,78280,82,47,1),(77,78288,83,28,1),(78,78485,84,48,1),(79,78489,85,49,1),(80,78490,94,50,1),(81,78492,77,51,1),(82,78493,79,42,1),(83,85106,93,52,1),(84,78494,82,53,1),(85,78495,83,49,1),(86,78496,84,54,1),(87,78497,85,48,1),(88,11710,101,55,1),(89,84264,92,13,1),(90,84183,90,56,1),(91,81357,86,45,1),(92,85103,87,56,1),(93,81219,88,49,1),(94,93071,99,57,1),(95,85103,87,48,1),(96,84184,91,58,1),(97,89689,89,59,1),(98,74381,63,28,1),(99,69489,105,60,1),(100,69482,61,61,1),(101,69486,64,26,1),(102,69487,71,62,1),(103,69485,66,38,1),(104,71928,57,63,1),(105,69495,63,64,1),(106,74691,105,60,1),(107,69490,61,65,1),(108,69493,64,66,1),(109,69492,66,67,1),(110,70139,59,68,1),(111,74258,108,69,1),(112,74265,110,70,1),(113,74370,65,31,1),(114,74269,93,71,1),(115,89696,68,72,1),(116,78950,108,73,1),(117,74272,109,74,1),(118,74275,110,70,1),(119,74414,65,74,1),(120,74280,93,71,1),(121,78286,117,70,1),(122,78297,118,43,1),(123,78300,96,75,1),(124,78710,120,72,1),(125,78714,121,75,1),(126,84401,99,64,1),(127,81267,117,58,1),(128,81270,96,75,1),(129,81271,120,74,1),(130,81272,121,75,1),(131,84198,91,64,1),(132,81275,97,64,1),(133,84200,90,74,1),(134,94404,128,76,1),(135,87359,91,55,1),(136,87363,122,43,1),(137,69425,60,77,1),(138,69412,61,33,1),(139,69413,107,78,1),(140,69416,64,26,1),(141,69421,71,26,1),(142,69429,63,79,1),(143,70151,56,80,1),(144,78954,57,81,1),(145,69479,60,82,1),(146,69475,61,65,1),(147,69476,107,63,1),(148,69477,64,83,1),(149,69478,71,66,1),(150,69486,63,78,1),(151,70156,56,80,1),(152,70159,107,84,1),(153,78779,71,85,1),(154,78952,63,38,1),(155,78953,64,86,1),(156,74235,133,87,1),(157,74236,134,60,1),(158,74237,136,38,1),(159,74238,75,72,1),(160,74242,135,85,1),(161,74472,69,55,1),(162,74244,133,55,1),(163,74246,134,45,1),(164,74249,136,54,1),(165,74250,35,43,1),(166,16876,135,88,1),(167,87452,135,22,1),(168,78783,83,89,1),(169,78785,145,89,1),(170,78925,142,89,1),(171,78927,144,90,1),(172,78928,143,90,1),(173,78929,83,91,1),(174,78930,145,41,1),(175,78933,142,85,1),(176,78935,144,90,1),(177,78938,143,92,1),(178,84191,91,78,1),(179,81252,78,45,1),(180,84196,90,89,1),(181,85292,147,93,1),(182,84194,91,86,1),(183,81255,149,86,1);
/*!40000 ALTER TABLE `academicofferings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `academicpersonnel`
--

DROP TABLE IF EXISTS `academicpersonnel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academicpersonnel` (
  `idAcademicPersonnel` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `paternalSurname` varchar(150) NOT NULL,
  `maternalSurname` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `emailAddress` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `idContractType` int DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idAcademicPersonnel`,`name`,`paternalSurname`,`maternalSurname`),
  KEY `FK_AcademicPersonnel_ContractType_idx` (`idContractType`),
  KEY `FK_AcademicPersonnel_User_idx` (`username`),
  CONSTRAINT `FK_AcademicPersonnel_ContractType` FOREIGN KEY (`idContractType`) REFERENCES `contracttype` (`idContractType`),
  CONSTRAINT `FK_AcademicPersonnel_User` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academicpersonnel`
--

LOCK TABLES `academicpersonnel` WRITE;
/*!40000 ALTER TABLE `academicpersonnel` DISABLE KEYS */;
INSERT INTO `academicpersonnel` VALUES (1,'José Fabián','Muñóz','Portilla','fmunoz@uv.mx',1,NULL),(2,'José Juan','Muñóz','León','juanmunoz@uv.mx',1,NULL),(3,'Miguel','Alonso','López','malonso@uv.mx',1,NULL),(4,'Víctor Manuel','Méndez','Sánchez','vmendez@uv.mx',1,NULL),(5,'Javier Ignacio','Romo','Encinas','jromo@uv.mx',2,NULL),(6,'Omar','Hernández','Báez','amhernandez@uv.mx',2,NULL),(7,'Minerva','Reyes','Félix','minreyes@uv.mx',2,NULL),(8,'Ángel','Miñón','Pérez','aminon@uv.mx',2,NULL),(9,'Eduardo','Díaz','Camacho','eddiaz@uv.mx',2,NULL),(10,'María Yesenia','Zavaleta','Sánchez','yzavaleta@uv.mx',2,NULL),(11,'Eliseo','Gabriel','Argüelles','egabriel@uv.mx',2,NULL),(12,'Mario Miguel','Ojeda','Ramírez','mojeda@uv.mx',1,NULL),(13,'Lorena','Alonso','Ramírez','lalonso@uv.mx',2,NULL),(14,'Judith Guadalupe','Montero','Mora','jmontero@uv.mx',1,NULL),(15,'Briseida','Jiménez','Velázquez','brjimenez@uv.mx',2,NULL),(16,'Jesús','Hernández','Suárez','jeshernandez@uv.mx',1,NULL),(17,'Juan','Ruíz','Ramírez','jruiz@uv.mx',1,NULL),(18,'Claudio Rafael','Castro','López','ccastro@uv.mx',1,NULL),(19,'Julia Aurora','Montano','Rivas','julmontano@uv.mx',1,NULL),(20,'Zoylo','Morales','Romero','zmorales@uv.mx',1,NULL),(21,'Cecilia','Cruz','López','ceccruz@uv.mx',2,NULL),(22,'Niels','Martínez','Guevara','niemartinez@uv.mx',2,NULL),(23,'Jazmín Josefina','García','Méndez','jazgarcia@uv.mx',2,NULL),(24,'Julián Felipe','Díaz','Camacho','judiaz@uv.mx',1,NULL),(25,'María de Lourdes','Velasco','Vázquez','lovelasco@uv.mx',1,NULL),(26,'Maribel','Carmona','García','maribelcarmona@uv.mx',2,NULL),(27,'Dolores','Carrillo','Coto','dcarrillo@uv.mx',2,NULL),(28,'María de los Ángeles','Arenas','Valdés','aarenas@uv.mx',1,'aarenas'),(29,'Erika','Meneses','Rico','ermeneses@uv.mx',2,NULL),(30,'Víctor Manuel','Tlapa','Carrera','vtlapa@uv.mx',2,NULL),(31,'Aquiles','Orduña','González','aorduna@uv.mx',2,NULL),(32,'Christian','Pérez','Salazar','chperez@uv.mx',1,NULL),(33,'Olga Irene','Zitácuaro','Ameca','ozitacuaro@uv.mx',2,NULL),(34,'Juan Carlos','García','Rodríguez','juangarcia06@uv.mx',2,NULL),(35,'Edna Lilliam','Mendoza','Solís','edmendoza@uv.mx',2,NULL),(36,'Adriana','Cervantes','Castillo','adcervantes@uv.mx',2,NULL),(37,'Virgina','Lagunes','Barradas','vlagunes@uv.mx',2,NULL),(38,'Ulises','Marinero','Aguilar','umarinero@uv.mx',2,NULL),(39,'Irma Elizabeth','Romero','Arrioja','iromero@uv.mx',2,NULL),(40,'Alba Cristina','Garza','Cayetano','agarza@uv.mx',2,NULL),(41,'Itzel Alessadra','Reyes','Flores','itreyes@uv.mx',2,NULL),(42,'Ramón','Gómez','Romero','ramongomez@uv.mx',2,NULL),(43,'Martha Elizabet','Domínguez','Bárcenas','eldominguez@uv.mx',1,NULL),(44,'Esmeralda Yamileth','Hernández','González','esmhernandez@uv.mx',2,NULL),(45,'María de Lourdes','Hernández','Rodríguez','lourhernandez@uv.mx',2,NULL),(46,'Óscar','Alonso','Ramírez','oalonso@uv.mx',2,NULL),(47,'Elizabeth','Murrieta','Sangabriel','elmurrieta@uv.mx',2,NULL),(48,'Saúl','Domínguez','Isidro','sauldominguez@uv.mx',2,NULL),(49,'Mario Alberto','Hernández','Pérez','mariohernandez02@uv.mx',2,NULL),(50,'David','Martínez','Galicia','dmartinez@uv.mx',2,NULL),(51,'Ana Luz','Polo','Estrella','apolo@uv.mx',1,NULL),(52,'Max William','Millán','Martínez','mmillan@uv.mx',2,NULL),(53,'María','Angélica','Cerdán','acerdan@uv.mx',2,NULL),(54,'Juan Luis','López','Herrera','julopez@uv.mx',2,NULL),(55,'María Dolores','Vargas','Cerdán','dvargas@uv.mx',1,NULL),(56,'Ángel Juan','Sánchez','García','angesanchez@uv.mx',1,NULL),(57,'Urbano Francisco','Ortega','Rivera','uortega@uv.mx',2,NULL),(58,'Jorge Octavio','Ocharán','Hernández','jocharan@uv.mx',1,NULL),(59,'Donají','Callejas','del Callejo','ddelcallejo@uv.mx',1,NULL),(60,'Gustavo Manuel','Balderas','Rosas','gbalderas@uv.mx',2,NULL),(61,'Arminda','Barradas','Sánchez','armbarradas@uv.mx',2,NULL),(62,'Aureliano','Aguilar','Bonilla','auraguilar@uv.mx',2,NULL),(63,'Willian','Zárate','Navarro','wzarate@uv.mx',2,NULL),(64,'Héctor Xavier','Limón','Riaño','hlimon@uv.mx',1,NULL),(65,'Cruz Enrique','Ortega','Ramírez','cortega@uv.mx',2,NULL),(66,'Diana Elizabeth','Valderrábano','Pedraza','dvalderrabano@uv.mx',1,NULL),(67,'Everardo','García','Menie','evgarcia@uv.mx',1,NULL),(68,'Carlos','Illescas','Sánchez','cillescas@uv.mx',2,NULL),(69,'Angélica','Pérez','Hernández','angelperez@uv.mx',1,NULL),(70,'Jesús Roberto','Méndez','Ortiz','jmendez@uv.mx',1,NULL),(71,'Juan Carlos','Pérez','Arriaga','juaperez@uv.mx',1,NULL),(72,'Carlos Alberto','Ochoa','Rivera','cochoa@uv.mx',2,NULL),(73,'Javier','Sánchez','Acosta','javsanchez@uv.mx',2,NULL),(74,'Alberto Jair','Cruz','Landa','albecruz@uv.mx',2,NULL),(75,'Gerardo','Contreras','Vega','gcontreras@uv.mx',1,NULL),(76,'Héctor','Bonola','Virues','hbonola@uv.mx',2,NULL),(77,'María','García','Araujo','marigarcia@uv.mx',2,NULL),(78,'Luis Gerardo','Montané','Jiménez','lmontane@uv.mx',2,NULL),(79,'María del Carmen','Mezura','Godoy','cmezura@uv.mx',1,NULL),(80,'María Silva','García','Ramírez','sgarcia@uv.mx',2,NULL),(81,'Verónica Elizabeth','Orozco','Ríoz','vorozco@uv.mx',2,NULL),(82,'Paola del Carmen','Cordero','Román','pcordero@uv.mx',2,NULL),(83,'Atanasio Hermilo','Delgado','Ramírez','hdelgado@uv.mx',1,NULL),(84,'María Eugenia','Barradas','García','mbarradas@uv.mx',2,NULL),(85,'Candy Obdulia','Sosa','Jiménez','cansosa@uv.mx',2,NULL),(86,'Juana Elisa','Escalante','Vega','jescalante@uv.mx',1,NULL),(87,'Edgard Iván','Benítez','Guerrero','edbenitez@uv.mx',1,NULL),(88,'Virginia Angélica','García','Vega','angegarcia@uv.mx',1,NULL),(89,'Fredy','Castañeda','Sánchez','fcastaneda@uv.mx',1,NULL),(90,'José Rafael','Rojano','Cáceres','rrojano@uv.mx',1,NULL),(91,'José Guillermo','Hernández','Calderón','guillermohernandez02@uv.mx',1,NULL),(92,'Ramón David','Sarmiento','Cervantes','rsarmiento@uv.mx',2,NULL),(93,'Margarita Edith','Canal','Martínez','mcanal@uv.mx',2,NULL),(94,'Jenny Betsabé','Vázquez','Aguirre','jaguirre@uv.mx',2,NULL),(95,'María de los Ángeles','Navarro','Guerrero','lonarro@uv.mx',2,NULL);
/*!40000 ALTER TABLE `academicpersonnel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `academicproblem`
--

DROP TABLE IF EXISTS `academicproblem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academicproblem` (
  `idAcademicProblem` int NOT NULL AUTO_INCREMENT,
  `title` varchar(150) NOT NULL,
  `description` text NOT NULL,
  `numberOfStudents` int NOT NULL,
  `idAcademicOfferings` int NOT NULL,
  `idAcademicTutorshipReport` int NOT NULL,
  `idAcademicProblemFollowUp` int DEFAULT NULL,
  PRIMARY KEY (`idAcademicProblem`),
  KEY `FK_AcademicProblem_AcademicTutorshipReport_idx` (`idAcademicTutorshipReport`),
  KEY `FK_AcademicProblem_AcademicProblemFollowUp_idx` (`idAcademicProblemFollowUp`),
  KEY `FK_AcademicProblem_AcademicOfferings_idx` (`idAcademicOfferings`),
  CONSTRAINT `FK_AcademicProblem_AcademicOfferings` FOREIGN KEY (`idAcademicOfferings`) REFERENCES `academicofferings` (`idAcademicOfferings`),
  CONSTRAINT `FK_AcademicProblem_AcademicProblemFollowUp` FOREIGN KEY (`idAcademicProblemFollowUp`) REFERENCES `academicproblemfollowup` (`idAcademicProblemFollowUp`),
  CONSTRAINT `FK_AcademicProblem_AcademicTutorshipReport` FOREIGN KEY (`idAcademicTutorshipReport`) REFERENCES `academictutorshipreport` (`idAcademicTutorshipReport`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academicproblem`
--

LOCK TABLES `academicproblem` WRITE;
/*!40000 ALTER TABLE `academicproblem` DISABLE KEYS */;
/*!40000 ALTER TABLE `academicproblem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `academicproblemfollowup`
--

DROP TABLE IF EXISTS `academicproblemfollowup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academicproblemfollowup` (
  `idAcademicProblemFollowUp` int NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`idAcademicProblemFollowUp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academicproblemfollowup`
--

LOCK TABLES `academicproblemfollowup` WRITE;
/*!40000 ALTER TABLE `academicproblemfollowup` DISABLE KEYS */;
/*!40000 ALTER TABLE `academicproblemfollowup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `academictutorship`
--

DROP TABLE IF EXISTS `academictutorship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academictutorship` (
  `idAcademicTutorship` int NOT NULL AUTO_INCREMENT,
  `idEducationalProgram` int NOT NULL,
  `idAcademicTutorshipSession` int NOT NULL,
  PRIMARY KEY (`idAcademicTutorship`),
  KEY `FK_AcademicTutorship_AcademicTutorshipSession_idx` (`idAcademicTutorshipSession`),
  KEY `FK_AcademicTutorship_EducationalProgram_idx` (`idEducationalProgram`),
  CONSTRAINT `FK_AcademicTutorship_AcademicTutorshipSession` FOREIGN KEY (`idAcademicTutorshipSession`) REFERENCES `academictutorshipsession` (`idAcademicTutorshipSession`),
  CONSTRAINT `FK_AcademicTutorship_EducationalProgram` FOREIGN KEY (`idEducationalProgram`) REFERENCES `educationalprogram` (`idEducationalProgram`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academictutorship`
--

LOCK TABLES `academictutorship` WRITE;
/*!40000 ALTER TABLE `academictutorship` DISABLE KEYS */;
/*!40000 ALTER TABLE `academictutorship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `academictutorshipreport`
--

DROP TABLE IF EXISTS `academictutorshipreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academictutorshipreport` (
  `idAcademicTutorshipReport` int NOT NULL AUTO_INCREMENT,
  `generalComment` text,
  `numberOfStudentsAttending` int NOT NULL,
  `numberOfStudentsAtRisk` int NOT NULL,
  `idAcademicPersonnel` int NOT NULL,
  `idAcademicTutorship` int NOT NULL,
  PRIMARY KEY (`idAcademicTutorshipReport`),
  KEY `FK_AcademicTutorshipReport_AcademicTutorship_idx` (`idAcademicTutorship`),
  KEY `FK_AcademicTutorshipReport_AcademicPersonnel_idx` (`idAcademicPersonnel`),
  CONSTRAINT `FK_AcademicTutorshipReport_AcademicPersonnel` FOREIGN KEY (`idAcademicPersonnel`) REFERENCES `academicpersonnel` (`idAcademicPersonnel`),
  CONSTRAINT `FK_AcademicTutorshipReport_AcademicTutorship` FOREIGN KEY (`idAcademicTutorship`) REFERENCES `academictutorship` (`idAcademicTutorship`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academictutorshipreport`
--

LOCK TABLES `academictutorshipreport` WRITE;
/*!40000 ALTER TABLE `academictutorshipreport` DISABLE KEYS */;
/*!40000 ALTER TABLE `academictutorshipreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `academictutorshipreportstudent`
--

DROP TABLE IF EXISTS `academictutorshipreportstudent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academictutorshipreportstudent` (
  `idAcademicTutorshipReportStudent` int NOT NULL AUTO_INCREMENT,
  `attendedBy` bit(1) NOT NULL,
  `inRisk` bit(1) NOT NULL,
  `idAcademicTutorshipReport` int NOT NULL,
  `registrationNumber` varchar(10) NOT NULL,
  PRIMARY KEY (`idAcademicTutorshipReportStudent`),
  KEY `FK_AcademicTutorshipReportStudent_AcademicTutorshipReport_idx` (`idAcademicTutorshipReport`),
  KEY `FK_AcademicTutorshipReportStudent_Student_idx` (`registrationNumber`),
  CONSTRAINT `FK_AcademicTutorshipReportStudent_AcademicTutorshipReport` FOREIGN KEY (`idAcademicTutorshipReport`) REFERENCES `academictutorshipreport` (`idAcademicTutorshipReport`),
  CONSTRAINT `FK_AcademicTutorshipReportStudent_Student` FOREIGN KEY (`registrationNumber`) REFERENCES `student` (`registrationNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academictutorshipreportstudent`
--

LOCK TABLES `academictutorshipreportstudent` WRITE;
/*!40000 ALTER TABLE `academictutorshipreportstudent` DISABLE KEYS */;
/*!40000 ALTER TABLE `academictutorshipreportstudent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `academictutorshipsession`
--

DROP TABLE IF EXISTS `academictutorshipsession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academictutorshipsession` (
  `idAcademicTutorshipSession` int NOT NULL AUTO_INCREMENT,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `closingDateReportSubmission` date NOT NULL,
  `sessionNumber` int NOT NULL,
  `idSchoolPeriod` int NOT NULL,
  PRIMARY KEY (`idAcademicTutorshipSession`),
  KEY `FK_AcademicTutorshipSession_SchoolPeriod_idx` (`idSchoolPeriod`),
  CONSTRAINT `FK_AcademicTutorshipSession_SchoolPeriod` FOREIGN KEY (`idSchoolPeriod`) REFERENCES `schoolperiod` (`idSchoolPeriod`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academictutorshipsession`
--

LOCK TABLES `academictutorshipsession` WRITE;
/*!40000 ALTER TABLE `academictutorshipsession` DISABLE KEYS */;
INSERT INTO `academictutorshipsession` VALUES (1,'2023-02-27','2023-03-27','2023-04-10',1,1),(2,'2023-04-10','2023-05-10','2023-05-17',2,1),(3,'2023-05-22','2023-06-22','2023-06-29',3,1);
/*!40000 ALTER TABLE `academictutorshipsession` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contracttype`
--

DROP TABLE IF EXISTS `contracttype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contracttype` (
  `idContractType` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`idContractType`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contracttype`
--

LOCK TABLES `contracttype` WRITE;
/*!40000 ALTER TABLE `contracttype` DISABLE KEYS */;
INSERT INTO `contracttype` VALUES (1,'Tiempo completo'),(2,'Por asignatura');
/*!40000 ALTER TABLE `contracttype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `educationalexperience`
--

DROP TABLE IF EXISTS `educationalexperience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `educationalexperience` (
  `idEducationalExperience` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  PRIMARY KEY (`idEducationalExperience`)
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `educationalexperience`
--

LOCK TABLES `educationalexperience` WRITE;
/*!40000 ALTER TABLE `educationalexperience` DISABLE KEYS */;
INSERT INTO `educationalexperience` VALUES (1,'Lectura y Escritura de Textos Académicos'),(2,'Lengua I'),(3,'Literacidad Digital'),(4,'Álgebra Lineal Aplicada a la Estadística I'),(5,'Cálculo Aplicado a la Estadística I'),(6,'Metodología Estadística para la Investigación'),(7,'Estadística Descriptiva y Exploratoria'),(8,'Pensamiento Crítico para la Solución de Problemas'),(9,'Lengua II'),(10,'Introducción a la Programación Estadística'),(11,'Álgebra Lineal Aplicada a la Estadística II'),(12,'Cálculo Aplicado a la Estadística II'),(13,'Pensamiento y Cultura Estadística'),(14,'Muestreo'),(15,'Modelos de Regresión Lineal y No Lineal'),(16,'Programación Estadística'),(17,'Probabilidad I'),(18,'Instrumento de Capacitación y Análisis de Datos'),(19,'Seminario de Aplicaciones Estadísticas'),(20,'Estadística Multivariada'),(21,'Modelos Lineales Generalizados'),(22,'Manejadores de Bases de Datos'),(23,'Probabilidad II'),(24,'Diseño y Análisis de Experimentos'),(25,'Modelos Espacio-Temporales'),(26,'Modelos de Regresión No Paramétrica y Semiparamétrica'),(27,'Minería de Datos y Aprendizaje Máquina'),(28,'Teoría Estadística'),(29,'Consultoría Estadística'),(30,'Análisis Cuantitativo para Datos Cualitativos'),(31,'Control Estadístico de la Calidad'),(32,'Big Data'),(33,'Estadística Bayesiana'),(34,'Práctica de Consultoría Estadística'),(35,'Servicio Social I'),(36,'Experiencia Recepcional I'),(37,'Bootstrap y Pruebas de Permutación'),(38,'Servicio Social II'),(39,'Experiencia Recepcional II'),(40,'Acreditación del Idioma Inglés'),(41,'Cálculo Avanzado Aplicado a la Estadística'),(42,'Estadística Espacial'),(43,'Visualización de Datos'),(44,'Bioestadística'),(45,'Educación Estadística'),(46,'Estadística Industrial'),(47,'Vinculación a Través de la Consultoría Estadística'),(48,'Álgebra Lineal Avanzada Aplicada a la Estadística'),(49,'Geoestadística'),(50,'Introducción a la Minería de Textos'),(51,'Estadística en la Salud'),(52,'Didáctica de la Estadística'),(53,'Estadística Empresarial'),(54,'Tópicos de Estadística'),(55,'Inglés I'),(56,'Computación Básica'),(57,'Introducción a la Programación'),(58,'Fundamentos de Matemáticas'),(59,'Habilidades del Pensamiento Crítico y Creativo'),(60,'Lectura y Redacción a Través del Análisis del Mundo Contemporáneo'),(61,'Inglés II'),(62,'Laboratorio de Resolución de Problemas'),(63,'Programación'),(64,'Álgebra Lineal para Computación'),(65,'Habilidades de Comunicación'),(66,'Matemáticas Discretas'),(67,'Requerimientos de Software'),(68,'Estructuras de Datos'),(69,'Bases de Datos'),(70,'Sistemas Operativos'),(71,'Probabilidad y Estadística para Computación'),(72,'Principios de Diseño de Software'),(73,'Paradigmas de Programación'),(74,'Principios de Construcción de Software'),(75,'Redes'),(76,'Procesos para Ingeniería de Software'),(77,'Diseño de Software'),(78,'Administración de Proyectos de Software'),(79,'Tecnologías para la Construcción de Software'),(80,'Prueba de Software'),(81,'Derecho de las Tecnologías de Información y Comunicación'),(82,'Verificación y Validación de Software'),(83,'Desarrollo de Software'),(84,'Desarrollo de Aplicaciones'),(85,'Desarrollo de Sistemas en Red'),(86,'Proyecto Guiado'),(87,'Prácticas de Ingeniería de Software'),(88,'Desarrollo de Sistemas Web'),(89,'Economía para Toma de Decisiones'),(90,'Servicio Social'),(91,'Experiencia Recepcional'),(92,'Bases de Datos No Convencionales'),(93,'Administración de Bases de Datos'),(94,'Inteligencia Artificial Aplicada a la Ingeniería de Software'),(95,'Medición de Software'),(96,'Administración Avanzada de Servicios'),(97,'Programación Segura'),(98,'Sistemas Operativos Móviles'),(99,'Pruebas de Penetración'),(100,'Programación Multinúcleo'),(101,'Diseño de Interfaces de Usuario'),(102,'Proyectos de Software'),(103,'Reingeniería de Software'),(104,'Arquitectura de Dispositivos de Red'),(105,'Principios de Telecomunicaciones'),(106,'Programación de Sistemas'),(107,'Metodología de la Investigación'),(108,'Enrutamiento Básico'),(109,'Arquitecturas en Red'),(110,'Mantenimiento de Equipo de Cómputo'),(111,'Enrutamiento Avanzado'),(112,'Ética y Normatividades en Comunicaciones y Redes'),(113,'Administración de Servidores'),(114,'Análisis de Protocolos'),(115,'Programación en la Administración de Redes'),(116,'Sistemas Operativos Aplicados'),(117,'Redes Inalámbricas'),(118,'Acceso WAN'),(120,'Conmutación con Redes LAN'),(121,'Seguridad'),(122,'Prácticas de Redes'),(123,'Administración de Proyectos de Red'),(124,'Cibercrimen y Herramientas Digital Forenses'),(125,'Recolección y Preservación de Evidencias'),(126,'Seguridad Móvil'),(127,'Análisis Forense para Sistemas de Escritorio'),(128,'Servicios de Virtualización'),(129,'Cómputo Sustentable'),(130,'Criptografía'),(131,'Tecnologías de Información para la Innovación'),(132,'Organización de Computadoras'),(133,'Bases de Datos Avanzadas'),(134,'Ingeniería de Software'),(135,'Sistemas Inteligentes'),(136,'Programación Avanzada'),(137,'Sistema Web'),(138,'Metodología de Desarrollo'),(139,'Habilidades Directivas'),(140,'Interacción Humano Computadora'),(141,'Ética y Legislación Informática'),(142,'Gestión de Proyectos de Tecnologías de Información'),(143,'Tecnologías Web'),(144,'Tecnologías para la Integración de Soluciones'),(145,'Desarrollo Móvil'),(146,'Integración de Soluciones'),(147,'Proyecto Integrador'),(148,'Servicio de Virtualización'),(149,'Graficación'),(150,'Diseño de Interacciones'),(151,'Ingeniería de Software Emergente'),(152,'Interfaces de Usuario Avanzadas');
/*!40000 ALTER TABLE `educationalexperience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `educationalprogram`
--

DROP TABLE IF EXISTS `educationalprogram`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `educationalprogram` (
  `idEducationalProgram` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  PRIMARY KEY (`idEducationalProgram`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `educationalprogram`
--

LOCK TABLES `educationalprogram` WRITE;
/*!40000 ALTER TABLE `educationalprogram` DISABLE KEYS */;
INSERT INTO `educationalprogram` VALUES (1,'Estadística'),(2,'Ingeniería de Software'),(3,'Redes y Servicios de Cómputo'),(4,'Tecnologías Computacionales');
/*!40000 ALTER TABLE `educationalprogram` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `educationalprogramrole`
--

DROP TABLE IF EXISTS `educationalprogramrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `educationalprogramrole` (
  `idEducationalProgramRole` int NOT NULL AUTO_INCREMENT,
  `idEducationalProgram` int NOT NULL,
  `idRole` int NOT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`idEducationalProgramRole`,`username`,`idEducationalProgram`,`idRole`),
  KEY `FK_EducationalProgramRole_User_idx` (`username`),
  KEY `FK_EducationalProgramRole_EducationalProgram_idx` (`idEducationalProgram`),
  KEY `FK_EducationalProgramRole_Role_idx` (`idRole`),
  CONSTRAINT `FK_EducationalProgramRole_EducationalProgram` FOREIGN KEY (`idEducationalProgram`) REFERENCES `educationalprogram` (`idEducationalProgram`),
  CONSTRAINT `FK_EducationalProgramRole_Role` FOREIGN KEY (`idRole`) REFERENCES `role` (`idRole`),
  CONSTRAINT `FK_EducationalProgramRole_User` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `educationalprogramrole`
--

LOCK TABLES `educationalprogramrole` WRITE;
/*!40000 ALTER TABLE `educationalprogramrole` DISABLE KEYS */;
INSERT INTO `educationalprogramrole` VALUES (1,2,1,'aarenas'),(2,2,3,'aarenas'),(3,3,3,'aarenas');
/*!40000 ALTER TABLE `educationalprogramrole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `idRole` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`idRole`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Coordinador de tutorías académicas'),(2,'Jefe de carrera'),(3,'Tutor académico'),(4,'Administrador');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schoolperiod`
--

DROP TABLE IF EXISTS `schoolperiod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schoolperiod` (
  `idSchoolPeriod` int NOT NULL AUTO_INCREMENT,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  PRIMARY KEY (`idSchoolPeriod`,`startDate`,`endDate`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schoolperiod`
--

LOCK TABLES `schoolperiod` WRITE;
/*!40000 ALTER TABLE `schoolperiod` DISABLE KEYS */;
INSERT INTO `schoolperiod` VALUES (1,'2023-02-07','2023-06-02');
/*!40000 ALTER TABLE `schoolperiod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `registrationNumber` varchar(10) NOT NULL,
  `name` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `paternalSurname` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `maternalSurname` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `emailAddress` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `idEducationalProgram` int NOT NULL,
  `idAcademicPersonnel` int NOT NULL,
  PRIMARY KEY (`registrationNumber`),
  KEY `FK_Student_EducationalProgram_idx` (`idEducationalProgram`),
  KEY `FK_Student_AcademicPersonnel_idx` (`idAcademicPersonnel`),
  CONSTRAINT `FK_Student_AcademicPersonnel` FOREIGN KEY (`idAcademicPersonnel`) REFERENCES `academicpersonnel` (`idAcademicPersonnel`),
  CONSTRAINT `FK_Student_EducationalProgram` FOREIGN KEY (`idEducationalProgram`) REFERENCES `educationalprogram` (`idEducationalProgram`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('S19014012','María José','Torres','Igartua','zs19014012@estudiantes.uv.mx',2,28),('S19014017','Gustavo','Flores','Romero','zs19014017@estudiantes.uv.mx',2,28),('S19014045','David Alexander','Mijangos','Paredes','zs19014045@estudiantes.uv.mx',2,28),('S20015699','Armando Omar','Obando','Muñóz','zs20015699@estudiantes.uv.mx',2,28),('S20015700','Jonatan','Alarcón','Alarcón','zs20015700@estudiantes.uv.mx',2,28),('S20015717','Obet Jair','Hernández','González','zs20015717@estudiantes.uv.mx',2,28),('S20015718','Gerardo','Herrera','Solano','zs20015718@estudiantes.uv.mx',2,28),('S20015742','Mario Alberto','Jiménez','Jiménez','zs20015742@estudiantes.uv.mx',2,28),('S20015744','Franz Jesús','Rivera','Alcántara','zs20015744@estudiantes.uv.mx',2,28),('S20015757','Ricardo','Noguera','Martínez','zs20015757@estudiantes.uv.mx',2,28),('S21013852','Ares Judda','Rivera','Soto','zs21013852@estudiantes.uv.mx',2,28),('S21013872','Luis Alexis','Rodríguez','Medina','zs21013872@estudiantes.uv.mx',2,28),('S21013877','Sergio de Jesús Marlon','Hernández','Pérez','zs21013877@estudiantes.uv.mx',2,28),('S21013884','Omar Dylan','Segura','Platas','zs21013884@estudiantes.uv.mx',2,28),('S21013898','Martin Emmanuel','Cruz','Carmona','zs21013898@estudiantes.uv.mx',2,28),('S21013906','Xavier Arian','Olivares','Sánchez','zs21013906@estudiantes.uv.mx',2,28),('S21021432','Ferdy Alexis','Reyes','Viveros','zs21021432@estudiantes.uv.mx',2,28);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syllabus`
--

DROP TABLE IF EXISTS `syllabus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `syllabus` (
  `idSyllabus` int NOT NULL AUTO_INCREMENT,
  `idEducationalProgram` int NOT NULL,
  `idEducationalExperience` int NOT NULL,
  PRIMARY KEY (`idSyllabus`,`idEducationalProgram`,`idEducationalExperience`),
  KEY `FK_Syllabus_EducationalExperience_idx` (`idEducationalExperience`),
  KEY `FK_Syllabus_EducationalProgram_idx` (`idEducationalProgram`),
  CONSTRAINT `FK_Syllabus_EducationalExperience` FOREIGN KEY (`idEducationalExperience`) REFERENCES `educationalexperience` (`idEducationalExperience`),
  CONSTRAINT `FK_Syllabus_EducationalProgram` FOREIGN KEY (`idEducationalProgram`) REFERENCES `educationalprogram` (`idEducationalProgram`)
) ENGINE=InnoDB AUTO_INCREMENT=204 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syllabus`
--

LOCK TABLES `syllabus` WRITE;
/*!40000 ALTER TABLE `syllabus` DISABLE KEYS */;
INSERT INTO `syllabus` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,7),(8,1,8),(9,1,9),(10,1,10),(11,1,11),(12,1,12),(13,1,13),(14,1,14),(15,1,15),(16,1,16),(17,1,17),(18,1,18),(19,1,19),(20,1,20),(21,1,21),(22,1,22),(23,1,23),(24,1,24),(25,1,25),(26,1,26),(27,1,27),(28,1,28),(29,1,29),(30,1,30),(31,1,31),(32,1,32),(33,1,33),(34,1,34),(35,1,35),(36,1,36),(37,1,37),(38,1,38),(39,1,39),(40,1,40),(55,2,40),(105,3,40),(157,4,40),(41,1,41),(42,1,42),(43,1,43),(44,1,44),(45,1,45),(46,1,46),(47,1,47),(48,1,48),(49,1,49),(50,1,50),(51,1,51),(52,1,52),(53,1,53),(54,1,54),(56,2,55),(106,3,55),(158,4,55),(57,2,56),(107,3,56),(159,4,56),(58,2,57),(108,3,57),(160,4,57),(59,2,58),(109,3,58),(161,4,58),(60,2,59),(110,3,59),(162,4,59),(61,2,60),(111,3,60),(163,4,60),(62,2,61),(112,3,61),(164,4,61),(63,2,62),(64,2,63),(113,3,63),(165,4,63),(65,2,64),(114,3,64),(166,4,64),(66,2,65),(115,3,65),(67,2,66),(116,3,66),(167,4,66),(68,2,67),(69,2,68),(117,3,68),(168,4,68),(70,2,69),(118,3,69),(169,4,69),(71,2,70),(119,3,70),(170,4,70),(72,2,71),(120,3,71),(171,4,71),(73,2,72),(74,2,73),(75,2,74),(76,2,75),(121,3,75),(172,4,75),(77,2,76),(78,2,77),(79,2,78),(173,4,78),(80,2,79),(81,2,80),(82,2,81),(83,2,82),(84,2,83),(174,4,83),(85,2,84),(86,2,85),(87,2,86),(88,2,87),(89,2,88),(122,3,88),(90,2,89),(91,2,90),(123,3,90),(175,4,90),(92,2,91),(124,3,91),(176,4,91),(93,2,92),(177,4,92),(94,2,93),(125,3,93),(178,4,93),(95,2,94),(96,2,95),(97,2,96),(126,3,96),(98,2,97),(127,3,97),(99,2,98),(128,3,98),(100,2,99),(129,3,99),(101,2,100),(102,2,101),(103,2,102),(104,2,103),(130,3,104),(131,3,105),(132,3,106),(133,3,107),(179,4,107),(134,3,108),(135,3,109),(136,3,110),(137,3,111),(138,3,112),(180,4,112),(139,3,113),(140,3,114),(141,3,115),(142,3,116),(143,3,117),(144,3,118),(146,3,120),(147,3,121),(181,4,121),(148,3,122),(149,3,123),(150,3,124),(151,3,125),(152,3,126),(153,3,127),(154,3,128),(155,3,129),(156,3,130),(182,4,131),(183,4,132),(184,4,133),(185,4,134),(186,4,135),(187,4,136),(188,4,137),(189,4,138),(190,4,139),(191,4,140),(192,4,141),(193,4,142),(194,4,143),(195,4,144),(196,4,145),(197,4,146),(198,4,147),(199,4,148),(200,4,149),(201,4,150),(202,4,151),(203,4,152);
/*!40000 ALTER TABLE `syllabus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `username` varchar(50) NOT NULL,
  `password` varchar(450) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('aarenas','MEPIxBcHCQgt8Um3LJ0euc5Wy9ctDB82/RCKiJRe0V4=');
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

-- Dump completed on 2023-03-03 19:11:00
