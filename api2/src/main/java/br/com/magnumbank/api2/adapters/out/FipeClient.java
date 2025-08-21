package br.com.magnumbank.api2.adapters.out;

import br.com.magnumbank.api2.adapters.out.dto.ModelosResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "fipeClient", url = "https://parallelum.com.br/fipe/api/v1")
public interface FipeClient {

    @GetMapping("/carros/marcas/{codigo}/modelos")
    ModelosResponse listarModelosPorMarca(@PathVariable("codigo") String codigoMarca);
}
