package br.com.zup.edu.handora.controller;

import br.com.zup.edu.handora.exception.ErroPadronizado;
import br.com.zup.edu.handora.model.Curso;
import br.com.zup.edu.handora.model.Pessoa;
import br.com.zup.edu.handora.repository.CursoRepository;
import br.com.zup.edu.handora.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

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
class CursoControllerAtualizarTest {

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
    void naoDeveAtualizarUmCursoNaoCadastrado() throws Exception {
        // cenário (given)
        //
        AtualizaCursoRequest atualizaCursoRequest = new AtualizaCursoRequest(
                "Atualizações Concorrentes com JPA/Hibernate",
                "Trabalhando com Lock otimista e pessimista",
                6,
                true);

        String payload = objectMapper.writeValueAsString(atualizaCursoRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/cursos/{id}",
                        Integer.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

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
        assertThat(mensagens, containsInAnyOrder("Curso com esse id não cadastrado"));
    }

    @Test
    void naoDeveAtualizarUmCursoComDadosNulos() throws Exception {
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

        AtualizaCursoRequest atualizaCursoRequest = new AtualizaCursoRequest(
                null,
                null,
                null,
                null);

        String payload = objectMapper.writeValueAsString(atualizaCursoRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/cursos/{id}",
                        curso.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
                .header(
                        "Accept-Language", "pt-br"
                );

        // ação (when) e corretude (then)
        //
        String response = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString(UTF_8);

        ErroPadronizado erroPadronizado = objectMapper.readValue(response, ErroPadronizado.class);
        List<String> mensagens = erroPadronizado.getMensagens();

        assertEquals(4, mensagens.size());
        assertThat(
                mensagens,
                containsInAnyOrder(
                        "nome: não deve estar em branco",
                        "descricao: não deve estar em branco",
                        "numeroDeVagas: não deve ser nulo",
                        "ativo: não deve ser nulo"
                )
        );

    }

    @Test
    void naoDeveAtualizarUmCursoComDadosInvalidos() throws Exception {
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

        AtualizaCursoRequest atualizaCursoRequest = new AtualizaCursoRequest(
                "Atualizações Concorrentes com JPA/Hibernate",
                "Trabalhando com Lock otimista e pessimista",
                -1,
                true);

        String payload = objectMapper.writeValueAsString(atualizaCursoRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/cursos/{id}",
                        curso.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
                .header(
                        "Accept-Language", "pt-br"
                );

        // ação (when) e corretude (then)
        //
        String response = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString(UTF_8);

        ErroPadronizado erroPadronizado = objectMapper.readValue(response, ErroPadronizado.class);
        List<String> mensagens = erroPadronizado.getMensagens();

        assertEquals(1, mensagens.size());
        assertThat(
                mensagens,
                containsInAnyOrder(
                        "numeroDeVagas: deve ser maior que 0"
                )
        );

    }


    @Test
    void naoDeveAtualizarUmCursoParaNumeroDeVagasMenorQueMatriculados() throws Exception {
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

        Pessoa pessoa1 = new Pessoa("Eloy", "47690351018");
        Pessoa pessoa2 = new Pessoa("Denes", "40553950002");

        curso.matricular(pessoa1);
        curso.matricular(pessoa2);

        cursoRepository.save(curso);
        pessoaRepository.saveAll(List.of(pessoa1, pessoa2));

        AtualizaCursoRequest atualizaCursoRequest = new AtualizaCursoRequest(
                "Atualizações Concorrentes com JPA/Hibernate",
                "Trabalhando com Lock otimista e pessimista",
                1,
                true);

        String payload = objectMapper.writeValueAsString(atualizaCursoRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/cursos/{id}",
                        curso.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
                .header(
                        "Accept-Language", "pt-br"
                );

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
        assertThat(mensagens, containsInAnyOrder("Não permitido diminuir número de vagas do curso " +
                "para uma quantidade menor que o número de pessoas matriculadas"));
    }

    @Test
    void deveAtualizarUmCurso() throws Exception {
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

        Pessoa pessoa1 = new Pessoa("Eloy", "47690351018");
        Pessoa pessoa2 = new Pessoa("Denes", "40553950002");

        curso.matricular(pessoa1);
        curso.matricular(pessoa2);

        cursoRepository.save(curso);
        pessoaRepository.saveAll(List.of(pessoa1,pessoa2));


        AtualizaCursoRequest atualizaCursoRequest = new AtualizaCursoRequest(
                "Atualizações Concorrentes com JPA/Hibernate",
                "Trabalhando com Lock otimista e pessimista",
                2,
                true);

        String payload = objectMapper.writeValueAsString(atualizaCursoRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/cursos/{id}",
                        curso.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
                .header(
                        "Accept-Language", "pt-br"
                );

        // ação (when) e corretude (then)
        //

        mockMvc.perform(request)
                .andExpect(
                        status().isNoContent()
                );

        Optional<Curso> possivelCurso = cursoRepository.findById(curso.getId());
        assertTrue(possivelCurso.isPresent());

        Curso cursoAposAtualizacao = possivelCurso.get();
        assertEquals(atualizaCursoRequest.getNome(),cursoAposAtualizacao.getNome());
        assertEquals(atualizaCursoRequest.getDescricao(),cursoAposAtualizacao.getDescricao());
        assertEquals(atualizaCursoRequest.getAtivo(),cursoAposAtualizacao.getAtivo());
        assertEquals(atualizaCursoRequest.getNumeroDeVagas(),cursoAposAtualizacao.getNumeroDeVagas());
    }

}