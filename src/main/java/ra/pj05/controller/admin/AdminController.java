package ra.pj05.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.pj05.exception.ChangeException;
import ra.pj05.model.dto.request.*;
import ra.pj05.model.entity.*;
import ra.pj05.model.entity.jointable.ProductSizeColor;
import ra.pj05.service.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;
    @Autowired
    private IProductDetailService productDetailService;

//    @Autowired
//    private IColorService colorService;
//
//    @Autowired
//    private ISizeService sizeService;

    @Autowired
    private IProductSizeColorService productSizeColorService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "2") int size,
                                      @RequestParam(defaultValue = "userId") String sortField,
                                      @RequestParam(defaultValue = "asc") String sortDirection) {

        Page<Users> users = userService.getAllUsers(page, size, sortField, sortDirection);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/search")
    public ResponseEntity<?> searchUsers(@RequestParam(required = false, defaultValue = "") String searchText,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "2") int size,
                                         @RequestParam(defaultValue = "userId") String sortField,
                                         @RequestParam(defaultValue = "asc") String sortDirection) {
        Page<Users> users = userService.searchAndPageUsers(searchText, page, size, sortField, sortDirection);
        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @PatchMapping("/user/changeStatus/{userId}")
    public ResponseEntity<?> changeStatus(@PathVariable Long userId){
        try {
            Users user = userService.changeStatus(userId);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }catch (ChangeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PatchMapping("/user/changeRole/{userId}")
    public ResponseEntity<?> changeRole(@PathVariable Long userId,@RequestBody Map<String, String> roleRequest){
        String newRole = roleRequest.get("newRole");
        try {
            Users user = userService.changeRole(userId, newRole);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ChangeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Category Admin

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "2") int size,
                                            @RequestParam(defaultValue = "categoryId") String sortField,
                                            @RequestParam(defaultValue = "asc") String sortDirection) {
        Page<Category> categories = categoryService.getAllCategories(page, size, sortField, sortDirection);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/category/search")
    public ResponseEntity<?> searchCategory(@RequestParam(required = false, defaultValue = "") String searchText,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "2") int size,
                                            @RequestParam(defaultValue = "categoryId") String sortField,
                                            @RequestParam(defaultValue = "asc") String sortDirection){
        Page<Category> categoryPage = categoryService.searchAndPageCategory(searchText,page, size, sortField, sortDirection);
        return new ResponseEntity<>(categoryPage, HttpStatus.OK);
    }

    @PostMapping("/category")
    public ResponseEntity<?> addCategory(@ModelAttribute CategoryRequest categoryRequest) {
        Category category = categoryService.addCategory(categoryRequest);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/category/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id,@ModelAttribute CategoryRequest categoryRequest) {
        Category category = categoryService.updateCategory(id,categoryRequest);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCat(){
        List<Category> categories = categoryService.getAllCategory();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //Product Admin

    @GetMapping("/findAllProducts")
    public ResponseEntity<?> findAllProducts(){
        List<Product> productList = productService.findAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "0")int page,
                                            @RequestParam(defaultValue = "2")int size,
                                            @RequestParam(defaultValue = "productId")String sortField,
                                            @RequestParam(defaultValue = "asc")String sortDirection){
        Page<Product> products = productService.getAllProducts(page, size, sortField, sortDirection);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/search")
    public ResponseEntity<?> searchAndPagingProducts(@RequestParam(required = false,defaultValue = "")String searchText,
                                                     @RequestParam(defaultValue = "0")int page,
                                                     @RequestParam(defaultValue = "2")int size,
                                                     @RequestParam(defaultValue = "productId")String sortField,
                                                     @RequestParam(defaultValue = "asc")String sortDirection){
        Page<Product> products = productService.searchAndPagingProducts(searchText, page, size, sortField, sortDirection);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@ModelAttribute ProductRequest productRequest) {
        Product product = productService.createProduct(productRequest);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PatchMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,@ModelAttribute ProductRequest productRequest) {
        Product product = productService.updateProduct(id,productRequest);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Admin Product Detail

    @GetMapping("/findAllProductDetails")
    public ResponseEntity<?> findAllProductDetails() {
        List<ProductDetail> productDetailList = productDetailService.findAllProductDetails();
        return new ResponseEntity<>(productDetailList, HttpStatus.OK);
    }

    @GetMapping("/productDetails/search")
    public ResponseEntity<?> searchAndPagingProductDetails(@RequestParam(required = false,defaultValue = "")String searchText,
                                                           @RequestParam(defaultValue = "0")int page,
                                                           @RequestParam(defaultValue = "2")int size,
                                                           @RequestParam(defaultValue = "productDetailId")String sortField,
                                                           @RequestParam(defaultValue = "asc")String sortDirection){
        Page<ProductDetail> productDetailPage = productDetailService.searchAndPagingProductDetail(searchText, page, size, sortField, sortDirection);
        return new ResponseEntity<>(productDetailPage, HttpStatus.OK);

    }

    @PostMapping("/productDetail")
    public ResponseEntity<?> addProductDetail(@ModelAttribute ProductDetailRequest productDetailRequest) {
        ProductDetail productDetail = productDetailService.addProductDetail(productDetailRequest);
        return new ResponseEntity<>(productDetail, HttpStatus.CREATED);
    }

    @PatchMapping("/productDetail/{id}")
    public ResponseEntity<?> updateProductDetail(@PathVariable Long id,@ModelAttribute ProductDetailRequest productDetailRequest) {
        ProductDetail productDetail = productDetailService.updateProductDetail(id,productDetailRequest);
        return new ResponseEntity<>(productDetail, HttpStatus.OK);
    }

    @DeleteMapping("/productDetail/{id}")
    public ResponseEntity<?> deleteProductDetail(@PathVariable Long id) {
        productDetailService.deleteProductDetailById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //ProductDetailSizeColor

    @GetMapping("/productDetail/productDetailSizeColor")
    public ResponseEntity<?> findAllProductDetailSizeColor() {
        List<ProductSizeColor> productSizeColorList = productSizeColorService.findAllProductSizeColors();
        return new ResponseEntity<>(productSizeColorList,HttpStatus.OK);
    };


    @PostMapping("/productDetail/productDetailSizeColor/{id}")
    public ResponseEntity<?> addProductDetailSizeColor(@PathVariable Long id,@ModelAttribute ProductSizeColorRequest productSizeColorRequest){
        ProductSizeColor productSizeColor = productSizeColorService.addProductSizeColor(id,productSizeColorRequest);
        return new ResponseEntity<>(productSizeColor,HttpStatus.CREATED);
    };

    @PatchMapping("/productDetail/productDetailSizeColor/{productDetailId}-{sizeId}-{colorId}")
    public ResponseEntity<?> updateProductSizeColor(@PathVariable Long productDetailId,
                                                    @PathVariable Long sizeId,
                                                    @PathVariable Long colorId,
                                                    @ModelAttribute ProductSizeColorRequest productSizeColorRequest) {
        ProductSizeColor productSizeColor = productSizeColorService.updateProductSizeColor(productDetailId,sizeId,colorId,productSizeColorRequest);
        return new ResponseEntity<>(productSizeColor, HttpStatus.OK);
    }

    @DeleteMapping("/productDetail/productDetailSizeColor/{productDetailId}-{sizeId}-{colorId}")
    public ResponseEntity<?> deleteProductSizeColor(@PathVariable Long productDetailId,
                                                    @PathVariable Long sizeId,
                                                    @PathVariable Long colorId) {
        productSizeColorService.deleteProductSizeColor(productDetailId,sizeId,colorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

