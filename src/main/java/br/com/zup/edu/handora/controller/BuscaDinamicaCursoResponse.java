package br.com.zup.edu.handora.controller;

import java.time.LocalDateTime;

import br.com.zup.edu.handora.model.Curso;

public class BuscaDinamicaCursoResponse {

    private String nome;
    private String descricao;
    private LocalDateTime criadoEm;

    public BuscaDinamicaCursoResponse() {}

    public BuscaDinamicaCursoResponse(Curso curso) {
        this.nome = curso.getNome();
        this.descricao = curso.getDescricao();
        this.criadoEm = curso.getCriadoEm();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

}
