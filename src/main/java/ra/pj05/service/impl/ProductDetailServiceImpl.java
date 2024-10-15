package ra.pj05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.pj05.exception.ChangeException;
import ra.pj05.exception.DataNotFoundException;
import ra.pj05.model.dto.request.ProductDetailRequest;
import ra.pj05.model.entity.Product;
import ra.pj05.model.entity.ProductDetail;
import ra.pj05.model.entity.jointable.ProductSizeColor;
import ra.pj05.repository.IProductDetailRepository;
import ra.pj05.repository.ProductSizeColorRepository;
import ra.pj05.service.IProductDetailService;
import ra.pj05.service.IProductService;
import ra.pj05.service.IProductSizeColorService;
import ra.pj05.service.UploadService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDetailServiceImpl implements IProductDetailService {
    @Autowired
    private IProductDetailRepository productDetailRepository;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private IProductService productService;

    @Autowired
    private ProductSizeColorRepository productSizeColorRepository;
    @Override
    public List<ProductDetail> findAllProductDetails() {
        return productDetailRepository.findAll();
    }

    @Override
    public Page<ProductDetail> searchAndPagingProductDetail(String searchText, int page, int size, String sortField, String sortDirection) {
        Sort sort = Sort.by(sortField);
        sort = sortDirection.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productDetailRepository.findByProductDetailNameContaining(searchText,pageable);
    }

    @Override
    public ProductDetail addProductDetail(ProductDetailRequest productDetailRequest) {
        Product product = productService.findProductById(productDetailRequest.getProductId());
        if (product == null) {
            throw new DataNotFoundException("Product not found");
        }
        String imageUrl = null;
        if (productDetailRequest.getImage() != null && !productDetailRequest.getImage().isEmpty()){
            imageUrl = uploadService.uploadFileToServer(productDetailRequest.getImage());
        }
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProductDetailName(productDetailRequest.getProductDetailName());
        productDetail.setImage(imageUrl);
        productDetail.setStatus(productDetailRequest.getStatus());
        productDetail.setStock(0);
        productDetail.setProduct(product);
        return productDetailRepository.save(productDetail);
    }



    @Override
    public ProductDetail findProductDetailById(Long id) {
        Optional<ProductDetail> productDetail = productDetailRepository.findById(id);
        return productDetail.orElseThrow(() -> new DataNotFoundException("Product_Detail not found"));
    }

    @Override
    public void deleteProductDetailById(Long id) {
        ProductDetail productDetail = findProductDetailById(id);
         if (!productSizeColorRepository.findByProductDetail(productDetail).isEmpty()){
             throw new ChangeException("Cannot delete ProductDetail because related products exist.");
         };
        productDetailRepository.delete(productDetail);
    }

    @Override
    public ProductDetail updateProductDetail(Long id,ProductDetailRequest productDetailRequest) {
        ProductDetail productDetail = findProductDetailById(id);
        if (productDetailRequest.getProductDetailName() != null && !productDetailRequest.getProductDetailName().isEmpty()){
            productDetail.setProductDetailName(productDetailRequest.getProductDetailName());
        }
        if (productDetailRequest.getImage() != null && !productDetailRequest.getImage().isEmpty()){
            productDetail.setImage(uploadService.uploadFileToServer(productDetailRequest.getImage()));
        }
        if (productDetailRequest.getProductId() != null) {
            Product product = productService.findProductById(productDetailRequest.getProductId());
            productDetail.setProduct(product);
        }
        if (productDetailRequest.getStatus() != null) {
            productDetail.setStatus(productDetailRequest.getStatus());
        }
        return productDetailRepository.save(productDetail);
    }

    @Override
    public ProductDetail saveProductDetail(ProductDetail productDetail) {
        return productDetailRepository.save(productDetail);
    }
}
