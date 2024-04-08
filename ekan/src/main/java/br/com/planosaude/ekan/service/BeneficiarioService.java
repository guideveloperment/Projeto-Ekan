package br.com.planosaude.ekan.service;

import br.com.planosaude.ekan.model.dto.BeneficiarioRequestTo;
import br.com.planosaude.ekan.model.dto.BeneficiarioResponseTo;
import br.com.planosaude.ekan.model.dto.BeneficiarioUpdateRequestTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BeneficiarioService {

    BeneficiarioResponseTo save(BeneficiarioRequestTo form);

    BeneficiarioResponseTo getById(Long id);

    Page<BeneficiarioResponseTo> listAll(Pageable page);

    BeneficiarioResponseTo update(BeneficiarioUpdateRequestTo form);

    void deleteById(Long id);
}
