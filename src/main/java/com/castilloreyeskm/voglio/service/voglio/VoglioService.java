package com.castilloreyeskm.voglio.service.voglio;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.castilloreyeskm.voglio.dto.ImageDto;
import com.castilloreyeskm.voglio.dto.VoglioDto;
import com.castilloreyeskm.voglio.exceptions.ResourceNotFoundException;
import com.castilloreyeskm.voglio.model.Category;
import com.castilloreyeskm.voglio.model.Image;
import com.castilloreyeskm.voglio.model.Voglio;
import com.castilloreyeskm.voglio.repository.CategoryRepository;
import com.castilloreyeskm.voglio.repository.ImageRepository;
import com.castilloreyeskm.voglio.repository.VoglioRepository;
import com.castilloreyeskm.voglio.request.AddVoglioRequest;
import com.castilloreyeskm.voglio.request.UpdateVoglioRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoglioService implements IVoglioService {

    private final VoglioRepository voglioRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public VoglioDto addVoglio(AddVoglioRequest request) {
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        Voglio newVoglio = voglioRepository.save(createVoglio(request, category));
        return convertToDto(newVoglio);
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
    public VoglioDto updateVoglio(UpdateVoglioRequest request, Long voglioId) {
        Voglio voglio = voglioRepository.findById(voglioId)
                .map(existingVoglio -> updateExistingVoglio(existingVoglio, request))
                .map(voglioRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Voglio not found"));
        return convertToDto(voglio);
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
    public List<VoglioDto> getAllVoglios() {
        List<Voglio> voglios = voglioRepository.findAll();
        return getConvertedVoglios(voglios);
    }

    @Override
    public List<VoglioDto> getVogliosByCategory(String categoryName) {
        List<Voglio> voglios = voglioRepository.findByCategoryName(categoryName);
        return getConvertedVoglios(voglios);
    }

    @Override
    public List<VoglioDto> getVogliosByName(String voglioName) {
        List<Voglio> voglios = voglioRepository.findByName(voglioName);
        return getConvertedVoglios(voglios);
    }

    private List<VoglioDto> getConvertedVoglios(List<Voglio> voglios) {
        return voglios.stream().map(this::convertToDto).toList();
    }

    @Override
    public VoglioDto convertToDto(Voglio voglio) {
        VoglioDto voglioDto = modelMapper.map(voglio, VoglioDto.class);
        List<Image> images = imageRepository.findByVoglioId(voglio.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        voglioDto.setImages(imageDtos);
        return voglioDto;
    }

}
