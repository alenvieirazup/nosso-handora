package br.com.zup.edu.handora.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "turmas")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Curso curso;

    @ManyToMany(mappedBy = "turmas")
    private Set<Pessoa> pessoas = new HashSet<>();

    @Column(nullable = false)
    private Integer vagasDisponiveis;

    @Version
    private int versao;

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Turma() {}

    public Turma(Curso curso) {
        this.curso = curso;
        this.vagasDisponiveis = curso.getNumeroDeVagas();
    }

    public Long getId() {
        return id;
    }

    public void adicionar(Pessoa pessoa) {
        this.pessoas.add(pessoa);
        pessoa.adicionarTurma(this);
        this.vagasDisponiveis--;
    }

    public Integer tamanho() {
        return this.pessoas.size();
    }

    public boolean contem(Pessoa pessoa) {
        return this.pessoas.contains(pessoa);
    }

}
