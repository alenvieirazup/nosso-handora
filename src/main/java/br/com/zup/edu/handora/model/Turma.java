package br.com.zup.edu.handora.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "turmas")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Curso curso;

    @ManyToMany(mappedBy = "turmas",fetch = FetchType.EAGER)
    private Set<Pessoa> pessoas = new HashSet<>();

    @Column(nullable = false)
    private boolean vagasDisponiveis;

    @Version
    private int versao;

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Turma() {}

    public Turma(Curso curso) {
        this.curso = curso;
        this.vagasDisponiveis = this.curso.getNumeroDeVagas() > 0;
    }

    public Long getId() {
        return id;
    }

    public void adicionar(Pessoa pessoa) {
        this.pessoas.add(pessoa);
        pessoa.adicionarTurma(this);
        this.vagasDisponiveis = this.curso.getNumeroDeVagas() > this.tamanho();
    }

    public Integer tamanho() {
        return this.pessoas.size();
    }

    public boolean contem(Pessoa pessoa) {
        return this.pessoas.contains(pessoa);
    }

    public Set<Pessoa> getPessoas() {
        return pessoas;
    }
}
