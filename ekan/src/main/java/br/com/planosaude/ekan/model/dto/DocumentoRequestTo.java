package br.com.planosaude.ekan.model.dto;

import br.com.planosaude.ekan.model.Beneficiario;
import br.com.planosaude.ekan.model.TipoDocumento;
import jakarta.validation.constraints.NotNull;

public record DocumentoRequestTo(
        @NotNull(message = "O Tipo Documento nao poder nulo!")
        TipoDocumento tipoDocumento,
        String descricao
) {
}
