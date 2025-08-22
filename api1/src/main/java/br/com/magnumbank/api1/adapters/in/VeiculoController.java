package br.com.magnumbank.api1.adapters.in;

import br.com.magnumbank.api1.adapters.in.dto.UpdateVeiculoRequest;
import br.com.magnumbank.api1.adapters.in.dto.VeiculoResponse;
import br.com.magnumbank.api1.application.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/veiculos")
@Tag(name = "Veiculos", description = "Atualiza nome do modelo e observação de veículo")
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza veículo")
    public ResponseEntity<VeiculoResponse> updateVeiculo(@PathVariable Long id,
                                                         @Valid @RequestBody UpdateVeiculoRequest updateVeiculoRequest){
        return ResponseEntity.ok(veiculoService.updateVeiculo(id, updateVeiculoRequest));
    }
}
