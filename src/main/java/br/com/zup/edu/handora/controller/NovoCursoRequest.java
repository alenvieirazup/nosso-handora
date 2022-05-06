package br.com.zup.edu.handora.controller;

import br.com.zup.edu.handora.model.Curso;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NovoCursoRequest {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotNull
    @Positive
    private Integer numeroDeVagas;

    public NovoCursoRequest() {
    }

    public NovoCursoRequest(String nome, String descricao, Integer numeroDeVagas) {
        this.nome = nome;
        this.descricao = descricao;
        this.numeroDeVagas = numeroDeVagas;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getNumeroDeVagas() {
        return numeroDeVagas;
    }

    public Curso toModel() {
        return new Curso(nome, descricao, numeroDeVagas);
    }

}