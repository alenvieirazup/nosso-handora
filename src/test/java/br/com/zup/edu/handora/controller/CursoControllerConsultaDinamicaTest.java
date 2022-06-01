package br.com.zup.edu.handora.controller;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import br.com.zup.edu.handora.model.Curso;
import br.com.zup.edu.handora.repository.CursoRepository;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
public class CursoControllerConsultaDinamicaTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CursoRepository cursoRepository;

    @BeforeEach
    void setUp() {
        cursoRepository.deleteAll();
    }

    @Test
    void deveRetornarUmaColecaoDeCursosCadastrados() throws Exception {
        // cenário (given)
        //
        Curso curso = new Curso(
            "Testes de Integração", "Aprendendo testes de integração com Java", 20
        );
        cursoRepository.save(curso);

        BuscaDinamicaCursoRequest buscaDinamicaCursoRequest = new BuscaDinamicaCursoRequest(
            List.of("Integração"), "Aprendendo", null
        );

        String payload = objectMapper.writeValueAsString(buscaDinamicaCursoRequest);

        MockHttpServletRequestBuilder request = get("/api/cursos").contentType(APPLICATION_JSON)
                                                                  .content(payload);

        // ação (when) e corretude (then)
        //
        String response = mockMvc.perform(request)
                                 .andExpect(status().isOk())
                                 .andReturn()
                                 .getResponse()
                                 .getContentAsString(UTF_8);

        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<BuscaDinamicaCursoResponse> cursosResponse = objectMapper.readValue(
            response,
            typeFactory.constructCollectionType(List.class, BuscaDinamicaCursoResponse.class)
        );

        assertThat(cursosResponse).hasSize(1)
                                  .extracting("nome", "descricao", "numeroDeVagas")
                                  .contains(
                                      new Tuple(
                                          curso.getNome(), curso.getDescricao(),
                                          curso.getNumeroDeVagas()
                                      )
                                  );
    }

    @Test
    void deveRetornarUmaColecaoDeCursosVazia() throws Exception {
        // cenário (given)
        //
        BuscaDinamicaCursoRequest buscaDinamicaCursoRequest = new BuscaDinamicaCursoRequest(
            List.of("Integração"), "Aprendendo", null
        );

        String payload = objectMapper.writeValueAsString(buscaDinamicaCursoRequest);

        MockHttpServletRequestBuilder request = get("/api/cursos").contentType(APPLICATION_JSON)
                                                                  .content(payload);

        // ação (when) e corretude (then)
        //
        String response = mockMvc.perform(request)
                                 .andExpect(status().isOk())
                                 .andReturn()
                                 .getResponse()
                                 .getContentAsString(UTF_8);

        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<BuscaDinamicaCursoResponse> cursosResponse = objectMapper.readValue(
            response,
            typeFactory.constructCollectionType(List.class, BuscaDinamicaCursoResponse.class)
        );

        assertThat(cursosResponse).isEmpty();
    }

}
