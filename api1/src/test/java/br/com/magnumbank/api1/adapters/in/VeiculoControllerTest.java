package br.com.magnumbank.api1.adapters.in;

import br.com.magnumbank.api1.adapters.in.dto.UpdateVeiculoRequest;
import br.com.magnumbank.api1.adapters.in.dto.VeiculoResponse;
import br.com.magnumbank.api1.application.VeiculoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(VeiculoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class VeiculoControllerTest {

        @Autowired
        MockMvc mockMvc;
        @Autowired
        ObjectMapper objectMapper;

        @MockitoBean
        VeiculoService veiculoService;

    @Test
    void shouldReturnStatus200() throws Exception{


        Long id = 1L;
        var request = new UpdateVeiculoRequest("Ecosport", "branco");
        var response = new VeiculoResponse(1L, "123", "Ecosport", "branco");

        Mockito.when(veiculoService.updateVeiculo(Mockito.eq(id), Mockito.any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/veiculos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.codigo").value("123"))
                .andExpect(jsonPath("$.modelo").value("Ecosport"))
                .andExpect(jsonPath("$.observacoes").value("branco"));

        var captor = ArgumentCaptor.forClass(UpdateVeiculoRequest.class);
        verify(veiculoService).updateVeiculo(Mockito.eq(id), captor.capture());
        assertThat(captor.getValue().modelo()).isEqualTo("Ecosport");
        assertThat(captor.getValue().observacoes()).isEqualTo("branco");
    }

    @Test
    void shouldReturnStatus400WhenEmpty() throws Exception{
        Long id = 1L;
        var invalidJson = """ 
                {"modelo":"", "observacoes":"branco"} 
                """;

        mockMvc.perform(put("/api/v1/veiculos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());

        verify(veiculoService, never()).updateVeiculo(Mockito.anyLong(), Mockito.any());
    }
}
