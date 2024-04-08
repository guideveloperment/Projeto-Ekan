package br.com.planosaude.ekan.model.dto;

import br.com.planosaude.ekan.model.TipoDocumento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DocumentoUpdateRequestTo(
        @NotNull(message = "o id do documento não pode ser nulo.")
        Long id,
        @NotNull(message = "O tipo do documento não pode ser nulo.")
        TipoDocumento tipoDocumento,
        @NotBlank(message = "A descrição não pode estar em branco.")
        @Size(max = 20, message = "A descrição deve ter no máximo 20 caracteres.")
        String descricao,
        Long beneficiarioId
) {
}
