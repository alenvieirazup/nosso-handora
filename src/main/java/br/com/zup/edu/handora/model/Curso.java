package br.com.zup.edu.handora.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Lob
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private LocalDateTime criadoem = LocalDateTime.now();

    public Curso() {
    }

    public Curso(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
