package com.example.spring2.service;

import com.example.spring2.entity.Model;
import com.example.spring2.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;

    public void add(Model model) {
        this.modelRepository.save(model);
    }

    public Model get(Long id) {
        Optional<Model> optionalModel = this.modelRepository.findById(id);

        return optionalModel.orElse(null);
    }

    public List<Model> getAll() {
        return this.modelRepository.findAll();
    }

    public List<Model> getAllByBrandId(Long brandId) {
        return this.modelRepository.findAllByBrandId(brandId);
    }

    public void delete(Long id) {
        this.modelRepository.deleteById(id);
    }
}
