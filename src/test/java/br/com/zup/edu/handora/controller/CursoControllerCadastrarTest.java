package br.com.zup.edu.handora.controller;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.edu.handora.exception.ErroPadronizado;
import br.com.zup.edu.handora.model.Curso;
import br.com.zup.edu.handora.repository.CursoRepository;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
public class CursoControllerCadastrarTest {

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
    void deveCadastrarUmCurso() throws Exception {
        // cenário (given)
        //
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        NovoCursoRequest novoCursoRequest = new NovoCursoRequest(
            "Testes de Integração", "Aprendendo a fazer testes de integração com Java.", 10
        );

        String payload = objectMapper.writeValueAsString(novoCursoRequest);

        MockHttpServletRequestBuilder request = post("/api/cursos").contentType(APPLICATION_JSON)
                                                                   .content(payload);

        // ação (when) e corretude (then)
        //
        mockMvc.perform(request)
               .andExpect(status().isCreated())
               .andExpect(redirectedUrlPattern(baseUrl + "/api/cursos/*"));

        List<Curso> cursos = cursoRepository.findAll();

        assertEquals(1, cursos.size());
    }

    @Test
    void naoDeveCadastrarUmCursoComDadosNulos() throws Exception {
        // cenário (given)
        //
        NovoCursoRequest novoCursoRequest = new NovoCursoRequest(null, null, null);

        String payload = objectMapper.writeValueAsString(novoCursoRequest);

        MockHttpServletRequestBuilder request = post("/api/cursos").contentType(APPLICATION_JSON)
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

        assertEquals(3, mensagens.size());
        assertThat(
            mensagens,
            containsInAnyOrder(
                "nome: não deve estar em branco", "descricao: não deve estar em branco",
                "numeroDeVagas: não deve ser nulo"
            )
        );
    }

    @Test
    void naoDeveCadastrarUmCursoComDadosInvalidos() throws Exception {
        // cenário (given)
        //
        NovoCursoRequest novoCursoRequest = new NovoCursoRequest(
            "Testes de Integração", "Aprendendo a fazer testes de integração com Java.", -1
        );

        String payload = objectMapper.writeValueAsString(novoCursoRequest);

        MockHttpServletRequestBuilder request = post("/api/cursos").contentType(APPLICATION_JSON)
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
        assertThat(mensagens, containsInAnyOrder("numeroDeVagas: deve ser maior que 0"));
    }

}
