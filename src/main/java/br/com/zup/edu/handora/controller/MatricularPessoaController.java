package br.com.zup.edu.handora.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.edu.handora.model.Curso;
import br.com.zup.edu.handora.model.Pessoa;
import br.com.zup.edu.handora.repository.CursoRepository;
import br.com.zup.edu.handora.repository.PessoaRepository;

@RestController
public class MatricularPessoaController {

    private final CursoRepository cursoRepository;
    private final PessoaRepository pessoaRepository;

    public MatricularPessoaController(CursoRepository cursoRepository,
                                      PessoaRepository pessoaRepository) {
        this.cursoRepository = cursoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @PatchMapping("/api/cursos/{idCurso}/pessoas/{idPessoa}")
    public ResponseEntity<?> matricular(@PathVariable("idCurso") Long idCurso,
                                        @PathVariable("idPessoa") Long idPessoa) {

        Curso curso = cursoRepository.findById(idCurso)
                                     .orElseThrow(
                                         () -> new ResponseStatusException(
                                             HttpStatus.NOT_FOUND, "Curso não encontrado."
                                         )
                                     );

        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                                        .orElseThrow(
                                            () -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "Pessoa não encontrada."
                                            )
                                        );

        curso.matricular(pessoa);

        cursoRepository.save(curso);

        return ResponseEntity.noContent().build();
    }

}
