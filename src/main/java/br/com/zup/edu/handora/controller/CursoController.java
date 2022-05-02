package br.com.zup.edu.handora.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.edu.handora.model.Curso;
import br.com.zup.edu.handora.repository.CursoRepository;
import br.com.zup.edu.handora.specification.CursoSpecification;

@RestController
public class CursoController {

    private final CursoRepository repository;

    public CursoController(CursoRepository repository) {
        this.repository = repository;
    }

    // Create: POST -> 201(Normal), 202(Assíncrono)
    @PostMapping("/api/cursos")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid NovoCursoRequest request,
                                          UriComponentsBuilder uriComponentsBuilder) {
        Curso curso = request.toModel();
        repository.save(curso);
        URI location = uriComponentsBuilder.path("/api/cursos/{id}")
                                           .buildAndExpand(curso.getId())
                                           .toUri();
        return ResponseEntity.created(location).build();
    }

    // Read: GET -> 200(body)
    @GetMapping("/api/cursos/{id}")
    public ResponseEntity<CursoResponse> consulta(@PathVariable Long id) {
        Curso curso = repository.findById(
            id
        ).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Curso não encontrado"));
        return ResponseEntity.ok(new CursoResponse(curso));
    }

    @GetMapping("/api/cursos")
    public ResponseEntity<List<BuscaDinamicaCursoResponse>> consultaDinamica(@RequestBody BuscaDinamicaCursoRequest request) {
        List<BuscaDinamicaCursoResponse> cursos = repository.findAll(
            Specification.where(
                CursoSpecification.nomes(request.getNomes())
                                  .and(CursoSpecification.descricao(request.getDescricao()))
            )
        ).stream().map(BuscaDinamicaCursoResponse::new).collect(Collectors.toList());

        return ResponseEntity.ok(cursos);
    }

}
