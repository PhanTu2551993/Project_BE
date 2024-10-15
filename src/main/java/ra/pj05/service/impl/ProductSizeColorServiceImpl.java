package ra.pj05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.pj05.exception.DataNotFoundException;
import ra.pj05.model.dto.request.ProductSizeColorRequest;
import ra.pj05.model.entity.Color;
import ra.pj05.model.entity.ProductDetail;
import ra.pj05.model.entity.Size;
import ra.pj05.model.entity.jointable.ProductSizeColor;
import ra.pj05.repository.ProductSizeColorRepository;
import ra.pj05.service.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductSizeColorServiceImpl implements IProductSizeColorService {
    @Autowired
    private ProductSizeColorRepository productSizeColorRepository;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private IColorService colorService;
    @Autowired
    private ISizeService sizeService;
    @Autowired
    private IProductDetailService productDetailService;


    @Override
    public List<ProductSizeColor> findAllProductSizeColors() {
        return productSizeColorRepository.findAll();
    }

    @Override
    public ProductSizeColor addProductSizeColor(Long productDetailId,ProductSizeColorRequest productSizeColorRequest) {
        ProductDetail productDetail = productDetailService.findProductDetailById(productDetailId);
        Color color = colorService.addColor(productSizeColorRequest.getColorName());
        Size size = sizeService.addSize(productSizeColorRequest.getSizeName());
        ProductSizeColor productSizeColor = new ProductSizeColor();
        String imageUrl = null;
        if (productSizeColorRequest.getImage() != null && !productSizeColorRequest.getImage().isEmpty()){
            imageUrl = uploadService.uploadFileToServer(productSizeColorRequest.getImage());
        }
        productSizeColor.setImage(imageUrl);
        productSizeColor.setDescription(productSizeColorRequest.getDescription());
        productSizeColor.setCreationDate(new Date());
        productSizeColor.setPrice(productSizeColorRequest.getPrice());
        productSizeColor.setQuantity(productSizeColorRequest.getQuantity());
        productSizeColor.setModificationDate(new Date());
        productSizeColor.setColor(color);
        productSizeColor.setSize(size);
        productSizeColor.setProductDetail(productDetail);
        productSizeColorRepository.save(productSizeColor);
        if (productDetail.getProductSizeColors() == null) {
            productDetail.setProductSizeColors(new ArrayList<>());
        }
        productDetail.getProductSizeColors().add(productSizeColor);
        Integer totalStock = totalStock(productDetail);
        productDetail.setStock(totalStock);
        productDetailService.saveProductDetail(productDetail);
        return productSizeColor;
    }

    @Override
    public ProductSizeColor updateProductSizeColor(Long productDetailId, Long sizeId, Long colorId, ProductSizeColorRequest productSizeColorRequest) {
        ProductDetail productDetail = productDetailService.findProductDetailById(productDetailId);
        Size size = sizeService.findSizeById(sizeId);
        Color color = colorService.findColorById(colorId);
        ProductSizeColor productSizeColor = findProductSizeColorById(productDetailId,sizeId,colorId);
        if(productSizeColorRequest.getImage() != null && !productSizeColorRequest.getImage().isEmpty()){
            productSizeColor.setImage(uploadService.uploadFileToServer(productSizeColorRequest.getImage()));
        }
        if(productSizeColorRequest.getDescription() != null && !productSizeColorRequest.getDescription().isEmpty()){
            productSizeColor.setDescription(productSizeColorRequest.getDescription());
        }
        if (productSizeColorRequest.getPrice() != null){
            productSizeColor.setPrice(productSizeColorRequest.getPrice());
        }
        if (productSizeColorRequest.getQuantity() != null){
            productSizeColor.setQuantity(productSizeColorRequest.getQuantity());
        }
        if (productSizeColorRequest.getSizeName() != null && !productSizeColorRequest.getSizeName().isEmpty()) {
            size.setSizeName(productSizeColorRequest.getSizeName());
            sizeService.saveSize(size);
            productSizeColor.setSize(size);
        }

        // Cập nhật tên màu sắc nếu có và lưu lại thay đổi
        if (productSizeColorRequest.getColorName() != null && !productSizeColorRequest.getColorName().isEmpty()) {
            color.setColorName(productSizeColorRequest.getColorName());
            colorService.saveColor(color);
            productSizeColor.setColor(color);
        }

        productSizeColor.setProductDetail(productDetail);
        productSizeColor.setModificationDate(new Date());
        productSizeColorRepository.save(productSizeColor);
        Integer totalStock = totalStock(productDetail);
        productDetail.setStock(totalStock);
        productDetailService.saveProductDetail(productDetail);
        return productSizeColor;
    }

    @Override
    public ProductSizeColor findProductSizeColorById(Long productDetailId, Long sizeId, Long colorId) {
        ProductDetail productDetail = productDetailService.findProductDetailById(productDetailId);
        Color color = colorService.findColorById(colorId);
        Size size = sizeService.findSizeById(sizeId);
        Optional<ProductSizeColor> productSizeColorOptional = productSizeColorRepository.findByProductDetailAndSizeAndColor(productDetail,size,color);
        return productSizeColorOptional.orElseThrow(() -> new DataNotFoundException("ProductSizeColorId not found"));
    }

    @Override
    public void deleteProductSizeColor(Long productDetailId, Long sizeId, Long colorId) {
        ProductDetail productDetail = productDetailService.findProductDetailById(productDetailId);
        ProductSizeColor productSizeColor = findProductSizeColorById(productDetailId, sizeId, colorId);
        productSizeColorRepository.delete(productSizeColor);
        colorService.deleteColorById(colorId);
        sizeService.deleteSizeById(sizeId);
        Integer totalStock = totalStock(productDetail);
        productDetail.setStock(totalStock);
        productDetailService.saveProductDetail(productDetail);
    }

    @Override
    public List<ProductSizeColor> findByProductDetail(ProductDetail productDetail) {
        return productSizeColorRepository.findByProductDetail(productDetail);
    }

//    @Override
//    public ProductSizeColor findById(ProductSizeColorId productSizeColorId) {
//         Optional<ProductSizeColor> productSizeColor =  productSizeColorRepository.findById(productSizeColorId);
//        return productSizeColor.orElseThrow(() -> new DataNotFoundException("ProductSizeColorId not found"));
//    }

    public Integer totalStock(ProductDetail productDetail){
        return  productSizeColorRepository.findByProductDetail(productDetail)
                .stream()
                .mapToInt(ProductSizeColor::getQuantity)
                .sum();

    }


}
