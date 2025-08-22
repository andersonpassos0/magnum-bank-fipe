package br.com.magnumbank.api2.ports.out;

import br.com.magnumbank.api2.domain.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    Optional<Marca> findByCodigo(String codigo);
}
