package br.com.planosaude.ekan.service.impl;

import br.com.planosaude.ekan.exception.DocumentoAssociadoException;
import br.com.planosaude.ekan.exception.NotFoundException;
import br.com.planosaude.ekan.model.Beneficiario;
import br.com.planosaude.ekan.model.Documento;
import br.com.planosaude.ekan.model.dto.BeneficiarioRequestTo;
import br.com.planosaude.ekan.model.dto.BeneficiarioResponseTo;
import br.com.planosaude.ekan.model.dto.BeneficiarioUpdateRequestTo;
import br.com.planosaude.ekan.repository.BeneficiarioRepository;
import br.com.planosaude.ekan.repository.DocumentoRepository;
import br.com.planosaude.ekan.service.BeneficiarioService;
import br.com.planosaude.ekan.util.Constant;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.planosaude.ekan.util.Constant.DOCUMENTO_ASSOCIADO_MSG;
import static br.com.planosaude.ekan.util.Constant.FORMULARIO_NAO_SUPORTADO_MSG;
import static br.com.planosaude.ekan.util.Constant.ERROR_MESSAGE_NOT_FOUND_BENEFICIARIO;

@Service
@RequiredArgsConstructor
public class BeneficiarioServiceImpl implements BeneficiarioService {

    private final BeneficiarioRepository beneficiarioRepository;

    private final DocumentoRepository documentoRepository;

    @Override
    @Transactional
    public BeneficiarioResponseTo save(BeneficiarioRequestTo form) {

        verifyIfDocumentIsAlreadyAssociated(form);

        var beneficiario = new Beneficiario();
        buildBeneficiario(beneficiario, form);

        beneficiario = beneficiarioRepository.save(beneficiario);

        return BeneficiarioResponseTo.fromBeneficiario(beneficiario);
    }

    @Override
    public BeneficiarioResponseTo getById(Long id) {
        var beneficiario = beneficiarioRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ERROR_MESSAGE_NOT_FOUND_BENEFICIARIO, id));
        return BeneficiarioResponseTo.fromBeneficiario(beneficiario);
    }

    @Override
    public Page<BeneficiarioResponseTo> listAll(Pageable page) {
        Page<Beneficiario> beneficiariosPage = beneficiarioRepository.findAll(page);

        var beneficiariosResponse = beneficiariosPage.getContent().stream()
                .map(BeneficiarioResponseTo::fromBeneficiario)
                .collect(Collectors.toList());

        return new PageImpl<>(beneficiariosResponse, page, beneficiariosPage.getTotalElements());
    }

    @Override
    @Transactional
    public BeneficiarioResponseTo update(BeneficiarioUpdateRequestTo form) {

        verifyIfDocumentIsAlreadyAssociated(form);

        var beneficiario = beneficiarioRepository.findById(form.id())
                .orElseThrow(() -> new NotFoundException(String.format(Constant.ERROR_MESSAGE_NOT_FOUND_BENEFICIARIO,
                        form.id())));

        updateDadosBeneficiario(form, beneficiario);

        verifyIfUnusedDocumentsExistAndRemove(form, beneficiario);
        verifyIfRecentlyAcquiredDocumentsCanBeAdded(form, beneficiario);

        beneficiario = beneficiarioRepository.save(beneficiario);

        return BeneficiarioResponseTo.fromBeneficiario(beneficiario);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        var beneficiario = beneficiarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(Constant.ERROR_MESSAGE_NOT_FOUND_BENEFICIARIO, id)));

        beneficiarioRepository.delete(beneficiario);
    }

    private void verifyIfDocumentIsAlreadyAssociated(Object form) {
        List<Long> documentosIds;
        if (form instanceof BeneficiarioRequestTo) {
            documentosIds = ((BeneficiarioRequestTo) form).documentosIds();
        } else if (form instanceof BeneficiarioUpdateRequestTo) {
            documentosIds = ((BeneficiarioUpdateRequestTo) form).documentosIds();
        } else {
            throw new IllegalArgumentException(FORMULARIO_NAO_SUPORTADO_MSG);
        }

        List<Documento> documentos = documentoRepository.findAllById(documentosIds);
        documentos.stream()
                .filter(documento -> documento.getBeneficiario() != null)
                .findAny()
                .ifPresent(documento -> {
                    throw new DocumentoAssociadoException(String.format(DOCUMENTO_ASSOCIADO_MSG, documento.getId()));
                });
    }

    private void verifyIfRecentlyAcquiredDocumentsCanBeAdded(BeneficiarioUpdateRequestTo form, Beneficiario beneficiario) {
        //Adicionando novos documentos ao beneficiário
        for (Long documentId : form.documentosIds()) {
            if (beneficiario.getDocumentos().stream().noneMatch(doc -> doc.getId().equals(documentId))) {
                var documento = documentoRepository.findById(documentId)
                        .orElseThrow(() -> new NotFoundException(String.format(Constant.ERROR_MESSAGE_NOT_FOUND_DOCUMENTO,
                                documentId)));
                beneficiario.addDocumento(documento);
            }
        }
    }

    private void verifyIfUnusedDocumentsExistAndRemove(BeneficiarioUpdateRequestTo form, Beneficiario beneficiario) {
        //Removendo documentos que não estão mais presentes no formulário
        List<Documento> documentosRemover = new ArrayList<>();
        for (Documento documento : beneficiario.getDocumentos()) {
            if (!form.documentosIds().contains(documento.getId())) {
                documento.setBeneficiario(null);
                documentosRemover.add(documento);
            }
        }
        beneficiario.getDocumentos().removeAll(documentosRemover);
    }

    private void updateDadosBeneficiario(BeneficiarioUpdateRequestTo form, Beneficiario beneficiario) {
        //Atualizando os dados do beneficiário
        beneficiario.setNome(form.nome());
        beneficiario.setTelefone(form.telefone());
        beneficiario.setDataNascimento(form.dataNascimento());
        beneficiario.setDataAtualizacao(LocalDateTime.now().withNano(0));
    }

    private void buildBeneficiario(Beneficiario beneficiario, BeneficiarioRequestTo form) {
        beneficiario.setNome(form.nome());
        beneficiario.setTelefone(form.telefone());
        beneficiario.setDataNascimento(form.dataNascimento());
        beneficiario.setDataInclusao(LocalDateTime.now());
        beneficiario.setDataAtualizacao(LocalDateTime.now());

        //Busca os documentos pelo ID e os adiciona ao beneficiário
        var documentoIds = form.documentosIds();
        var documentos = documentoIds.stream()
                .map(documentoRepository::findById)
                .map(Optional::orElseThrow) //Converte Optional<Document> para Document
                .collect(Collectors.toList());
        documentos.forEach(beneficiario::addDocumento);
    }
}
