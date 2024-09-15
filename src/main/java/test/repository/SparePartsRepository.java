package test.repository;

import test.entity.SparePartEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class SparePartsRepository {

    private static final List<SparePartEntity> db;

    static {
        db = new ArrayList<>();

        db.add(new SparePartEntity(UUID.randomUUID(), "clutch", new BigDecimal(35), OffsetDateTime.now()));
        db.add(new SparePartEntity(UUID.randomUUID(), "tires", new BigDecimal(50), OffsetDateTime.now()));
        db.add(new SparePartEntity(UUID.randomUUID(), "pump", new BigDecimal(40), OffsetDateTime.now()));
        db.add(new SparePartEntity(UUID.randomUUID(), "timing belt", new BigDecimal(15), OffsetDateTime.now()));
    }

    public List<SparePartEntity> getSpareParts() {
        return db;
    }

    public SparePartEntity getSparePart(UUID id) {
        return db.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }

    public boolean createSparePart(SparePartEntity sparePart) {
        return null != sparePart ? db.add(sparePart) : false;
    }

    public boolean updateSparePart(UUID id, SparePartEntity sparePart) {
        SparePartEntity oldSparePart = getSparePart(sparePart.getId());
        if (oldSparePart != null) {
            oldSparePart.setId(sparePart.getId());
            oldSparePart.setName(sparePart.getName());
            oldSparePart.setCreated(sparePart.getCreated());
            oldSparePart.setPrice(sparePart.getPrice());

            return true;
        }
        return false;
    }

    public void deleteSparePart(UUID id) {
        db.removeIf(p -> p.getId().equals(id));
    }

}
