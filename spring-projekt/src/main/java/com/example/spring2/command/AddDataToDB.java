package com.example.spring2.command;

import com.example.spring2.entity.Brand;
import com.example.spring2.entity.Model;
import com.example.spring2.repository.BrandRepository;
import com.example.spring2.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddDataToDB implements CommandLineRunner {

    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;

    @Override
    public void run(String... args) throws Exception {
        Brand brand = brandRepository.findByName("Honda");
        if (brand == null) {
            brand = new Brand();
            brand.setName("Honda");

            brand = brandRepository.save(brand);

            Model accord = new Model();
            accord.setName("Accord");
            accord.setBrand(brand);

            Model civic = new Model();
            civic.setName("Civic");
            civic.setBrand(brand);

            modelRepository.save(accord);
            modelRepository.save(civic);
        }
    }
}
