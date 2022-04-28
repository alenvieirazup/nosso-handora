package br.com.zup.edu.handora.repository;

import br.com.zup.edu.handora.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}