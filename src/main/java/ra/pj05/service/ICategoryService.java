package ra.pj05.service;

import org.springframework.data.domain.Page;
import ra.pj05.model.dto.request.CategoryRequest;
import ra.pj05.model.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    Page<Category> getAllCategories(int page, int size, String sortField, String sortDirection);
    Category addCategory(CategoryRequest categoryRequest);
    Page<Category> searchAndPageCategory(String search, int page, int size, String sortField, String sortDirection);
    void deleteCategory(Long catId);
    Category updateCategory(Long catId, CategoryRequest categoryRequest);
    Category getCategoryById(Long catId);
    List<Category> getAllCategory();
}
