CREATE DATABASE IF NOT EXISTS ans_analytics;
USE ans_analytics;

CREATE TABLE IF NOT EXISTS operadoras_ativas (
                                                 registro_ans VARCHAR(20) PRIMARY KEY,
    cnpj VARCHAR(18),
    razao_social VARCHAR(255),
    nome_fantasia VARCHAR(255),
    modalidade VARCHAR(100),
    logradouro VARCHAR(255),
    numero VARCHAR(20),
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    uf VARCHAR(2),
    cep VARCHAR(10),
    ddd VARCHAR(4),
    telefone VARCHAR(20),
    fax VARCHAR(20),
    email VARCHAR(100),
    representante VARCHAR(100),
    cargo_representante VARCHAR(100),
    data_registro_ans DATE
    ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS demonstracoes_contabeis (
                                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                                       registro_ans VARCHAR(20),
    competencia DATE,
    conta_contabil VARCHAR(50),
    descricao_conta VARCHAR(255),
    valor DOUBLE,
    INDEX idx_registro_ans (registro_ans),
    INDEX idx_competencia (competencia),
    INDEX idx_conta_contabil (conta_contabil)
    ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;