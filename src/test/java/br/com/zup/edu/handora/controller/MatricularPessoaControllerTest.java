package br.com.zup.edu.handora.controller;

import br.com.zup.edu.handora.exception.CursoInativoException;
import br.com.zup.edu.handora.exception.CursoSemVagaException;
import br.com.zup.edu.handora.exception.ErroPadronizado;
import br.com.zup.edu.handora.exception.PessoaJaMatriculadaException;
import br.com.zup.edu.handora.model.Curso;
import br.com.zup.edu.handora.model.Pessoa;
import br.com.zup.edu.handora.repository.CursoRepository;
import br.com.zup.edu.handora.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class MatricularPessoaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @BeforeEach
    void setUp() {
        pessoaRepository.deleteAll();
        cursoRepository.deleteAll();
    }

    @Test
    void naoDeveMatricularUmaPessoaEmCursoNaoCadastrado() throws Exception {
        // cenário (given)
        //

        Pessoa pessoa = new Pessoa("Eloy", "47690351018");
        pessoaRepository.save(pessoa);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch("/api/cursos/{idCurso}/pessoas/{idPessoa}",
                Long.MAX_VALUE, pessoa.getId()).contentType(MediaType.APPLICATION_JSON);

        // ação (when) e corretude (then)
        //

        String response = mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString(UTF_8);

        ErroPadronizado erroPadronizado = objectMapper.readValue(response, ErroPadronizado.class);
        List<String> mensagens = erroPadronizado.getMensagens();

        assertEquals(1, mensagens.size());
        assertThat(mensagens, containsInAnyOrder("Curso não encontrado."));

    }

    @Test
    void naoDeveMatricularUmaPessoaNaoCadastradaEmUmCurso() throws Exception {
        // cenário (given)
        //

        Curso curso = new Curso(
                "CRUD e Atualizações Concorrentes com JPA/Hibernate",
                "Aprenda a desenhar e implementar APIs REST que performam em ambientes de alta "
                        + "concorrência. Aprenda como funciona as principais estratégias de locking "
                        + "em bancos de dados relacionais, como tirar proveito da JPA e Hibernate ao "
                        + "escrever lógicas de negócio seguras e consistentes em ambientes "
                        + "concorrentes, e também a definir constraints de unicidade no banco de dados.",
                true,
                5
        );

        cursoRepository.save(curso);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch("/api/cursos/{idCurso}/pessoas/{idPessoa}",
                curso.getId(), Long.MAX_VALUE).contentType(MediaType.APPLICATION_JSON);

        // ação (when) e corretude (then)
        //

        String response = mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString(UTF_8);

        ErroPadronizado erroPadronizado = objectMapper.readValue(response, ErroPadronizado.class);
        List<String> mensagens = erroPadronizado.getMensagens();

        assertEquals(1, mensagens.size());
        assertThat(mensagens, containsInAnyOrder("Pessoa não encontrada."));

    }

    @Test
    void naoDeveMatricularUmaPessoaEmCursoInativo() throws Exception {
        // cenário (given)
        //

        Pessoa pessoa = new Pessoa("Eloy", "47690351018");
        pessoaRepository.save(pessoa);

        Curso curso = new Curso(
                "CRUD e Atualizações Concorrentes com JPA/Hibernate",
                "Aprenda a desenhar e implementar APIs REST que performam em ambientes de alta "
                        + "concorrência. Aprenda como funciona as principais estratégias de locking "
                        + "em bancos de dados relacionais, como tirar proveito da JPA e Hibernate ao "
                        + "escrever lógicas de negócio seguras e consistentes em ambientes "
                        + "concorrentes, e também a definir constraints de unicidade no banco de dados.",
                false,
                5
        );

        cursoRepository.save(curso);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch("/api/cursos/{idCurso}/pessoas/{idPessoa}",
                curso.getId(), pessoa.getId()).contentType(MediaType.APPLICATION_JSON);

        // ação (when) e corretude (then)
        //

        String response = mockMvc.perform(request)
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
                .getResponse()
                .getContentAsString(UTF_8);

        ErroPadronizado erroPadronizado = objectMapper.readValue(response, ErroPadronizado.class);
        List<String> mensagens = erroPadronizado.getMensagens();

        assertEquals(1, mensagens.size());
        assertThat(mensagens, containsInAnyOrder("Curso inativo"));
    }

    @Test
    void naoDeveMatricularUmaPessoaEmCursoSemVagas() throws Exception {
        // cenário (given)
        //

        Curso curso = new Curso(
                "CRUD e Atualizações Concorrentes com JPA/Hibernate",
                "Aprenda a desenhar e implementar APIs REST que performam em ambientes de alta "
                        + "concorrência. Aprenda como funciona as principais estratégias de locking "
                        + "em bancos de dados relacionais, como tirar proveito da JPA e Hibernate ao "
                        + "escrever lógicas de negócio seguras e consistentes em ambientes "
                        + "concorrentes, e também a definir constraints de unicidade no banco de dados.",
                true,
                1
        );

        Pessoa pessoa1 = new Pessoa("Eloy", "47690351018");
        Pessoa pessoa2 = new Pessoa("Denes", "40553950002");

        curso.matricular(pessoa1);

        cursoRepository.save(curso);
        pessoaRepository.saveAll(List.of(pessoa1, pessoa2));


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch("/api/cursos/{idCurso}/pessoas/{idPessoa}",
                curso.getId(), pessoa2.getId()).contentType(MediaType.APPLICATION_JSON);

        // ação (when) e corretude (then)
        //

        String response = mockMvc.perform(request)
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
                .getResponse()
                .getContentAsString(UTF_8);

        ErroPadronizado erroPadronizado = objectMapper.readValue(response, ErroPadronizado.class);
        List<String> mensagens = erroPadronizado.getMensagens();

        assertEquals(1, mensagens.size());
        assertThat(mensagens, containsInAnyOrder("Curso sem vagas"));
    }

    @Test
    void naoDeveMatricularUmaPessoaJaMatriculadaEmUmCurso() throws Exception {
        // cenário (given)
        //

        Curso curso = new Curso(
                "CRUD e Atualizações Concorrentes com JPA/Hibernate",
                "Aprenda a desenhar e implementar APIs REST que performam em ambientes de alta "
                        + "concorrência. Aprenda como funciona as principais estratégias de locking "
                        + "em bancos de dados relacionais, como tirar proveito da JPA e Hibernate ao "
                        + "escrever lógicas de negócio seguras e consistentes em ambientes "
                        + "concorrentes, e também a definir constraints de unicidade no banco de dados.",
                true,
                1
        );

        Pessoa pessoa1 = new Pessoa("Eloy", "47690351018");

        curso.matricular(pessoa1);

        cursoRepository.save(curso);
        pessoaRepository.save(pessoa1);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch("/api/cursos/{idCurso}/pessoas/{idPessoa}",
                curso.getId(), pessoa1.getId()).contentType(MediaType.APPLICATION_JSON);

        // ação (when) e corretude (then)
        //

        String response = mockMvc.perform(request)
                .andExpect(status().isUnprocessableEntity())
                .andReturn()
                .getResponse()
                .getContentAsString(UTF_8);

        ErroPadronizado erroPadronizado = objectMapper.readValue(response, ErroPadronizado.class);
        List<String> mensagens = erroPadronizado.getMensagens();

        assertEquals(1, mensagens.size());
        assertThat(mensagens, containsInAnyOrder("Pessoa já matriculada"));
    }

    @Test
    void deveMatricularUmaPessoaEmUmCurso() throws Exception {
        // cenário (given)
        //

        Curso curso = new Curso(
                "CRUD e Atualizações Concorrentes com JPA/Hibernate",
                "Aprenda a desenhar e implementar APIs REST que performam em ambientes de alta "
                        + "concorrência. Aprenda como funciona as principais estratégias de locking "
                        + "em bancos de dados relacionais, como tirar proveito da JPA e Hibernate ao "
                        + "escrever lógicas de negócio seguras e consistentes em ambientes "
                        + "concorrentes, e também a definir constraints de unicidade no banco de dados.",
                true,
                1
        );

        Pessoa pessoa1 = new Pessoa("Eloy", "47690351018");

        cursoRepository.save(curso);
        pessoaRepository.save(pessoa1);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.patch("/api/cursos/{idCurso}/pessoas/{idPessoa}",
                curso.getId(), pessoa1.getId()).contentType(MediaType.APPLICATION_JSON);

        // ação (when) e corretude (then)
        //

        mockMvc.perform(request)
                .andExpect(
                        status().isNoContent()
                );

        Curso cursoRecuperado = cursoRepository.findById(curso.getId()).get();

        assertTrue(cursoRecuperado.getTurma().contem(pessoa1));
    }
}