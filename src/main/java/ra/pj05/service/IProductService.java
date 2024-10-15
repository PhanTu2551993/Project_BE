package ra.pj05.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.pj05.model.dto.request.ProductRequest;
import ra.pj05.model.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAllProducts();
    Page<Product> getAllProducts(int page, int size, String sortField, String sortDirection);
    Page<Product> searchAndPagingProducts(String searchText, int page, int size, String sortField, String sortDirection);
    Product updateProduct(Long proId,ProductRequest productRequest);
    void deleteProduct(Long proId);
    Product createProduct(ProductRequest productRequest);
    Product findProductById(Long proId);
}
