package br.com.zup.edu.handora.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "UK_CPF", columnNames = {"cpf"}))
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 11)
    private String cpf;

    @ManyToMany
    @JoinTable(name = "pessoa_turma", joinColumns = @JoinColumn(name = "pessoa_id"), inverseJoinColumns = @JoinColumn(name = "turma_id"))
    private Set<Turma> turmas;

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
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
