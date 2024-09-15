package test.mapper;

import test.domain.SparePart;
import test.entity.SparePartEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SparePartMapper {

    List<SparePart> toSpareParts(List<SparePartEntity> sparePartEntities);

    SparePart toSparePart(SparePartEntity sparePartEntity);

    SparePartEntity toSparePartEntity(SparePart sparePart);
}
