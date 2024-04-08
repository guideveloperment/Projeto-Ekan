CREATE TABLE IF NOT EXISTS documentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_documento VARCHAR(50) NOT NULL,
    descricao VARCHAR(255),
    data_inclusao DATETIME,
    data_atualizacao DATETIME,
    beneficiario_id BIGINT,
    FOREIGN KEY (beneficiario_id) REFERENCES beneficiarios(id) ON DELETE CASCADE
);