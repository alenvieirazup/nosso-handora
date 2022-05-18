package br.com.zup.edu.handora.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.zup.edu.handora.exception.CursoInativoException;
import br.com.zup.edu.handora.exception.CursoSemVagaException;
import br.com.zup.edu.handora.exception.PessoaJaMatriculadaException;

@Entity
@Table(name = "cursos")
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

    @OneToOne(mappedBy = "curso", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Turma turma;

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Curso() {}

    public Curso(String nome, String descricao, Integer numeroDeVagas) {
        this.nome = nome;
        this.descricao = descricao;
        this.numeroDeVagas = numeroDeVagas;
        this.turma = new Turma(this);
    }

    public Curso(String nome, String descricao, Boolean ativo, Integer numeroDeVagas) {
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = ativo;
        this.numeroDeVagas = numeroDeVagas;
        this.turma = new Turma(this);
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

    public Turma getTurma() {
        return turma;
    }

    public void adicionarPessoa(Pessoa pessoa) {
        this.turma.adicionar(pessoa);
    }

    public boolean isVagas() {
        return this.numeroDeVagas <= this.turma.tamanho();
    }

    public boolean isMatriculada(Pessoa pessoa) {
        return this.turma.contem(pessoa);
    }

    public void matricular(Pessoa pessoa) {
        if (!this.ativo)
            throw new CursoInativoException("Curso inativo");
        if (isMatriculada(pessoa))
            throw new PessoaJaMatriculadaException("Pessoa já matriculada");
        if (isVagas())
            throw new CursoSemVagaException("Curso sem vagas");

        this.adicionarPessoa(pessoa);
    }

}
