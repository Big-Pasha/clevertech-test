package test.mapper;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import test.domain.SparePart;
import test.entity.SparePartEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SparePartMapperTest {

    private SparePartMapper sparePartMapper = new SparePartMapperImpl();

    private EasyRandom easyRandom = new EasyRandom();

    @Test
    void toSpareParts() {
        int listSize = 10;

        List<SparePartEntity> spareParts = easyRandom.objects(SparePartEntity.class, listSize)
                .collect(Collectors.toList());

        List<SparePart> sparePartList = sparePartMapper.toSpareParts(spareParts);

        for(int i = 0; i < sparePartList.size(); i++) {
            assertEquals(spareParts.get(i).getId(), sparePartList.get(i).getId());
            assertEquals(spareParts.get(i).getName(), sparePartList.get(i).getName());
            assertEquals(spareParts.get(i).getCreated(), sparePartList.get(i).getCreated());
            assertEquals(spareParts.get(i).getPrice(), sparePartList.get(i).getPrice());
        }
    }

    @Test
    void toSparePart() {
        SparePartEntity sparePartEntity = easyRandom.nextObject(SparePartEntity.class);

        SparePart sparePart = sparePartMapper.toSparePart(sparePartEntity);

        assertEquals(sparePartEntity.getId(), sparePart.getId());
        assertEquals(sparePartEntity.getName(), sparePart.getName());
        assertEquals(sparePartEntity.getCreated(), sparePartEntity.getCreated());
        assertEquals(sparePartEntity.getPrice(), sparePartEntity.getPrice());
    }

    @Test
    void toSparePartEntity() {
        SparePart sparePart = easyRandom.nextObject(SparePart.class);

        SparePartEntity sparePartEntity = sparePartMapper.toSparePartEntity(sparePart);

        assertEquals(sparePartEntity.getId(), sparePart.getId());
        assertEquals(sparePartEntity.getName(), sparePart.getName());
        assertEquals(sparePartEntity.getCreated(), sparePartEntity.getCreated());
        assertEquals(sparePartEntity.getPrice(), sparePartEntity.getPrice());
    }
}