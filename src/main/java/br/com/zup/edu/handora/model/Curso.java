package br.com.zup.edu.handora.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    @Lob
    private String descricao;

    @Column(nullable = false)
    private Boolean ativo = false;

    @Column(nullable = false)
    private Integer numeroDeVagas;

    @Column(nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @ManyToMany(mappedBy = "cursos")
    private Set<Pessoa> participantes = new HashSet<>();

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Curso() {}

    public Curso(String nome, String descricao, Integer numeroDeVagas) {
        this.nome = nome;
        this.descricao = descricao;
        this.numeroDeVagas = numeroDeVagas;
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

    public Integer getNumeroDeVagas() {
        return numeroDeVagas;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void adicionarPessoa(Pessoa pessoa) {
        this.participantes.add(pessoa);
        pessoa.adicionarCurso(this);
    }

}
