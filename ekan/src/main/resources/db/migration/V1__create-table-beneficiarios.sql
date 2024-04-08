CREATE TABLE IF NOT EXISTS beneficiarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    data_nascimento DATETIME NOT NULL,
    data_inclusao DATETIME,
    data_atualizacao DATETIME
);