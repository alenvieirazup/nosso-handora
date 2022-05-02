package br.com.zup.edu.handora.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.zup.edu.handora.model.Curso;

public interface CursoRepository
        extends JpaRepository<Curso, Long>, JpaSpecificationExecutor<Curso> {}
