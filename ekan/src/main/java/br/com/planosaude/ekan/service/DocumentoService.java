package br.com.planosaude.ekan.service;

import br.com.planosaude.ekan.model.dto.DocumentoRequestTo;
import br.com.planosaude.ekan.model.dto.DocumentoResponseTo;
import br.com.planosaude.ekan.model.dto.DocumentoUpdateRequestTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DocumentoService {

    DocumentoResponseTo save(DocumentoRequestTo form);

    DocumentoResponseTo getById(Long id);

    Page<DocumentoResponseTo> listAll(Pageable page);

    DocumentoResponseTo update(DocumentoUpdateRequestTo form);

    void deleteById(Long id);
}
