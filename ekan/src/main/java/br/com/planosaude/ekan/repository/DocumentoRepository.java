package br.com.planosaude.ekan.repository;

import br.com.planosaude.ekan.model.Documento;
import br.com.planosaude.ekan.model.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    boolean existsByTipoDocumentoAndDescricao(TipoDocumento tipoDocumento, String descricao);
}

