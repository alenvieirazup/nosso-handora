package br.com.zup.edu.handora.controller;

import br.com.zup.edu.handora.model.Curso;
import br.com.zup.edu.handora.repository.CursoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class CursoController {

    private final CursoRepository repository;

    public CursoController(CursoRepository repository) {
        this.repository = repository;
    }

    // Create: POST -> 201(Normal),  202(Assíncrono)
    @PostMapping("/api/cursos")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid NovoCursoRequest request,
                                    UriComponentsBuilder uriComponentsBuilder) {
        Curso curso = request.toModel();
        repository.save(curso);
        URI location = uriComponentsBuilder.path("/api/cursos/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    // Read: GET -> 200(body)
    @GetMapping("/api/cursos/{id}")
    public ResponseEntity<CursoResponse> consulta(@PathVariable Long id) {
        Curso curso = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Curso não encontrado"));
        return ResponseEntity.ok(new CursoResponse(curso));
    }

}
