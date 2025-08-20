package br.com.magnumbank.api1.adapters.in;

import br.com.magnumbank.api1.domain.port.MarcaServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaServicePort marcaServicePort;

    @GetMapping
    public ResponseEntity<List<String>> getMarcas() {
        List<String> nomes = marcaServicePort.buscarMarcas();
        return nomes.isEmpty() ? ResponseEntity.noContent().build()
                                 : ResponseEntity.ok(nomes);
    }

}
