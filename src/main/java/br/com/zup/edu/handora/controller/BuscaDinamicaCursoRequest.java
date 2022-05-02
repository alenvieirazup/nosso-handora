package br.com.zup.edu.handora.controller;

import java.util.List;

public class BuscaDinamicaCursoRequest {

    private List<String> nomes;
    private String descricao;

    public BuscaDinamicaCursoRequest() {}

    public BuscaDinamicaCursoRequest(List<String> nomes, String descricao) {
        this.nomes = nomes;
        this.descricao = descricao;
    }

    public List<String> getNomes() {
        return nomes;
    }

    public String getDescricao() {
        return descricao;
    }

}
