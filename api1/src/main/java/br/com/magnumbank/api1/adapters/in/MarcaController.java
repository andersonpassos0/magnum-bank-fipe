package br.com.magnumbank.api1.adapters.in;

import br.com.magnumbank.api1.adapters.in.dto.MarcaResponse;
import br.com.magnumbank.api1.application.MarcaService;
import br.com.magnumbank.api1.application.MarcaServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaServicePort marcaServicePort;
    private final MarcaService marcaService;

    @GetMapping("/fipe")
    public ResponseEntity<List<String>> getMarcasFipe() {
        List<String> nomes = marcaServicePort.buscarMarcasFipe();
        return nomes.isEmpty() ? ResponseEntity.noContent().build()
                                 : ResponseEntity.ok(nomes);
    }

    @GetMapping
    public ResponseEntity<Page<MarcaResponse>> getMarcas(Pageable pageable){
        return ResponseEntity.ok().body(marcaService.getMarcas(pageable));
    }
}
