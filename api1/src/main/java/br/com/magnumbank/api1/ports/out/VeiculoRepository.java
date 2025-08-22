package br.com.magnumbank.api1.ports.out;

import br.com.magnumbank.api1.domain.Marca;
import br.com.magnumbank.api1.domain.Veiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    Page<Veiculo> findByMarca(Marca marca, Pageable pageable);
    long countByMarcaCodigo(String codigo);
}
