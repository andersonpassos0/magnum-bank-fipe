package br.com.magnumbank.api1.application;

import br.com.magnumbank.api1.adapters.in.dto.UpdateVeiculoRequest;
import br.com.magnumbank.api1.adapters.in.dto.VeiculoResponse;
import br.com.magnumbank.api1.domain.Veiculo;
import br.com.magnumbank.api1.mapper.VeiculoMapper;
import br.com.magnumbank.api1.ports.out.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VeiculoServiceImplTest {

    @Mock
    VeiculoRepository veiculoRepository;
    @Mock
    VeiculoMapper veiculoMapper;

    @InjectMocks
    VeiculoServiceImpl veiculoService;

    @Test
    void updateVeiculo_quandoExiste_deveAtualizarEMapearResposta() {
        Long id = 1L;
        var req = new UpdateVeiculoRequest("Ecosport", "branco");

        var veiculo = new Veiculo();
        veiculo.setId(id);
        veiculo.setCodigo("123");
        veiculo.setModelo("Antigo");
        veiculo.setObservacoes("obs antiga");

        when(veiculoRepository.findById(id)).thenReturn(Optional.of(veiculo));

        doAnswer(invocation -> {
            Veiculo v = invocation.getArgument(0, Veiculo.class);
            UpdateVeiculoRequest r = invocation.getArgument(1, UpdateVeiculoRequest.class);
            v.setModelo(r.modelo());
            v.setObservacoes(r.observacoes());
            return null;
        }).when(veiculoMapper).updateEntityFromRequest(any(Veiculo.class), any(UpdateVeiculoRequest.class));

        when(veiculoRepository.save(veiculo)).thenReturn(veiculo);
        when(veiculoMapper.toResponse(veiculo)).thenReturn(new VeiculoResponse(id, "V010", "Civic", "Revisado 2025"));

        VeiculoResponse resp = veiculoService.updateVeiculo(id, req);

        assertThat(resp.id()).isEqualTo(1L);
        assertThat(resp.codigo()).isEqualTo("123");
        assertThat(resp.modelo()).isEqualTo("Ecosport");
        assertThat(resp.observacoes()).isEqualTo("branco");

        verify(veiculoRepository).findById(1L);
        verify(veiculoMapper).updateEntityFromRequest(same(veiculo), eq(req));
        verify(veiculoRepository).save(same(veiculo));
        verify(veiculoMapper).toResponse(same(veiculo));
        verifyNoMoreInteractions(veiculoRepository, veiculoMapper);

        assertThat(veiculo.getModelo()).isEqualTo("Ecosport");
        assertThat(veiculo.getObservacoes()).isEqualTo("branco");
    }

    @Test
    void updateVeiculo_quandoNaoExiste_deveLancarEntityNotFound() {
        Long id = 123L;
        var req = new UpdateVeiculoRequest("Novo", "Obs");

        when(veiculoRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> veiculoService.updateVeiculo(id, req))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Veículo de id 123 não encontrado");

        verify(veiculoRepository).findById(123L);
        verifyNoMoreInteractions(veiculoRepository);
        verifyNoInteractions(veiculoMapper);
    }
}
