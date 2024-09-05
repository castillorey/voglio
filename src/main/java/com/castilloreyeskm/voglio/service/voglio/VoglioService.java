package com.castilloreyeskm.voglio.service.voglio;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.castilloreyeskm.voglio.exceptions.ResourceNotFoundException;
import com.castilloreyeskm.voglio.model.Category;
import com.castilloreyeskm.voglio.model.Voglio;
import com.castilloreyeskm.voglio.repository.CategoryRepository;
import com.castilloreyeskm.voglio.repository.VoglioRepository;
import com.castilloreyeskm.voglio.request.AddVoglioRequest;
import com.castilloreyeskm.voglio.request.UpdateVoglioRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoglioService implements IVoglioService {

    private final VoglioRepository voglioRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Voglio addVoglio(AddVoglioRequest request) {
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return voglioRepository.save(createVoglio(request, category));
    }

    private Voglio createVoglio(AddVoglioRequest request, Category category) {
        return new Voglio(
                request.getName(),
                request.getDescription(),
                request.getPriority(),
                request.getQuantity(),
                request.isActive(),
                category);
    }

    @Override
    public Voglio getVoglioById(Long id) {
        return voglioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voglio not found"));
    }

    @Override
    public void deleteVoglioById(Long id) {
        voglioRepository.findById(id).ifPresentOrElse(voglioRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Voglio not found");
                });
    }

    @Override
    public Voglio updateVoglio(UpdateVoglioRequest request, Long voglioId) {
        return voglioRepository.findById(voglioId)
                .map(existingVoglio -> updateExistingVoglio(existingVoglio, request))
                .map(voglioRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Voglio not found"));
    }

    private Voglio updateExistingVoglio(Voglio existingVoglio, UpdateVoglioRequest request) {
        existingVoglio.setName(request.getName());
        existingVoglio.setDescription(request.getDescription());
        existingVoglio.setPriority(request.getPriority());
        existingVoglio.setQuantity(request.getQuantity());
        existingVoglio.setActive(request.isActive());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingVoglio.setCategory(category);
        return existingVoglio;
    }

    @Override
    public List<Voglio> getAllVoglios() {
        return voglioRepository.findAll();
    }

    @Override
    public List<Voglio> getVogliosByCategory(String categoryName) {
        return voglioRepository.findByCategoryName(categoryName);
    }

    @Override
    public List<Voglio> getVogliosByName(String voglioName) {
        return voglioRepository.findByName(voglioName);
    }

}
