package br.com.zup.edu.handora.controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AtualizaCursoRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @NotNull
    @Positive
    private Integer numeroDeVagas;

    @NotNull
    private Boolean ativo;

    public AtualizaCursoRequest() {}

    public AtualizaCursoRequest(String nome, String descricao, Integer numeroDeVagas,
                                Boolean ativo) {
        this.nome = nome;
        this.descricao = descricao;
        this.numeroDeVagas = numeroDeVagas;
        this.ativo = ativo;
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

    public Boolean getAtivo() {
        return ativo;
    }

}
