package br.com.magnumbank.api1.adapters.out;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "fipeClient", url = "https://parallelum.com.br/fipe/api/v1")
public interface FipeClient {

    @GetMapping("/carros/marcas")
    List<Map<String, Object>> buscarMarcas();
}
