package br.com.magnumbank.api1.adapters.in;

import br.com.magnumbank.api1.adapters.in.dto.UpdateVeiculoRequest;
import br.com.magnumbank.api1.adapters.in.dto.VeiculoResponse;
import br.com.magnumbank.api1.application.VeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponse> updateVeiculo(@PathVariable Long id,
                                                         @Valid @RequestBody UpdateVeiculoRequest updateVeiculoRequest){
        return ResponseEntity.ok(veiculoService.updateVeiculo(id, updateVeiculoRequest));
    }
}
