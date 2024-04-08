package br.com.planosaude.ekan.model.dto;

import br.com.planosaude.ekan.model.Beneficiario;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record BeneficiarioResponseTo(
        Long beneficiarioId,
        String nome,
        String telefone,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataNascimento,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataInclusao,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataAtualizacao,
        List<DocumentoResponseTo> documentos
) {
    public static BeneficiarioResponseTo fromBeneficiario(Beneficiario beneficiarioSaved) {
        return new BeneficiarioResponseTo(
                beneficiarioSaved.getId(),
                beneficiarioSaved.getNome(),
                beneficiarioSaved.getTelefone(),
                beneficiarioSaved.getDataNascimento(),
                beneficiarioSaved.getDataInclusao(),
                beneficiarioSaved.getDataAtualizacao(),
                beneficiarioSaved.getDocumentos().stream()
                        .map(DocumentoResponseTo::fromDocumento)
                        .collect(Collectors.toList())
        );
    }
}