package br.com.magnumbank.api1.mapper;

import br.com.magnumbank.api1.adapters.in.dto.UpdateVeiculoRequest;
import br.com.magnumbank.api1.adapters.in.dto.VeiculoResponse;
import br.com.magnumbank.api1.domain.Veiculo;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VeiculoMapper {

    VeiculoResponse toResponse (Veiculo veiculo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(@MappingTarget Veiculo veiculo, UpdateVeiculoRequest updateVeiculoRequest);
}
