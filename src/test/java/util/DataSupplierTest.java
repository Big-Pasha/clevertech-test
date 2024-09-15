package util;

import test.common.helper.DataSupplier;

import java.time.OffsetDateTime;

public class DataSupplierTest implements DataSupplier {

    @Override
    public OffsetDateTime getCurrentDateTime() {
        return OffsetDateTime.parse("2023-01-01T00:00:00.000Z");
    }
}
