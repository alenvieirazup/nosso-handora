package br.com.zup.edu.handora;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import br.com.zup.edu.handora.model.Curso;
import br.com.zup.edu.handora.model.Pessoa;
import br.com.zup.edu.handora.repository.CursoRepository;
import br.com.zup.edu.handora.repository.PessoaRepository;

@Component
@Profile("default")
public class DataLoader implements CommandLineRunner {

    private final CursoRepository cursoRepository;

    private final PessoaRepository pessoaRepository;

    public DataLoader(CursoRepository cursoRepository, PessoaRepository pessoaRepository) {
        this.cursoRepository = cursoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Curso curso1 = new Curso(
            "CRUD e Atualizações Concorrentes com JPA/Hibernate",
            "Aprenda a desenhar e implementar APIs REST que performam em ambientes de alta "
                    + "concorrência. Aprenda como funciona as principais estratégias de locking "
                    + "em bancos de dados relacionais, como tirar proveito da JPA e Hibernate ao "
                    + "escrever lógicas de negócio seguras e consistentes em ambientes "
                    + "concorrentes, e também a definir constraints de unicidade no banco de dados.",
            true, 1
        );

        Curso curso2 = new Curso(
            "CRUD e Relacionamentos com Java, Spring Boot e JPA/Hibernate",
            "Aprenda sobre HTTP, REST, Spring Boot e JPA nesse módulo sobre CRUDs com "
                    + "Java. Modele entidades, faça queries para listagem e detalhes de objetos. "
                    + "Aprenda como funciona o protocolo HTTP e como usar o Spring Boot para "
                    + "implementar comportamentos para cada um dos verbos HTTP.",
            1
        );

        Curso curso3 = new Curso(
            "Comunicação na Empresa",
            "Conheça mais sobre os princípios de comunicação assertiva e não violenta "
                    + "que devem nortear suas interações com pessoas mais júnior, pares, "
                    + "lideranças e pessoas com outras expertises. Monte apresentações de impacto "
                    + "para o seu time, outros zuppers e clientes. Lide com requisitos e prazos.",
            1
        );

        Pessoa pessoa1 = new Pessoa("Eloy");
        Pessoa pessoa2 = new Pessoa("Denes");

        cursoRepository.save(curso1);
        cursoRepository.save(curso2);
        cursoRepository.save(curso3);

        pessoaRepository.save(pessoa1);
        pessoaRepository.save(pessoa2);
    }

}
