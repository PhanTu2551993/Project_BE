package ra.pj05.service;

import ra.pj05.model.dto.request.ProductSizeColorRequest;
import ra.pj05.model.entity.ProductDetail;
import ra.pj05.model.entity.jointable.ProductSizeColor;

import java.util.List;

public interface IProductSizeColorService {
    List<ProductSizeColor> findAllProductSizeColors();
    ProductSizeColor addProductSizeColor(Long productDetailId,ProductSizeColorRequest productSizeColorRequest);
    ProductSizeColor updateProductSizeColor(Long productDetailId, Long sizeId, Long colorId,ProductSizeColorRequest productSizeColorRequest);
    ProductSizeColor findProductSizeColorById(Long productDetailId,Long sizeId,Long colorId);
    void deleteProductSizeColor(Long productDetailId,Long sizeId,Long colorId);
    List<ProductSizeColor> findByProductDetail(ProductDetail productDetail);
}
