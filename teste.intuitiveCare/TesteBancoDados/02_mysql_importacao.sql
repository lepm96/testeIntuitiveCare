USE ans_analytics;

LOAD DATA INFILE '/caminho/para/operadoras_ativas.csv'
INTO TABLE operadoras_ativas
CHARACTER SET utf8mb4
FIELDS TERMINATED BY ';'
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

LOAD DATA INFILE '/caminho/para/demonstracao_contabil.csv'
INTO TABLE demonstracoes_contabeis
CHARACTER SET utf8mb4
FIELDS TERMINATED BY ';'
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(registro_ans, competencia, conta_contabil, descricao_conta, valor);