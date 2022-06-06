package br.com.zup.edu.handora.controller;

import br.com.zup.edu.handora.exception.ErroPadronizado;
import br.com.zup.edu.handora.model.Pessoa;
import br.com.zup.edu.handora.repository.PessoaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
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

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class CadastrarNovaPessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PessoaRepository pessoaRepository;

    @BeforeEach
    void setUp() {
        this.pessoaRepository.deleteAll();
    }

    @Test
    @DisplayName("Não deve cadastrar uma pessoa com dados nulos")
    void naoDeveCadastrarUmaPessoaComDadosNulos() throws Exception {

        // Cenário
        CadastrarNovaPessoaRequest cadastrarNovaPessoaRequest = new CadastrarNovaPessoaRequest(
                null,
                null
        );

        String payloadRequest = mapper.writeValueAsString(cadastrarNovaPessoaRequest);

        MockHttpServletRequestBuilder request = post("/pessoas")
                .header("Accept-Language", "pt-br")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadRequest);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request).
                andExpect(
                        status().isBadRequest()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        ErroPadronizado erroPadronizado = mapper.readValue(payloadResponse, ErroPadronizado.class);

        // Asserts
        assertThat(erroPadronizado.getMensagens())
                .hasSize(2)
                .contains("nome: não deve estar em branco","cpf: não deve estar em branco");

    }

    @Test
    @DisplayName("Não deve cadastrar uma pessoa com dados inválidos")
    void naoDeveCadastrarUmaPessoaComDadosInvalidos() throws Exception {

        // Cenário
        CadastrarNovaPessoaRequest cadastrarNovaPessoaRequest = new CadastrarNovaPessoaRequest(
                "RafaellaRafaellaRafaellaRafaellaRafaellaRafaellaRafaellaRafaellaRafaellaRafaellaRafaellaRafaellaRafaellaRafaellaRafaellaRafaellaRafaellaRafaellaRafaella",
                "12345678910"
        );

        String payloadRequest = mapper.writeValueAsString(cadastrarNovaPessoaRequest);

        MockHttpServletRequestBuilder request = post("/pessoas")
                .header("Accept-Language", "pt-br")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadRequest);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request).
                andExpect(
                        status().isBadRequest()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        ErroPadronizado erroPadronizado = mapper.readValue(payloadResponse, ErroPadronizado.class);

        // Asserts
        assertThat(erroPadronizado.getMensagens())
                .hasSize(2)
                .contains("nome: tamanho deve ser entre 0 e 100","cpf: número do registro de contribuinte individual brasileiro (CPF) inválido");

    }

    @Test
    @DisplayName("Não deve cadastrar uma pessoa cujo CPF já foi cadastrado")
    void naoDeveCadastrarUmaPessoaCujoCPFJaFoiCadastrado() throws Exception {

        // Cenário
        Pessoa pessoa = new Pessoa("Miguel", "51112820094");
        this.pessoaRepository.save(pessoa);

        CadastrarNovaPessoaRequest cadastrarNovaPessoaRequest = new CadastrarNovaPessoaRequest(
                "Rafaella",
                "51112820094"
        );

        String payloadRequest = mapper.writeValueAsString(cadastrarNovaPessoaRequest);

        MockHttpServletRequestBuilder request = post("/pessoas")
                .header("Accept-Language", "pt-br")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadRequest);

        // Ação e Corretude
        String payloadResponse = mockMvc.perform(request).
                andExpect(
                        status().isUnprocessableEntity()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        ErroPadronizado erroPadronizado = mapper.readValue(payloadResponse, ErroPadronizado.class);

        // Asserts
        assertThat(erroPadronizado.getMensagens())
                .hasSize(1)
                .contains("Já existe uma pessoa cadastrada com o CPF informado");

    }

    @Test
    @DisplayName("Deve cadastrar uma pessoa")
    void deveCadastrarUmaPessoa() throws Exception {

        // Cenário
        CadastrarNovaPessoaRequest cadastrarNovaPessoaRequest = new CadastrarNovaPessoaRequest(
                "Rafaella",
                "51112820094"
        );

        String payloadRequest = mapper.writeValueAsString(cadastrarNovaPessoaRequest);

        MockHttpServletRequestBuilder request = post("/pessoas")
                .header("Accept-Language", "pt-br")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadRequest);

        // Ação e Corretude
        mockMvc.perform(request).
                andExpect(
                        status().isCreated()
                )
                .andExpect(
                        redirectedUrlPattern("http://localhost/pessoas/*")
                );

        List<Pessoa> pessoas = pessoaRepository.findAll();

        // Asserts
        assertEquals(1,pessoas.size());

    }

}