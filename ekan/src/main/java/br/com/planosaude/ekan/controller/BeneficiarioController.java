package br.com.planosaude.ekan.controller;

import br.com.planosaude.ekan.model.dto.BeneficiarioUpdateRequestTo;
import br.com.planosaude.ekan.service.BeneficiarioService;
import br.com.planosaude.ekan.model.dto.BeneficiarioRequestTo;
import br.com.planosaude.ekan.model.dto.BeneficiarioResponseTo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("beneficiario")
@RequiredArgsConstructor
public class BeneficiarioController {

    private final BeneficiarioService service;

    @PostMapping
    public ResponseEntity<BeneficiarioResponseTo> save(@RequestBody @Valid BeneficiarioRequestTo form){
        return ResponseEntity.ok().body(service.save(form));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeneficiarioResponseTo> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("list-all")
    public ResponseEntity<Page<BeneficiarioResponseTo>> listAll(@PageableDefault(size = 10, sort = {"id"})Pageable page) {
        Page<BeneficiarioResponseTo> beneficiarios = service.listAll(page);
        return ResponseEntity.ok().body(beneficiarios);
    }

    @PutMapping
    public ResponseEntity<BeneficiarioResponseTo> update(@RequestBody @Valid BeneficiarioUpdateRequestTo form) {
        return ResponseEntity.ok().body(service.update(form));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
