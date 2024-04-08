package br.com.planosaude.ekan.repository;

import br.com.planosaude.ekan.model.Beneficiario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {

}
