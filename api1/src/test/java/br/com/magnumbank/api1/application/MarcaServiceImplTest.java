package br.com.magnumbank.api1.application;

import br.com.magnumbank.api1.adapters.in.dto.MarcaResponse;
import br.com.magnumbank.api1.adapters.in.dto.VeiculoResponse;
import br.com.magnumbank.api1.domain.Marca;
import br.com.magnumbank.api1.domain.Veiculo;
import br.com.magnumbank.api1.mapper.MarcaMapper;
import br.com.magnumbank.api1.mapper.VeiculoMapper;
import br.com.magnumbank.api1.ports.out.MarcaRepository;
import br.com.magnumbank.api1.ports.out.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MarcaServiceImplTest {

    @Mock
    MarcaRepository marcaRepository;
    @Mock
    VeiculoRepository veiculoRepository;
    @Mock
    MarcaMapper marcaMapper;
    @Mock
    VeiculoMapper veiculoMapper;

    @InjectMocks
    MarcaServiceImpl marcaService;

    @Test
    void shouldReturnMarcaPageable() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nome"));
        var marca = new Marca(1L, "123", "Ford");
        var page = new PageImpl<>(List.of(marca), pageable, 1);

        when(marcaRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(marcaRepository.count()).thenReturn(1L);
        when(marcaMapper.toResponse(marca)).thenReturn(new MarcaResponse("123", "Ford"));

        Page<MarcaResponse> result = marcaService.getMarcas(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).codigo()).isEqualTo("123");
        assertThat(result.getContent().get(0).nome()).isEqualTo("Ford");
        assertThat(result.getTotalElements()).isEqualTo(1L);
    }

    @Test
    void shouldReturnListaDeVeiculosPorMarca() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("modelo"));
        var marca = new Marca(1L, "123", "Ford");
        var veiculo = new Veiculo(1L, "321", "Ecosport", null, marca);
        var page = new PageImpl<>(List.of(veiculo), pageable, 1);

        when(marcaRepository.findByCodigo("123")).thenReturn(Optional.of(marca));
        when(veiculoRepository.findByMarca(eq(marca), any(Pageable.class))).thenReturn(page);
        when(veiculoMapper.toResponse(veiculo)).thenReturn(new VeiculoResponse(1L, "321", "Ecosport", null));

        Page<VeiculoResponse> result = marcaService.listarVeiculosPorMarca("321", pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).codigo()).isEqualTo("321");
        assertThat(result.getContent().get(0).modelo()).isEqualTo("Ecosport");
        assertThat(result.getContent().get(0).observacoes()).isEqualTo(null);
    }

    @Test
    void shouldReturnExceptionWhenMarcaNaoExiste(){
        Pageable pageable = PageRequest.of(0, 2);

        when(marcaRepository.findByCodigo("000")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> marcaService.listarVeiculosPorMarca("000", pageable))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("000");
    }
}
