-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: solucoes_engenharia_cf
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `alerta`
--

DROP TABLE IF EXISTS `alerta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alerta` (
  `id_alerta` int NOT NULL AUTO_INCREMENT,
  `tipo` enum('Documento','Etapa','Auditoria','Norma','NaoConformidade') NOT NULL,
  `id_referencia` int NOT NULL,
  `mensagem` text NOT NULL,
  `data_geracao` datetime DEFAULT CURRENT_TIMESTAMP,
  `data_vencimento` datetime NOT NULL,
  `status` enum('Pendente','Enviado','Resolvido') DEFAULT 'Pendente',
  `id_usuario_destino` int NOT NULL,
  PRIMARY KEY (`id_alerta`),
  KEY `id_usuario_destino` (`id_usuario_destino`),
  CONSTRAINT `alerta_ibfk_1` FOREIGN KEY (`id_usuario_destino`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alerta`
--

LOCK TABLES `alerta` WRITE;
/*!40000 ALTER TABLE `alerta` DISABLE KEYS */;
/*!40000 ALTER TABLE `alerta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditoria`
--

DROP TABLE IF EXISTS `auditoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auditoria` (
  `id_auditoria` int NOT NULL AUTO_INCREMENT,
  `id_projeto` int NOT NULL,
  `tipo` enum('Interna','Externa','Certificação') NOT NULL,
  `data_agendada` datetime NOT NULL,
  `data_realizacao` datetime DEFAULT NULL,
  `id_auditor_responsavel` int NOT NULL,
  `resultado` enum('Conforme','Não Conforme','Parcialmente Conforme') DEFAULT NULL,
  `relatorio_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_auditoria`),
  KEY `id_projeto` (`id_projeto`),
  KEY `id_auditor_responsavel` (`id_auditor_responsavel`),
  CONSTRAINT `auditoria_ibfk_1` FOREIGN KEY (`id_projeto`) REFERENCES `projeto` (`id_projeto`) ON DELETE CASCADE,
  CONSTRAINT `auditoria_ibfk_2` FOREIGN KEY (`id_auditor_responsavel`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditoria`
--

LOCK TABLES `auditoria` WRITE;
/*!40000 ALTER TABLE `auditoria` DISABLE KEYS */;
INSERT INTO `auditoria` VALUES (1,1,'Interna','2026-01-15 09:00:00',NULL,1,NULL,NULL),(2,1,'Externa','2026-01-18 14:00:00','2026-01-18 14:00:00',1,'Conforme',NULL),(3,1,'Interna','2026-01-15 09:00:00',NULL,1,NULL,NULL),(4,1,'Externa','2026-01-18 14:00:00','2026-01-18 14:00:00',1,'Conforme',NULL),(5,1,'Interna','2026-01-15 09:00:00',NULL,1,NULL,NULL),(6,1,'Externa','2026-01-18 14:00:00','2026-01-18 14:00:00',1,'Conforme',NULL),(7,1,'Interna','2026-01-20 10:30:00','2026-01-20 10:30:00',1,'Parcialmente Conforme',NULL);
/*!40000 ALTER TABLE `auditoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checklist`
--

DROP TABLE IF EXISTS `checklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checklist` (
  `id_checklist` int NOT NULL AUTO_INCREMENT,
  `id_norma` int NOT NULL,
  `id_projeto` int NOT NULL,
  `nome` varchar(100) NOT NULL,
  `descricao` text,
  `data_criacao` datetime DEFAULT CURRENT_TIMESTAMP,
  `id_criador` int NOT NULL,
  PRIMARY KEY (`id_checklist`),
  KEY `id_norma` (`id_norma`),
  KEY `id_projeto` (`id_projeto`),
  KEY `id_criador` (`id_criador`),
  CONSTRAINT `checklist_ibfk_1` FOREIGN KEY (`id_norma`) REFERENCES `normaiso` (`id_norma`),
  CONSTRAINT `checklist_ibfk_2` FOREIGN KEY (`id_projeto`) REFERENCES `projeto` (`id_projeto`) ON DELETE CASCADE,
  CONSTRAINT `checklist_ibfk_3` FOREIGN KEY (`id_criador`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checklist`
--

LOCK TABLES `checklist` WRITE;
/*!40000 ALTER TABLE `checklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `checklist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documento`
--

DROP TABLE IF EXISTS `documento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documento` (
  `id_documento` int NOT NULL AUTO_INCREMENT,
  `id_projeto` int NOT NULL,
  `nome` varchar(100) NOT NULL,
  `tipo` enum('Manual','Relatório','Checklist','Certificado','Laudo','Outro') NOT NULL,
  `caminho_arquivo` varchar(255) NOT NULL,
  `data_upload` datetime DEFAULT CURRENT_TIMESTAMP,
  `id_uploader` int NOT NULL,
  `versao` varchar(20) NOT NULL,
  `data_validade` date DEFAULT NULL,
  `aprovado` tinyint(1) DEFAULT '0',
  `id_aprovador` int DEFAULT NULL,
  `data_aprovacao` datetime DEFAULT NULL,
  PRIMARY KEY (`id_documento`),
  KEY `id_projeto` (`id_projeto`),
  KEY `id_uploader` (`id_uploader`),
  KEY `id_aprovador` (`id_aprovador`),
  CONSTRAINT `documento_ibfk_1` FOREIGN KEY (`id_projeto`) REFERENCES `projeto` (`id_projeto`) ON DELETE CASCADE,
  CONSTRAINT `documento_ibfk_2` FOREIGN KEY (`id_uploader`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `documento_ibfk_3` FOREIGN KEY (`id_aprovador`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documento`
--

LOCK TABLES `documento` WRITE;
/*!40000 ALTER TABLE `documento` DISABLE KEYS */;
/*!40000 ALTER TABLE `documento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `etapaprojeto`
--

DROP TABLE IF EXISTS `etapaprojeto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `etapaprojeto` (
  `id_etapa` int NOT NULL AUTO_INCREMENT,
  `id_projeto` int NOT NULL,
  `nome` varchar(50) NOT NULL,
  `descricao` text,
  `data_inicio_prevista` date NOT NULL,
  `data_termino_prevista` date NOT NULL,
  `data_inicio_real` date DEFAULT NULL,
  `data_termino_real` date DEFAULT NULL,
  `status` enum('Não Iniciada','Em Andamento','Concluída','Atrasada') DEFAULT 'Não Iniciada',
  PRIMARY KEY (`id_etapa`),
  KEY `id_projeto` (`id_projeto`),
  CONSTRAINT `etapaprojeto_ibfk_1` FOREIGN KEY (`id_projeto`) REFERENCES `projeto` (`id_projeto`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `etapaprojeto`
--

LOCK TABLES `etapaprojeto` WRITE;
/*!40000 ALTER TABLE `etapaprojeto` DISABLE KEYS */;
/*!40000 ALTER TABLE `etapaprojeto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evidenciachecklist`
--

DROP TABLE IF EXISTS `evidenciachecklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evidenciachecklist` (
  `id_evidencia` int NOT NULL AUTO_INCREMENT,
  `id_item` int NOT NULL,
  `caminho_arquivo` varchar(255) NOT NULL,
  `tipo_arquivo` varchar(50) NOT NULL,
  `data_upload` datetime DEFAULT CURRENT_TIMESTAMP,
  `id_uploader` int NOT NULL,
  PRIMARY KEY (`id_evidencia`),
  KEY `id_item` (`id_item`),
  KEY `id_uploader` (`id_uploader`),
  CONSTRAINT `evidenciachecklist_ibfk_1` FOREIGN KEY (`id_item`) REFERENCES `itemchecklist` (`id_item`) ON DELETE CASCADE,
  CONSTRAINT `evidenciachecklist_ibfk_2` FOREIGN KEY (`id_uploader`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evidenciachecklist`
--

LOCK TABLES `evidenciachecklist` WRITE;
/*!40000 ALTER TABLE `evidenciachecklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `evidenciachecklist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemchecklist`
--

DROP TABLE IF EXISTS `itemchecklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itemchecklist` (
  `id_item` int NOT NULL AUTO_INCREMENT,
  `id_checklist` int NOT NULL,
  `codigo_clausula` varchar(20) NOT NULL,
  `descricao` text NOT NULL,
  `status` enum('Não Aplicável','Não Verificado','Conforme','Não Conforme') DEFAULT 'Não Verificado',
  `observacoes` text,
  `id_responsavel_verificacao` int DEFAULT NULL,
  `data_verificacao` datetime DEFAULT NULL,
  PRIMARY KEY (`id_item`),
  KEY `id_checklist` (`id_checklist`),
  KEY `id_responsavel_verificacao` (`id_responsavel_verificacao`),
  CONSTRAINT `itemchecklist_ibfk_1` FOREIGN KEY (`id_checklist`) REFERENCES `checklist` (`id_checklist`) ON DELETE CASCADE,
  CONSTRAINT `itemchecklist_ibfk_2` FOREIGN KEY (`id_responsavel_verificacao`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemchecklist`
--

LOCK TABLES `itemchecklist` WRITE;
/*!40000 ALTER TABLE `itemchecklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `itemchecklist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `naoconformidade`
--

DROP TABLE IF EXISTS `naoconformidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `naoconformidade` (
  `id_nao_conformidade` int NOT NULL AUTO_INCREMENT,
  `id_auditoria` int DEFAULT NULL,
  `id_projeto` int NOT NULL,
  `descricao` text NOT NULL,
  `causa_raiz` text,
  `gravidade` enum('Baixa','Média','Alta','Crítica') NOT NULL,
  `data_registro` datetime DEFAULT CURRENT_TIMESTAMP,
  `id_responsavel_correcao` int NOT NULL,
  `prazo_correcao` date NOT NULL,
  `status` enum('Registrada','Em Correção','Corrigida','Verificada','Fechada') DEFAULT 'Registrada',
  `data_correcao` datetime DEFAULT NULL,
  PRIMARY KEY (`id_nao_conformidade`),
  KEY `id_auditoria` (`id_auditoria`),
  KEY `id_projeto` (`id_projeto`),
  KEY `id_responsavel_correcao` (`id_responsavel_correcao`),
  CONSTRAINT `naoconformidade_ibfk_1` FOREIGN KEY (`id_auditoria`) REFERENCES `auditoria` (`id_auditoria`) ON DELETE SET NULL,
  CONSTRAINT `naoconformidade_ibfk_2` FOREIGN KEY (`id_projeto`) REFERENCES `projeto` (`id_projeto`) ON DELETE CASCADE,
  CONSTRAINT `naoconformidade_ibfk_3` FOREIGN KEY (`id_responsavel_correcao`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `naoconformidade`
--

LOCK TABLES `naoconformidade` WRITE;
/*!40000 ALTER TABLE `naoconformidade` DISABLE KEYS */;
INSERT INTO `naoconformidade` VALUES (1,2,2,'Registro incompleto de evidências de auditoria.','Falta de procedimento padronizado.','Média','2026-01-13 03:39:56',2,'2026-01-28','Em Correção',NULL),(2,NULL,1,'Documento vencido anexado ao projeto.','Controle de validade não aplicado.','Alta','2026-01-13 03:39:56',1,'2026-01-25','Registrada',NULL),(3,1,1,'Documento vencido utilizado na auditoria.','Falta de controle de validade.','Alta','2026-01-13 19:04:33',1,'2026-01-25','Registrada',NULL),(4,2,1,'Registro incompleto de evidências.','Procedimento não seguido.','Média','2026-01-13 19:04:33',1,'2026-01-28','Em Correção',NULL),(5,1,1,'Documento vencido utilizado na auditoria.','Falta de controle de validade.','Alta','2026-01-13 19:08:52',1,'2026-01-25','Registrada',NULL),(6,2,1,'Registro incompleto de evidências.','Procedimento não seguido.','Média','2026-01-13 19:08:52',1,'2026-01-28','Em Correção',NULL),(7,2,1,'Teste','Teste de cadastro de texto','Alta','2026-01-13 19:52:41',2,'2026-01-14','Em Correção',NULL);
/*!40000 ALTER TABLE `naoconformidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `normaiso`
--

DROP TABLE IF EXISTS `normaiso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `normaiso` (
  `id_norma` int NOT NULL AUTO_INCREMENT,
  `codigo` varchar(20) NOT NULL,
  `descricao` varchar(100) NOT NULL,
  `versao` varchar(20) NOT NULL,
  `ativa` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id_norma`),
  UNIQUE KEY `codigo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `normaiso`
--

LOCK TABLES `normaiso` WRITE;
/*!40000 ALTER TABLE `normaiso` DISABLE KEYS */;
INSERT INTO `normaiso` VALUES (1,'ISO 9001','Gestão da Qualidade','2023',1),(2,'ISO 14001','Gestão Ambiental','2015',1),(3,'ISO 45001','Segurança no Trabalho','2018',1),(4,'ISO 27001','Segurança da Informação','2022',1),(5,'ISO 50001','Gestão de Energia','2018',1);
/*!40000 ALTER TABLE `normaiso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projeto`
--

DROP TABLE IF EXISTS `projeto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projeto` (
  `id_projeto` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `descricao` text,
  `id_cliente` int DEFAULT NULL,
  `data_inicio` date NOT NULL,
  `data_termino` date DEFAULT NULL,
  `orcamento` decimal(12,2) DEFAULT NULL,
  `status` enum('Planejamento','Andamento','Concluído','Cancelado') DEFAULT 'Planejamento',
  `id_responsavel` int NOT NULL,
  `data_criacao` datetime DEFAULT CURRENT_TIMESTAMP,
  `data_atualizacao` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_projeto`),
  KEY `id_responsavel` (`id_responsavel`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `projeto_ibfk_1` FOREIGN KEY (`id_responsavel`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `projeto_ibfk_2` FOREIGN KEY (`id_cliente`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projeto`
--

LOCK TABLES `projeto` WRITE;
/*!40000 ALTER TABLE `projeto` DISABLE KEYS */;
INSERT INTO `projeto` VALUES (1,'Projeto Solar',NULL,5,'2024-01-15','2024-07-15',NULL,'Andamento',2,'2026-01-12 17:41:50','2026-01-12 17:41:50'),(2,'Auditoria ISO 9001',NULL,5,'2024-02-01','2024-03-15',NULL,'Planejamento',3,'2026-01-12 17:41:50','2026-01-12 17:41:50'),(3,'Modernização Fábrica',NULL,5,'2023-11-10','2024-05-20',NULL,'Andamento',2,'2026-01-12 17:41:50','2026-01-12 17:41:50'),(4,'Certificação ISO 14001',NULL,5,'2024-03-01','2024-12-10',NULL,'Planejamento',3,'2026-01-12 17:41:50','2026-01-12 17:41:50'),(5,'Sistema de Gestão',NULL,5,'2023-09-01','2024-02-28',NULL,'Concluído',1,'2026-01-12 17:41:50','2026-01-12 17:41:50'),(6,'Teste 1','Teste de c�digo para saber se est� cadastrando os dados e salvando a descri��o',1,'2026-01-12','2026-01-12',2498.53,'Andamento',1,'2026-01-12 18:19:43','2026-01-12 18:19:43'),(7,'Teste 2','Outro teste',1,'2026-01-13','2026-01-13',3216.02,'Planejamento',1,'2026-01-13 00:37:25','2026-01-13 00:37:25'),(8,'Teste 3','',2,'2026-01-13','2026-01-20',2450.00,'Andamento',2,'2026-01-13 01:40:27','2026-01-13 01:40:27'),(9,'Projeto Solar',NULL,5,'2024-01-15','2024-06-30',NULL,'Andamento',2,'2026-01-13 03:10:08','2026-01-13 03:10:08'),(10,'Auditoria ISO 9001',NULL,5,'2024-02-01','2024-03-15',NULL,'Planejamento',3,'2026-01-13 03:10:08','2026-01-13 03:10:08'),(11,'Modernização Fábrica',NULL,5,'2023-11-10','2024-05-20',NULL,'Andamento',2,'2026-01-13 03:10:08','2026-01-13 03:10:08'),(12,'Certificação ISO 14001',NULL,5,'2024-03-01','2024-12-10',NULL,'Planejamento',3,'2026-01-13 03:10:08','2026-01-13 03:10:08'),(13,'Sistema de Gestão',NULL,5,'2023-09-01','2024-02-28',NULL,'Concluído',1,'2026-01-13 03:10:08','2026-01-13 03:10:08'),(14,'Projeto Solar',NULL,5,'2024-01-15','2024-06-30',NULL,'Andamento',2,'2026-01-13 03:39:56','2026-01-13 03:39:56'),(15,'Auditoria ISO 9001',NULL,5,'2024-02-01','2024-03-15',NULL,'Planejamento',3,'2026-01-13 03:39:56','2026-01-13 03:39:56'),(16,'Modernização Fábrica',NULL,5,'2023-11-10','2024-05-20',NULL,'Andamento',2,'2026-01-13 03:39:56','2026-01-13 03:39:56'),(17,'Certificação ISO 14001',NULL,5,'2024-03-01','2024-12-10',NULL,'Planejamento',3,'2026-01-13 03:39:56','2026-01-13 03:39:56'),(18,'Sistema de Gestão',NULL,5,'2023-09-01','2024-02-28',NULL,'Concluído',1,'2026-01-13 03:39:56','2026-01-13 03:39:56'),(19,'Projeto Solar',NULL,5,'2024-01-15','2024-06-30',NULL,'Andamento',2,'2026-01-13 19:04:33','2026-01-13 19:04:33'),(20,'Auditoria ISO 9001',NULL,5,'2024-02-01','2024-03-15',NULL,'Planejamento',3,'2026-01-13 19:04:33','2026-01-13 19:04:33'),(21,'Modernização Fábrica',NULL,5,'2023-11-10','2024-05-20',NULL,'Andamento',2,'2026-01-13 19:04:33','2026-01-13 19:04:33'),(22,'Certificação ISO 14001',NULL,5,'2024-03-01','2024-12-10',NULL,'Planejamento',3,'2026-01-13 19:04:33','2026-01-13 19:04:33'),(23,'Sistema de Gestão',NULL,5,'2023-09-01','2024-02-28',NULL,'Concluído',1,'2026-01-13 19:04:33','2026-01-13 19:04:33'),(24,'Projeto Solar',NULL,5,'2024-01-15','2024-06-30',NULL,'Andamento',2,'2026-01-13 19:08:52','2026-01-13 19:08:52'),(25,'Auditoria ISO 9001',NULL,5,'2024-02-01','2024-03-15',NULL,'Planejamento',3,'2026-01-13 19:08:52','2026-01-13 19:08:52'),(26,'Modernização Fábrica',NULL,5,'2023-11-10','2024-05-20',NULL,'Andamento',2,'2026-01-13 19:08:52','2026-01-13 19:08:52'),(27,'Certificação ISO 14001',NULL,5,'2024-03-01','2024-12-10',NULL,'Planejamento',3,'2026-01-13 19:08:52','2026-01-13 19:08:52'),(28,'Sistema de Gestão',NULL,5,'2023-09-01','2024-02-28',NULL,'Concluído',1,'2026-01-13 19:08:52','2026-01-13 19:08:52'),(29,'Teste 4','',3,'2026-01-13','2026-01-20',125.25,'Concluído',3,'2026-01-13 19:10:58','2026-01-13 19:10:58');
/*!40000 ALTER TABLE `projeto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projeto_norma`
--

DROP TABLE IF EXISTS `projeto_norma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projeto_norma` (
  `id_projeto` int NOT NULL,
  `id_norma` int NOT NULL,
  `data_vinculacao` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_projeto`,`id_norma`),
  KEY `id_norma` (`id_norma`),
  CONSTRAINT `projeto_norma_ibfk_1` FOREIGN KEY (`id_projeto`) REFERENCES `projeto` (`id_projeto`) ON DELETE CASCADE,
  CONSTRAINT `projeto_norma_ibfk_2` FOREIGN KEY (`id_norma`) REFERENCES `normaiso` (`id_norma`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projeto_norma`
--

LOCK TABLES `projeto_norma` WRITE;
/*!40000 ALTER TABLE `projeto_norma` DISABLE KEYS */;
INSERT INTO `projeto_norma` VALUES (1,2,'2026-01-12 17:41:50'),(1,3,'2026-01-12 17:41:50'),(2,1,'2026-01-12 17:41:50'),(3,1,'2026-01-12 17:41:50'),(4,2,'2026-01-12 17:41:50');
/*!40000 ALTER TABLE `projeto_norma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `tipo` enum('Administrador','Gerente','Consultor','Engenheiro','Cliente') NOT NULL,
  `ativo` tinyint(1) DEFAULT '1',
  `data_criacao` datetime DEFAULT CURRENT_TIMESTAMP,
  `data_atualizacao` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Christian Barrantes','christian@empresa.com','55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251','Administrador',1,'2026-01-12 17:41:50','2026-01-12 17:41:50'),(2,'Fihama Santos','fih@empresa.com','6b08d780140e292a4af8ba3f2333fc1357091442d7e807c6cad92e8dcd0240b7','Gerente',1,'2026-01-12 17:41:50','2026-01-12 17:41:50'),(3,'Carlos Barrantes','carlos@empresa.com','b578dc5fcbfabbc7e96400601d0858c951f04929faef033bbbc117ab935c6ae9','Consultor',1,'2026-01-12 17:41:50','2026-01-12 17:41:50'),(4,'Nancy Briceño','nancy@empresa.com','6a2085168e2877e655b49046832e165180ca948638efdca39f7662733fe039e9','Engenheiro',1,'2026-01-12 17:41:50','2026-01-12 17:41:50'),(5,'Steven Barrantes','sybarrantes@empresa.com','a8117a3a76bd3110dfa4b46b3381c1489513c1d2ab1a634f5f1f73346dceedc8','Cliente',1,'2026-01-12 17:41:50','2026-01-12 17:41:50');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-13 20:10:35
