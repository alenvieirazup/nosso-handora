package br.com.zup.edu.handora.controller;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.edu.handora.exception.ErroPadronizado;
import br.com.zup.edu.handora.model.Curso;
import br.com.zup.edu.handora.repository.CursoRepository;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
public class CursoControllerConsultarTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CursoRepository cursoRepository;

    private Curso curso;

    @BeforeEach
    void setUp() {
        cursoRepository.deleteAll();

        curso = new Curso("Testes de Integração", "Aprendendo testes de integração com Java", 20);
        cursoRepository.save(curso);
    }

    @Test
    void deveConsultarUmCurso() throws Exception {
        // cenário (given)
        //
        MockHttpServletRequestBuilder request = get("/api/cursos/{id}", curso.getId()).contentType(
            APPLICATION_JSON
        );

        // ação (when) e corretude (then)
        //
        String response = mockMvc.perform(request)
                                 .andExpect(status().isOk())
                                 .andReturn()
                                 .getResponse()
                                 .getContentAsString(UTF_8);

        Curso cursoConsulta = objectMapper.readValue(response, Curso.class);

        assertThat(cursoConsulta).extracting("nome", "descricao", "numeroDeVagas")
                                 .contains(
                                     curso.getNome(), curso.getDescricao(), curso.getNumeroDeVagas()
                                 );
    }

    @Test
    void naoDeveConsultarUmCursoQueNaoEstaCadastrado() throws Exception {
        // cenário (given)
        //
        MockHttpServletRequestBuilder request = get(
            "/api/cursos/{id}", Integer.MAX_VALUE
        ).contentType(APPLICATION_JSON);

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
        assertThat(mensagens, containsInAnyOrder("Curso não encontrado"));
    }

}
