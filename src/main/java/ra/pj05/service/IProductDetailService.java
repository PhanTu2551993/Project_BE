package ra.pj05.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.pj05.model.dto.request.ProductDetailRequest;
import ra.pj05.model.entity.ProductDetail;

import java.util.List;

public interface IProductDetailService {
    List<ProductDetail> findAllProductDetails();
    Page<ProductDetail> searchAndPagingProductDetail(String searchText, int page, int size, String sortField, String sortDirection);
    ProductDetail addProductDetail(ProductDetailRequest productDetailRequest);
    ProductDetail findProductDetailById(Long id);
    void deleteProductDetailById(Long id);
    ProductDetail updateProductDetail(Long id,ProductDetailRequest productDetailRequest);
    ProductDetail saveProductDetail(ProductDetail productDetail);
}
