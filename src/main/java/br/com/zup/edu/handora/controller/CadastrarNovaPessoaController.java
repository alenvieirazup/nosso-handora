package br.com.zup.edu.handora.controller;

import br.com.zup.edu.handora.model.Pessoa;
import br.com.zup.edu.handora.repository.PessoaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class CadastrarNovaPessoaController {

    private PessoaRepository pessoaRepository;

    public CadastrarNovaPessoaController(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @PostMapping("/pessoas")
    public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastrarNovaPessoaRequest request,
                                       UriComponentsBuilder uriComponentsBuilder) {

        if (pessoaRepository.existsByCpf(request.getCpf())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe uma pessoa cadastrada com o CPF informado");
        }

        Pessoa pessoa = request.toModel();

        pessoaRepository.save(pessoa);

        URI location = uriComponentsBuilder.path("/pessoas/{id}")
                .buildAndExpand(pessoa.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }
}
