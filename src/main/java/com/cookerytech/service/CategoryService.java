package com.cookerytech.service;

import com.cookerytech.domain.Category;
import com.cookerytech.domain.Product;
import com.cookerytech.dto.CategoryDTO;
import com.cookerytech.dto.ProductDTO;
import com.cookerytech.dto.request.CategoryRequest;
import com.cookerytech.dto.request.CategoryUpdateRequest;
import com.cookerytech.exception.BadRequestException;
import com.cookerytech.exception.ResourceNotFoundException;
import com.cookerytech.exception.message.ErrorMessage;
import com.cookerytech.mapper.CategoryMapper;
import com.cookerytech.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private final ProductService productService;

    private final CategoryMapper categoryMapper;


    public CategoryService(CategoryRepository categoryRepository, ProductService productService, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
        this.categoryMapper = categoryMapper;
    }

    public List<ProductDTO> getProductsByCategory(Long categoryId) {

        List<ProductDTO> productDTOList = productService.getProductsByCategory(categoryId);
        return productDTOList;

    }

    public Category getCategory(Long id){

        return categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION, id)));

    }

    public CategoryDTO saveCategory(CategoryRequest categoryRequest) {
        Optional<Category> existingCategory = categoryRepository.findByTitle(categoryRequest.getTitle());

        if (existingCategory.isPresent()) {
            throw new ResourceNotFoundException(ErrorMessage.CATEGORY_ALREADY_EXIST_MESSAGE);
        }

        String titleCumle = categoryRequest.getTitle();
        titleCumle.replaceAll("[^a-zA-ZğüşıöçĞÜŞİÖÇ\\s]", "-").toLowerCase();


        Category category = categoryMapper.categorySaveRequestToCategory(categoryRequest);

        category.setCreateAt(LocalDateTime.now());
        category.setMainCategoryId(3);
        long seqNo = categoryRepository.count() + 1;
        category.setSeq((int) seqNo);
        category.setBuiltIn(false);
        category.setDescription("Category aciklama");
        category.setSlug(titleCumle);
        category.setTitle(categoryRequest.getTitle());


        categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDTO(category);
    }
    public CategoryDTO updateCategoryWithId(Long id, CategoryUpdateRequest categoryUpdateRequest) {
        Category category = getCategory(id);

        if(category.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        String titleCumle = categoryUpdateRequest.getTitle();
        titleCumle.replaceAll("[^a-zA-ZğüşıöçĞÜŞİÖÇ\\s]", "-").toLowerCase();

        LocalDateTime now = LocalDateTime.now();

        category.setTitle(categoryUpdateRequest.getTitle());
        category.setIsActive(categoryUpdateRequest.getIsActive());
        category.setSlug(titleCumle);
        category.setUpdateAt(now);

        Category updateCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDTO(updateCategory);
    }

    public CategoryDTO deleteCategoryByID(Long id) {

        Category category = getCategory(id);

        if(category.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        List<ProductDTO> productDTOList = getProductsByCategory(id);

        if(productDTOList.size()>0){
            throw new BadRequestException(ErrorMessage.CATEGORY_CANNOT_BE_DELETED_MESSAGE);
        }

        categoryRepository.delete(category);

        return categoryMapper.categoryToCategoryDTO(category);

    }
}
