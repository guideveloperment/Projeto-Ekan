package br.com.planosaude.ekan.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record BeneficiarioRequestTo(
        @NotNull(message = "O nome nao pode ser nulo.")
        String nome,
        String telefone,
        @NotNull(message = "A data de nascimento nao pode ser nula.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataNascimento,
        @JsonAlias({"ids_documentos", "documentos"})
        List<Long> documentosIds
) {
}
