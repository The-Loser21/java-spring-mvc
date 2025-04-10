package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public ProductController(ProductService productService, UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model) {
        List<Product> products = this.productService.getAllProduct();
        model.addAttribute("product", products);
        return "admin/product/show";
    }

    // Create Product
    @GetMapping("/admin/product/create")
    public String createProduct(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProductPage(Model model, @ModelAttribute("newProduct") @Valid Product product,
            BindingResult newProductBindResult,
            @RequestParam("productFile") MultipartFile file) {
        // Valitate data
        List<FieldError> errors = newProductBindResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        if (newProductBindResult.hasErrors()) {
            return "/admin/product/create";
        }
        // save data
        String productImages = this.uploadService.handleSaveUploadFile(file, "product");
        product.setImage(productImages);
        this.productService.saveProduct(product);
        return "redirect:/admin/product"; //
    }

    // View detail Product
    @GetMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        Product viewProduct = this.productService.getProductById(id);
        model.addAttribute("info", viewProduct);
        model.addAttribute("id", id);
        return "admin/product/detail";
    }

    // Update Product
    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        Product updateProduct = this.productService.getProductById(id);
        model.addAttribute("productUpdate", updateProduct);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String postMethodName(Model model, @ModelAttribute("productUpdate") @Valid Product product,
            BindingResult newProductBindResult,
            @RequestParam("productFile") MultipartFile file) {
        List<FieldError> errors = newProductBindResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        if (newProductBindResult.hasErrors()) {
            return "/admin/product/update";
        }

        Product curentUpdate = this.productService.getProductById(product.getId());
        if (curentUpdate != null) {
            if (!file.isEmpty()) {
                String img = this.uploadService.handleSaveUploadFile(file, "product");
                curentUpdate.setImage(img);
            }
            curentUpdate.setName(product.getName());
            curentUpdate.setDetailDesc(product.getDetailDesc());
            curentUpdate.setQuantity(product.getQuantity());
            curentUpdate.setShortDesc(product.getShortDesc());
            curentUpdate.setFactory(product.getFactory());
            curentUpdate.setPrice(product.getPrice());
            curentUpdate.setTarget(product.getTarget());
            this.productService.saveProduct(curentUpdate);
        }
        return "redirect:/admin/product";
    }

    // Delete Product
    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        Product deleteProduct = new Product();
        deleteProduct.setId(id);
        model.addAttribute("deleteProduct", deleteProduct);
        model.addAttribute("id", id);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteProduct(Model model, @ModelAttribute("deleteProduct") Product product) {
        this.productService.deleteProduct(product.getId());
        return "redirect:/admin/product";
    }

}
