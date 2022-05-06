package br.com.zup.edu.handora.repository;

import br.com.zup.edu.handora.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa,Long> {
}
