package br.com.magnumbank.api1.adapters.in;

import br.com.magnumbank.api1.adapters.in.dto.MarcaResponse;
import br.com.magnumbank.api1.adapters.in.dto.VeiculoResponse;
import br.com.magnumbank.api1.application.MarcaService;
import br.com.magnumbank.api1.application.MarcaServicePort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Sinks;

import java.awt.*;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MarcaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MarcaControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    MarcaService marcaService;
    @MockitoBean
    MarcaServicePort marcaServicePort;

    @Test
    void shouldReturnMarcasFipeStatus200() throws Exception{
        Mockito.when(marcaServicePort.buscarMarcasFipe()).thenReturn(List.of("Fiat", "Ford"));

        mockMvc.perform(get("/api/v1/marcas/fipe").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Fiat"))
                .andExpect(jsonPath("$[1]").value("Ford"));
    }

    @Test
    void shouldReturnNoContentWhenEmpty() throws Exception{
        Mockito.when(marcaServicePort.buscarMarcasFipe()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/marcas/fipe"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    void shouldReturnOk() throws Exception{
        var page = new PageImpl<>(
                List.of(new MarcaResponse("1", "Fiat")),
                PageRequest.of(0, 10),
                1);

        Mockito.when(marcaService.getMarcas(any())).thenReturn(page);

        mockMvc.perform(get("/api/v1/marcas?page=0&size=10").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].codigo").value("1"))
                .andExpect(jsonPath("$.content[0].nome").value("Fiat"))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void shouldReturnListaVeiculosPorMarcaStatus200() throws Exception{
        var page = new PageImpl<>(
                List.of(new VeiculoResponse(1L, "123", "Ecosport", "")),
                PageRequest.of(0, 10),
                1);

        Mockito.when(marcaService.listarVeiculosPorMarca(eq("1"), any())).thenReturn(page);

        mockMvc.perform(get("/api/v1/marcas/1/veiculos?page=0&size=10").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].codigo").value("123"))
                .andExpect(jsonPath("$.content[0].modelo").value("Ecosport"))
                .andExpect(jsonPath("$.content[0].observacoes").value(""))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.totalElements").value(1));
    }
}
