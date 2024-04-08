package br.com.planosaude.ekan.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoDocumento {

    RG(1, "RG"),
    CPF(2, "CPF"),
    CNH(3, "CNH"),
    PASSAPORTE(4, "PASSAPORTE");

    private int id;
    private final String descricao;
}
