package test.service;

import test.common.helper.DataSupplier;
import test.domain.SparePart;
import lombok.AllArgsConstructor;
import test.mapper.SparePartMapper;
import org.springframework.stereotype.Service;
import test.repository.SparePartsRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SparePartsService {

    private SparePartsRepository sparePartsRepository;
    private SparePartMapper sparePartMapper;
    private DataSupplier dataSupplier;

    public List<SparePart> getSpareParts() {
        return sparePartMapper.toSpareParts(
                sparePartsRepository.getSpareParts().stream()
                        .filter(e -> e.getCreated().isBefore(dataSupplier.getCurrentDateTime())
                                || e.getCreated().isEqual(dataSupplier.getCurrentDateTime()))
                        .collect(Collectors.toList())
        );
    }

    public SparePart getSparePart(UUID id) {
        return sparePartMapper.toSparePart(
                sparePartsRepository.getSparePart(id)
        );
    }

    public boolean createSparePart(SparePart sparePart) {
        return sparePartsRepository.createSparePart(
                sparePartMapper.toSparePartEntity(sparePart)
        );
    }

    public boolean updateSparePart(UUID id, SparePart sparePart) {
        return sparePartsRepository.updateSparePart(
                id, sparePartMapper.toSparePartEntity(sparePart)
        );
    }

    public void deleteSparePart(UUID id) {
        sparePartsRepository.deleteSparePart(id);
    }
}
