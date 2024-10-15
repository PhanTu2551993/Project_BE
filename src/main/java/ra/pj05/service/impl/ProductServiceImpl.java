package ra.pj05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.pj05.exception.ChangeException;
import ra.pj05.exception.DataNotFoundException;
import ra.pj05.model.dto.request.ProductRequest;
import ra.pj05.model.entity.Category;
import ra.pj05.model.entity.Product;
import ra.pj05.repository.ICategoryRepository;
import ra.pj05.repository.IProductDetailRepository;
import ra.pj05.repository.IProductRepository;
import ra.pj05.service.ICategoryService;
import ra.pj05.service.IProductService;
import ra.pj05.service.UploadService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IProductDetailRepository productDetailRepository;

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getAllProducts(int page, int size, String sortField, String sortDirection) {
        Sort sort = Sort.by(sortField);
        sort = sortDirection.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> searchAndPagingProducts(String searchText, int page, int size, String sortField, String sortDirection) {
        Sort sort = Sort.by(sortField);
        sort = sortDirection.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findByProductNameContainingOrSkuContainingOrDescriptionContaining(searchText,searchText,searchText,pageable);
    }

    @Override
    public Product updateProduct(Long proId, ProductRequest productRequest) {
        Product product = productRepository.findById(proId).orElseThrow(()->new DataNotFoundException("Product not found"));
        if(productRequest.getProductName() != null && !productRequest.getProductName().isEmpty()) {
           product.setProductName(productRequest.getProductName());
        };
        if (productRequest.getDescription() != null && !productRequest.getDescription().isEmpty()) {
            product.setDescription(productRequest.getDescription());
        };
        if (productRequest.getCategoryId() != null) {
            Category category = categoryService.getCategoryById(productRequest.getCategoryId());
            product.setCategory(category);
        }
        String imageUrl = null;
        if (productRequest.getImage() != null && !productRequest.getImage().isEmpty()){
            imageUrl = uploadService.uploadFileToServer(productRequest.getImage());
        }
        product.setImage(imageUrl);
        product.setUpdatedAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long proId) {
        Product product = productRepository.findById(proId).orElseThrow(()->new DataNotFoundException("Product not found"));
        if (!productDetailRepository.findByProduct(product).isEmpty()) {
            throw new ChangeException("Cannot delete Product because related ProductDetail exist.");
        }
        productRepository.deleteById(proId);
    }

    @Override
    public Product createProduct(ProductRequest productRequest) {
        Category category = categoryService.getCategoryById(productRequest.getCategoryId());
        if (category == null) {
            throw new DataNotFoundException("Category not found");
        }
        String imageUrl = null;
        if (productRequest.getImage() != null && !productRequest.getImage().isEmpty()){
            imageUrl = uploadService.uploadFileToServer(productRequest.getImage());
        }
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setCategory(category);
        product.setUpdatedAt(new Date());
        String sku = UUID.randomUUID().toString();
        product.setSku(sku);
        product.setImage(imageUrl);
        product.setCreatedAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long proId) {
        Optional<Product> product = productRepository.findById(proId);
        return product.orElseThrow(()->new DataNotFoundException("Product not found"));
    }

}
