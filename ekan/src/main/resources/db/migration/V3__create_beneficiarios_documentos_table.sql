CREATE TABLE IF NOT EXISTS beneficiarios_documentos (
    beneficiario_id BIGINT,
    documento_id BIGINT,
    FOREIGN KEY (beneficiario_id) REFERENCES beneficiarios(id),
    FOREIGN KEY (documento_id) REFERENCES documentos(id)
);
