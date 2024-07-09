package com.example.spring2.service;

import com.example.spring2.entity.Brand;
import com.example.spring2.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public void add(Brand brand) {
        this.brandRepository.save(brand);
    }

    public Brand get(Long id) {
        Optional<Brand> optionalBrand = this.brandRepository.findById(id);

        return optionalBrand.orElse(null);
    }

    public List<Brand> get() {
        return this.brandRepository.findAll();
    }

    public void delete(Long id) {
        this.brandRepository.deleteById(id);
    }
}
