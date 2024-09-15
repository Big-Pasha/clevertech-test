package test.common.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import test.domain.SparePart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import test.service.SparePartsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SparePartController {

    private final SparePartsService sparePartsService;
    private final ObjectMapper objectMapper;

    @GetMapping("/api/v1/sparepart")
    public ResponseEntity<List<SparePart>> getSpareParts() {
        List<SparePart> spareParts = sparePartsService.getSpareParts();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(spareParts);
    }

    @GetMapping("/api/v1/sparepart/{id}")
    public ResponseEntity<SparePart> getSparePartById(@PathVariable("id") UUID id) {
        SparePart sparePart = sparePartsService.getSparePart(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(sparePart);
    }

    @PostMapping("/api/v1/sparepart")
    public ResponseEntity<Boolean> createSparePart(@RequestBody SparePart sparePart) {
        if (sparePartsService.createSparePart(sparePart)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PutMapping("/api/v1/sparepart/{id}")
    public ResponseEntity<Boolean> updateSparePart(@PathVariable("id") UUID id, @RequestBody SparePart sparePart) {
        if (sparePartsService.updateSparePart(id, sparePart)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @DeleteMapping("/api/v1/sparepart/{id}")
    public ResponseEntity deleteSparePart(@PathVariable("id") UUID id) {
        sparePartsService.deleteSparePart(id);

        return ResponseEntity.accepted().body("");
    }
}
