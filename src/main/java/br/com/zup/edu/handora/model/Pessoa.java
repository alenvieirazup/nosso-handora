package br.com.zup.edu.handora.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "pessoa_curso",
            joinColumns = @JoinColumn(name = "pessoa_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private Set<Curso> cursos = new HashSet<>();

    public Pessoa(String nome) {
        this.nome = nome;
    }

    /**
     * @deprecated Construtor para uso exclusivo do Hibernate.
     */
    @Deprecated
    public Pessoa() {
    }

    public Long getId() {
        return id;
    }

    public void adicionarCurso(Curso curso) {
        this.cursos.add(curso);
    }
}
