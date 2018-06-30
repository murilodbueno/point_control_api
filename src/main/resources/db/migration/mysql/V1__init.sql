--create tables
CREATE TABLE `empresa` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `cnpj` VARCHAR(255) NOT NULL,
  `data_atualizacao` DATETIME NOT NULL,
  `razao_social` VARCHAR(255) NOT NULL,
  `data_criacao` DATETIME NOT NULL,
   primary key (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `funcionario` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `cpf` VARCHAR(255) NOT NULL,
  `valor_hora` DECIMAL(19,2) NOT NULL,
  `data_atualizacao` DATETIME NOT NULL,
  `data_criacao` DATETIME NOT NULL,
  `perfil` VARCHAR(255) NOT NULL,
  `qtd_horas_almoco` FLOAT NOT NULL,
  `qtd_horas_trabalho_dia` FLOAT NOT NULL,
  `empresa_id` BIGINT(20) NOT NULL,
   primary key (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lancamento` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `data` DATETIME NOT NULL,
  `data_atualizacao` DATETIME NOT NULL,
  `data_criacao` DATETIME NOT NULL,
  `descricao` VARCHAR(255) NOT NULL,
  `localizacao` VARCHAR(255) NOT NULL,
  `tipo` VARCHAR(255) NOT NULL,
  `funcionario_id` BIGINT(20) NOT NULL,
  primary key (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--index for table funcionario
ALTER table `funcionario`
  ADD KEY `FK_EMPRESA_ID`(`empresa_id`);

--index for table lancamento
ALTER table `lancamento`
  ADD KEY `FK_FUNCIONARIO_ID`(`funcionario_id`);

--
--constraints for table funcionario
ALTER TABLE `funcionario`
  ADD CONSTRAINT `FK_EMPRESA_ID` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`);

--constraints for table lancamento
ALTER TABLE `lancamento`
  ADD CONSTRAINT `FK_FUNCIONARIO_ID` FOREIGN KEY (`funcionario_id`) REFERENCES `funcionario` (`id`);