package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.ProductReponsitory;
import vn.hoidanit.laptopshop.repository.RoleRepository;

@Service
public class ProductService {
    private final ProductReponsitory productReponsitory;

    public ProductService(ProductReponsitory productReponsitory, RoleRepository roleRepository) {
        this.productReponsitory = productReponsitory;
    }

    public List<Product> getAllProduct() {
        return this.productReponsitory.findAll();
    }

    public List<Product> getAllProductByName(String name) {
        return this.productReponsitory.findAllByName(name);
    }

    public List<Product> getAllProductByPrice(double price) {
        return this.productReponsitory.findAllByPrice(price);
    }

    public Product getProductById(long id) {
        return this.productReponsitory.findById(id);
    }

    public Product deleteProduct(long id) {
        return this.productReponsitory.deleteById(id);
    }

    public Product saveProduct(Product product) {
        return this.productReponsitory.save(product);
    }
}
