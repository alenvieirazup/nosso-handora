package br.com.zup.edu.handora.model;

import br.com.zup.edu.handora.exception.CursoInativoException;
import br.com.zup.edu.handora.exception.CursoSemVagaException;
import br.com.zup.edu.handora.exception.PessoaJaMatriculadaException;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
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

    public Curso(String nome, String descricao, Boolean ativo, Integer numeroDeVagas) {
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = ativo;
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

    public boolean isVagas() {
        return this.numeroDeVagas <= this.participantes.size();
    }

    public boolean isMatriculada(Pessoa pessoa) {
        return this.participantes.contains(pessoa);
    }

    public void matricular(Pessoa pessoa) {
        if(!this.ativo) throw new CursoInativoException("Curso inativo");
        if(isMatriculada(pessoa)) throw new PessoaJaMatriculadaException("Pessoa já matriculada");
        if(isVagas()) throw new CursoSemVagaException("Curso sem vagas");
        this.adicionarPessoa(pessoa);
    }

}
