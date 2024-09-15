package test.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import test.domain.SparePart;
import test.service.SparePartsService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(controllers = SparePartController.class)
class SparePartControllerTest {

    @MockBean
    private SparePartsService sparePartsService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void shouldFindAllSpareParts() throws Exception {
        when(sparePartsService.getSpareParts())
                .thenReturn(List.of(new SparePart(), new SparePart(), new SparePart()));

        mockMvc.perform(get("/api/v1/sparepart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }


    @Test
    void shouldReturnSparePartById() throws Exception {
        UUID id = UUID.randomUUID();
        SparePart sparePart = new SparePart().setId(id);

        when(sparePartsService.getSparePart(id))
                .thenReturn(sparePart);

        mockMvc.perform(get("/api/v1/sparepart/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sparePart.getId().toString()));
    }

    @Test
    void shouldReturnNull_whenIdNotFoundInSpareParts() throws Exception {
        UUID id = UUID.randomUUID();

        when(sparePartsService.getSparePart(id))
                .thenReturn(null);

        mockMvc.perform(get("/api/v1/sparepart/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }


    @Test
    void shouldCreateSparePart() throws Exception {
        SparePart sparePart = new SparePart()
                .setId(UUID.randomUUID())
                .setName("test")
                .setCreated(OffsetDateTime.now())
                .setPrice(BigDecimal.TEN);

        when(sparePartsService.createSparePart(any()))
                .thenReturn(true);

        mockMvc.perform(post("/api/v1/sparepart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sparePart))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void shouldReturnBadRequest_whenSparePartIsNull() throws Exception {
        SparePart sparePart = null;

        when(sparePartsService.createSparePart(any()))
                .thenReturn(false);

        mockMvc.perform(post("/api/v1/sparepart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sparePart))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateSparePart() throws Exception {
        UUID id = UUID.randomUUID();
        SparePart sparePart = new SparePart();

        when(sparePartsService.updateSparePart(eq(id), any()))
                .thenReturn(true);

        mockMvc.perform(put("/api/v1/sparepart/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sparePart))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void shouldNotUpdateSparePartWhenBadId() throws Exception {
        String id = "asd";
        SparePart sparePart = new SparePart();

        when(sparePartsService.updateSparePart(any(), any()))
                .thenReturn(false);

        mockMvc.perform(put("/api/v1/sparepart/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sparePart))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteSparePart() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/sparepart/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isAccepted());
    }

}