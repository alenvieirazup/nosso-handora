package br.com.zup.edu.handora.controller;

import java.util.List;

public class BuscaDinamicaCursoRequest {

    private List<String> nomes;
    private String descricao;

    private Boolean ativo;

    public BuscaDinamicaCursoRequest() {}

    public BuscaDinamicaCursoRequest(List<String> nomes, String descricao, Boolean ativo) {
        this.nomes = nomes;
        this.descricao = descricao;
        this.ativo = ativo;
    }

    public List<String> getNomes() {
        return nomes;
    }

    public String getDescricao() {
        return descricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }
}
