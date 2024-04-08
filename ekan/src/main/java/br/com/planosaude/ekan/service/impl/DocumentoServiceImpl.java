package br.com.planosaude.ekan.service.impl;

import br.com.planosaude.ekan.exception.DocumentoDuplicadoException;
import br.com.planosaude.ekan.exception.TipoDocumentoInvalidoException;
import br.com.planosaude.ekan.model.TipoDocumento;
import br.com.planosaude.ekan.model.dto.DocumentoUpdateRequestTo;
import br.com.planosaude.ekan.repository.BeneficiarioRepository;
import br.com.planosaude.ekan.repository.DocumentoRepository;
import br.com.planosaude.ekan.service.DocumentoService;
import br.com.planosaude.ekan.util.Constant;
import br.com.planosaude.ekan.exception.NotFoundException;
import br.com.planosaude.ekan.model.Documento;
import br.com.planosaude.ekan.model.dto.DocumentoRequestTo;
import br.com.planosaude.ekan.model.dto.DocumentoResponseTo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import static br.com.planosaude.ekan.util.Constant.DOCUMENTO_DUPLICADO_MSG;

@Service
@RequiredArgsConstructor
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoRepository repository;

    private final BeneficiarioRepository beneficiarioRepository;

    @Override
    @Transactional
    public DocumentoResponseTo save(DocumentoRequestTo form) {

        if (Arrays.stream(TipoDocumento.values())
                .noneMatch(tipo -> tipo.equals(form.tipoDocumento()))) {
            throw new TipoDocumentoInvalidoException(String.format(Constant.TIPO_DOCUMENTO_INVALIDO_MSG,
                    form.tipoDocumento().getDescricao()));
        }

        if (repository.existsByTipoDocumentoAndDescricao(form.tipoDocumento(), form.descricao())) {
            throw new DocumentoDuplicadoException(DOCUMENTO_DUPLICADO_MSG);
        }
        var documento = buildDocumento(form);

        documento = repository.save(documento);

        return DocumentoResponseTo.fromDocumento(documento);
    }

    @Override
    public DocumentoResponseTo getById(Long id) {
        var beneficiario = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(Constant.ERROR_MESSAGE_NOT_FOUND_DOCUMENTO, id)));
        return DocumentoResponseTo.fromDocumento(beneficiario);
    }

    @Override
    public Page<DocumentoResponseTo> listAll(Pageable page) {
        Page<Documento> beneficiariosPage = repository.findAll(page);

        var beneficiariosResponse = beneficiariosPage.getContent().stream()
                .map(DocumentoResponseTo::fromDocumento)
                .collect(Collectors.toList());

        return new PageImpl<>(beneficiariosResponse, page, beneficiariosPage.getTotalElements());
    }

    @Override
    @Transactional
    public DocumentoResponseTo update(DocumentoUpdateRequestTo form) {
        // Verificar se o tipo do documento existe no enum TipoDocumento
        if (Arrays.stream(TipoDocumento.values())
                .noneMatch(tipo -> tipo.getDescricao().equalsIgnoreCase(form.tipoDocumento().name()))) {
            throw new TipoDocumentoInvalidoException(String.format(Constant.TIPO_DOCUMENTO_INVALIDO_MSG,
                    form.tipoDocumento()));
        }

        var documento = repository.findById(form.id())
                .orElseThrow(() -> new NotFoundException(String.format(Constant.ERROR_MESSAGE_NOT_FOUND_DOCUMENTO,
                        form.id())));

        // Atualizando os dados do documento
        documento.setTipoDocumento(form.tipoDocumento());
        documento.setDescricao(form.descricao());
        documento.setDataAtualizacao(LocalDateTime.now());

        // Atualizando o relacionamento com o beneficiário
        if (form.beneficiarioId() != null) {
            var beneficiario = beneficiarioRepository.findById(form.beneficiarioId())
                    .orElseThrow(() -> new NotFoundException(String.format(Constant.ERROR_MESSAGE_NOT_FOUND_BENEFICIARIO,
                            form.beneficiarioId())));
            documento.setBeneficiario(beneficiario);
        }

        documento = repository.save(documento);

        return DocumentoResponseTo.fromDocumento(documento);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        var documento = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(Constant.ERROR_MESSAGE_NOT_FOUND_DOCUMENTO, id)));

        repository.delete(documento);
    }

    private Documento buildDocumento(DocumentoRequestTo form) {
        Documento documento = new Documento();
        documento.setTipoDocumento(form.tipoDocumento());
        documento.setDescricao(form.descricao());
        documento.setDataInclusao(LocalDateTime.now());
        documento.setDataAtualizacao(LocalDateTime.now());
        return documento;
    }

}
