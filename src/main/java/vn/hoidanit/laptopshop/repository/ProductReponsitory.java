package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Product;

@Repository
public interface ProductReponsitory extends JpaRepository<Product, Long> {

    Product save(Product product);

    List<Product> findAll();

    List<Product> findAllByName(String name);

    List<Product> findAllByPrice(double price);

    Product findById(long id);

    Product deleteById(long id);

}
