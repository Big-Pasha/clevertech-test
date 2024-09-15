package service;

import test.domain.SparePart;
import test.entity.SparePartEntity;
import test.mapper.SparePartMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import test.repository.SparePartsRepository;
import test.service.SparePartsService;
import util.DataSupplierTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SparePartsServiceTest {

    @Mock
    private SparePartsRepository sparePartsRepository;

    @Mock
    private SparePartMapper sparePartMapper;

    @Spy
    private DataSupplierTest dataSupplierTest;

    @InjectMocks
    private SparePartsService sparePartsService;

    @Test
    void shouldGetSpareParts() {
        //given
        List<SparePartEntity> sparePartEntityList = List.of(
                new SparePartEntity().setCreated(dataSupplierTest.getCurrentDateTime())
        );
        List<SparePart> sparePartList = List.of(new SparePart());

        when(sparePartsRepository.getSpareParts())
                .thenReturn(sparePartEntityList);
        when(sparePartMapper.toSpareParts(sparePartEntityList))
                .thenReturn(sparePartList);

        //when
        List<SparePart> spareParts = sparePartsService.getSpareParts();

        //then
        assertFalse(spareParts.isEmpty());
        verify(sparePartsRepository, times(1)).getSpareParts();
        verify(sparePartMapper, times(1)).toSpareParts(sparePartEntityList);
    }

    @Test
    void shouldGetSparePartById() {
        UUID sparePartId = UUID.randomUUID();
        SparePartEntity sparePartEntity = new SparePartEntity().setId(sparePartId);
        SparePart sparePart = new SparePart().setId(sparePartId);

        when(sparePartsRepository.getSparePart(sparePartId))
                .thenReturn(sparePartEntity);
        when(sparePartsService.getSparePart(sparePartId))
                .thenReturn(sparePart);

        SparePart realSparePart = sparePartsService.getSparePart(sparePartId);

        assertEquals(sparePartId, realSparePart.getId());
        verify(sparePartsRepository, times(2)).getSparePart(sparePartId);
        verify(sparePartMapper, times(1)).toSparePart(sparePartEntity);
    }

    @Test
    void shouldThrowNPE_whenSparePartNotExist() {
        UUID sparePartId = UUID.randomUUID();

        when(sparePartsRepository.getSparePart(sparePartId))
            .thenThrow(NullPointerException.class);

        assertThrows(
                NullPointerException.class,
                () -> sparePartsService.getSparePart(sparePartId)
        );

        verify(sparePartsRepository, times(1)).getSparePart(sparePartId);
        verifyNoInteractions(sparePartMapper);
    }

    @Test
    void shouldReturnTrueWhenCreateSparePart() {
        UUID id = UUID.randomUUID();
        SparePart sparePart = new SparePart().setId(id);
        SparePartEntity sparePartEntity = new SparePartEntity().setId(id);

        when(sparePartsRepository.createSparePart(sparePartEntity))
                .thenReturn(true);
        when(sparePartMapper.toSparePartEntity(sparePart))
                .thenReturn(sparePartEntity);


        boolean result = sparePartsService.createSparePart(sparePart);

        assertTrue(result);
        verify(sparePartsRepository, times(1)).createSparePart(sparePartEntity);
        verify(sparePartMapper, times(1)).toSparePartEntity(sparePart);
    }

    @Test
    void shouldReturnFalseWhenCreateNullSparePart() {
        SparePart sparePart = null;
        SparePartEntity sparePartEntity = null;

        when(sparePartsRepository.createSparePart(sparePartEntity))
                .thenReturn(false);
        when(sparePartMapper.toSparePartEntity(sparePart))
                .thenReturn(sparePartEntity);


        boolean result = sparePartsService.createSparePart(sparePart);

        assertFalse(result);
        verify(sparePartsRepository, times(1)).createSparePart(sparePartEntity);
        verify(sparePartMapper, times(1)).toSparePartEntity(sparePart);
    }

    @Test
    void shouldReturnTrueWhenUpdateSparePart() {
        UUID id = UUID.randomUUID();
        SparePart sparePart = new SparePart().setId(UUID.randomUUID());
        SparePartEntity sparePartEntity = new SparePartEntity().setId(sparePart.getId());

        when(sparePartMapper.toSparePartEntity(sparePart))
                .thenReturn(sparePartEntity);
        when(sparePartsRepository.updateSparePart(id, sparePartEntity))
                .thenReturn(true);

        boolean result = sparePartsService.updateSparePart(id, sparePart);

        assertTrue(result);
        verify(sparePartsRepository).updateSparePart(id, sparePartEntity);
        verify(sparePartMapper).toSparePartEntity(sparePart);
    }

    @Test
    void shouldDeleteSparePartById() {
        UUID id = UUID.randomUUID();

        doNothing().when(sparePartsRepository)
                        .deleteSparePart(id);

        sparePartsService.deleteSparePart(id);

        verify(sparePartsRepository).deleteSparePart(id);
    }
}