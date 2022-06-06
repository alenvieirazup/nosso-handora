package br.com.zup.edu.handora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.edu.handora.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    boolean existsByCpf(String cpf);
}
