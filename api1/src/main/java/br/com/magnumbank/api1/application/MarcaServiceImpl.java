package br.com.magnumbank.api1.application;

import br.com.magnumbank.api1.adapters.in.dto.MarcaResponse;
import br.com.magnumbank.api1.adapters.in.dto.VeiculoResponse;
import br.com.magnumbank.api1.cache.SortUtils;
import br.com.magnumbank.api1.domain.Marca;
import br.com.magnumbank.api1.domain.Veiculo;
import br.com.magnumbank.api1.mapper.MarcaMapper;
import br.com.magnumbank.api1.mapper.VeiculoMapper;
import br.com.magnumbank.api1.ports.out.MarcaRepository;
import br.com.magnumbank.api1.ports.out.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaServiceImpl implements MarcaService{

    private final MarcaRepository marcaRepository;
    private final VeiculoRepository veiculoRepository;
    private final MarcaMapper marcaMapper;
    private final VeiculoMapper veiculoMapper;

    @Override
    public Page<MarcaResponse> getMarcas(Pageable pageable) {
        String sortKey = SortUtils.sortToKey(pageable.getSort());
        List<MarcaResponse> content = getMarcasContentCached(pageable.getPageNumber(), pageable.getPageSize(), sortKey);
        long total = getMarcasTotalCached();
        return new PageImpl<>(content, pageable, total);
    }

    @Cacheable(cacheNames = "marcas_todas", key = "´p:´ + #page + ':s:' + #size + ':O:' + #sortkey")
    public List<MarcaResponse> getMarcasContentCached(int page, int size, String sortKey){
        Pageable pageable = PageRequest.of(page, size, SortUtils.keyToSort(sortKey));
        Page<Marca> pageData = marcaRepository.findAll(pageable);
        return pageData.map(marcaMapper::toResponse).getContent();
    }

    @Cacheable(cacheNames = "marcas_total", key = "'v1'")
    public long getMarcasTotalCached(){
        return marcaRepository.count();
    }

    @Override
    public Page<VeiculoResponse> listarVeiculosPorMarca(String codigo, Pageable pageable) {
        String sortKey = SortUtils.sortToKey(pageable.getSort());
        List<VeiculoResponse> content = getVeiculosPorMarcaContentCached(codigo, pageable.getPageNumber(), pageable.getPageSize(), sortKey);
        long total = getVeiculosPorMarcaTotalCached(codigo);
        return new PageImpl<>(content, pageable, total);
    }

    @Cacheable(cacheNames = "veiculos_por_marca", key = "#codigo +'::p:' + #page + ':s:' + #size + ':o:' + #sortKey")
    private List<VeiculoResponse> getVeiculosPorMarcaContentCached(String codigo, int page, int size, String sortKey) {
        Pageable pageable = PageRequest.of(page, size,SortUtils.keyToSort(sortKey));
        var marca = marcaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Marca de código " + codigo + " não encotrada!"));
        var pageData = veiculoRepository.findByMarca(marca, pageable);
        return pageData.map(veiculoMapper::toResponse).getContent();
    }

    @Cacheable(cacheNames = "veiculos_por_marca_total", key = "#codigo")
    public long getVeiculosPorMarcaTotalCached(String codigo) {
        return veiculoRepository.countByMarcaCodigo(codigo);
    }
}
