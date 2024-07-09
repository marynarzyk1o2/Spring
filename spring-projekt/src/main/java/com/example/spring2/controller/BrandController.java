package com.example.spring2.controller;

import com.example.spring2.dto.ErrorDTO;
import com.example.spring2.dto.BrandDTO;
import com.example.spring2.entity.Brand;
import com.example.spring2.mapper.BrandMapper;
import com.example.spring2.service.BrandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BrandDTO>> brands() {
        List<Brand> brands = this.brandService.get();

        return ResponseEntity.ok(BrandMapper.mapBrands(brands));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BrandDTO> getBrand(@PathVariable Long id) {
        Brand brand = this.brandService.get(id);
        if (brand == null) {
            // Http Status - 404 - Not Found
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(BrandMapper.map(brand));
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> store(@RequestBody BrandDTO dto) {
        if (dto.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Id cannot be set"));
        }

        Brand brand = BrandMapper.map(dto);
        this.brandService.add(brand);

        // Http status - 201 - Created
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.brandService.delete(id);

        // Http Status - 204 - No Content
        return ResponseEntity.noContent().build();
    }
}
