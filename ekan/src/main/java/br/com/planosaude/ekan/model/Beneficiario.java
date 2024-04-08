package br.com.planosaude.ekan.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "beneficiarios")
@Data
@NoArgsConstructor
public class Beneficiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "data_nascimento")
    private LocalDateTime dataNascimento;

    @Column(name = "data_inclusao")
    private LocalDateTime dataInclusao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "beneficiario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos = new ArrayList<>();

    public void addDocumento(Documento documento) {
        documentos.add(documento);
        documento.setBeneficiario(this);
    }

    public void removeDocumento(Documento documento) {
        documentos.remove(documento);
        documento.setBeneficiario(null);
    }
}
