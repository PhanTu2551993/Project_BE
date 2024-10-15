package ra.pj05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.pj05.exception.ChangeException;
import ra.pj05.exception.DataExistException;
import ra.pj05.exception.DataNotFoundException;
import ra.pj05.model.dto.request.CategoryRequest;
import ra.pj05.model.entity.Category;
import ra.pj05.repository.ICategoryRepository;
import ra.pj05.repository.IProductRepository;
import ra.pj05.service.ICategoryService;
import ra.pj05.service.UploadService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private IProductRepository productRepository;
    @Override

    public Page<Category> getAllCategories(int page, int size, String sortField, String sortDirection) {
        Sort sort = Sort.by(sortField);
        sort = sortDirection.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category addCategory(CategoryRequest categoryRequest) {
//        if (categoryRepository.existsByCategoryName(categoryRequest.getCategoryName())) {
//            throw new DataExistException("Tên Danh mục đã tồn tại", "categoryName");
//        }
        String imageUrl = null;
        if (categoryRequest.getImage() != null && !categoryRequest.getImage().isEmpty()){
            imageUrl = uploadService.uploadFileToServer(categoryRequest.getImage());

        }
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setDescription(categoryRequest.getDescription());
        category.setCreatedAt(categoryRequest.getCreatedAt());
        category.setImage(imageUrl);
        category.setStatus(true);

        return categoryRepository.save(category);
    }

    @Override
    public Page<Category> searchAndPageCategory(String search, int page, int size, String sortField, String sortDirection) {
        Sort sort = Sort.by(sortField);
        sort = sortDirection.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return categoryRepository.findByCategoryNameContainingOrDescriptionContaining(search, search, pageable);
    }

    @Override
    public void deleteCategory(Long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(() -> new NoSuchElementException("Không tồn tại danh mục"));
        if(!productRepository.findByCategory(category).isEmpty()){
            throw new ChangeException("Cannot delete Category because related Product exist.");
        }
        categoryRepository.deleteById(catId);
    }

    @Override
    public Category updateCategory(Long catId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new DataNotFoundException("Category not found"));
        if (categoryRequest.getDescription() != null && !categoryRequest.getDescription().isEmpty()){
            category.setDescription(categoryRequest.getDescription());
        }
        if (categoryRequest.getCategoryName() != null && !categoryRequest.getCategoryName().isEmpty()){
            category.setCategoryName(categoryRequest.getCategoryName());
        }
        if (categoryRequest.getStatus() != null){
            category.setStatus(categoryRequest.getStatus());
        }
        String imageUrl = null;
        if (categoryRequest.getImage() != null && !categoryRequest.getImage().isEmpty()){
            imageUrl = uploadService.uploadFileToServer(categoryRequest.getImage());

        }
        category.setImage(imageUrl);
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long catId) {
        Optional<Category> category = categoryRepository.findById(catId);
        return category.orElseThrow(() -> new DataNotFoundException("Category not found"));
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
