package br.com.planosaude.ekan.model;

import br.com.planosaude.ekan.model.dto.DocumentoRequestTo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "documentos")
@Data
@NoArgsConstructor
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_inclusao")
    private LocalDateTime dataInclusao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiario_id")
    private Beneficiario beneficiario;


    public Documento(DocumentoRequestTo form, Beneficiario beneficiario) {
        this.tipoDocumento = form.tipoDocumento();
        this.descricao = form.descricao();
        this.dataInclusao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.beneficiario = beneficiario;
    }
}
