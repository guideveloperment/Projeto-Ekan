package br.com.planosaude.ekan.model.dto;

import br.com.planosaude.ekan.model.Documento;
import br.com.planosaude.ekan.model.TipoDocumento;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DocumentoResponseTo(
    Long id,
    TipoDocumento tipoDocumento,
    String descricao,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime dataInclusao,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime dataAtualizacao
) {
    public static DocumentoResponseTo fromDocumento(Documento documento) {
        return new DocumentoResponseTo(
            documento.getId(),
            documento.getTipoDocumento(),
            documento.getDescricao(),
            documento.getDataInclusao(),
            documento.getDataAtualizacao()
        );
    }
}
