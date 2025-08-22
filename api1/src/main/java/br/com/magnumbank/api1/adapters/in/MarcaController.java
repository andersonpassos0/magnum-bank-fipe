package br.com.magnumbank.api1.adapters.in;

import br.com.magnumbank.api1.adapters.in.dto.MarcaResponse;
import br.com.magnumbank.api1.adapters.in.dto.VeiculoResponse;
import br.com.magnumbank.api1.application.MarcaService;
import br.com.magnumbank.api1.application.MarcaServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marcas")
@RequiredArgsConstructor
@Tag(name = "Marcas", description = "Consulta marcas, modelos e atualiza modelo")
public class MarcaController {

    private final MarcaServicePort marcaServicePort;
    private final MarcaService marcaService;

    @GetMapping("/fipe")
    @Operation(summary = "Lista as marcas de carros buscados da tabela FIPE")
    public ResponseEntity<List<String>> getMarcasFipe() {
        List<String> nomes = marcaServicePort.buscarMarcasFipe();
        return nomes.isEmpty() ? ResponseEntity.noContent().build()
                                 : ResponseEntity.ok(nomes);
    }

    @GetMapping
    @Operation(summary = "Lista as marcas de carros cadatradas no banco de dados - paginado")
    public ResponseEntity<Page<MarcaResponse>> getMarcas(Pageable pageable){
        return ResponseEntity.ok().body(marcaService.getMarcas(pageable));
    }

    @GetMapping("/{codigo}/veiculos")
    @Operation(summary = "Lista veículos de determinada marca - paginado")
    public ResponseEntity<Page<VeiculoResponse>> listarVeiculosPorMarca(@PathVariable String codigo,
                                                                        Pageable pageable){
        return ResponseEntity.ok(marcaService.listarVeiculosPorMarca(codigo, pageable));
    }
}
