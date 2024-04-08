package br.com.planosaude.ekan.controller;

import br.com.planosaude.ekan.model.dto.DocumentoUpdateRequestTo;
import br.com.planosaude.ekan.service.DocumentoService;
import br.com.planosaude.ekan.model.dto.DocumentoRequestTo;
import br.com.planosaude.ekan.model.dto.DocumentoResponseTo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("documento")
@RequiredArgsConstructor
public class DocumentoController {

    private final DocumentoService service;

    @PostMapping
    public ResponseEntity<DocumentoResponseTo> save(@RequestBody @Valid DocumentoRequestTo form){
        var savedDocumento = service.save(form);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDocumento.id())
                .toUri();
        return ResponseEntity.created(location).body(savedDocumento);
    }

    @PutMapping
    public ResponseEntity<DocumentoResponseTo> update(@RequestBody @Valid DocumentoUpdateRequestTo form) {
        return ResponseEntity.ok(service.update(form));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoResponseTo> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("list-all")
    public ResponseEntity<Page<DocumentoResponseTo>> listAll(@PageableDefault(size = 10, sort = {"id"}) Pageable page) {
        Page<DocumentoResponseTo> beneficiarios = service.listAll(page);
        return ResponseEntity.ok().body(beneficiarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
