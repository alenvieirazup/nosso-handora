package br.com.zup.edu.handora.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @ManyToMany
    @JoinTable(name = "pessoa_turma", joinColumns = @JoinColumn(name = "pessoa_id"), inverseJoinColumns = @JoinColumn(name = "turma_id"))
    private Set<Turma> turmas;

    public Pessoa(String nome) {
        this.nome = nome;
    }

    /**
     * @deprecated Construtor para uso exclusivo do Hibernate.
     */
    @Deprecated
    public Pessoa() {}

    public Long getId() {
        return id;
    }

    public void adicionarTurma(Turma turma) {
        this.turmas.add(turma);
    }

}
