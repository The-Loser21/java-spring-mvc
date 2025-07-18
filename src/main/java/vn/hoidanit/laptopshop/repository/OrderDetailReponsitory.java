package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;

@Repository
public interface OrderDetailReponsitory extends JpaRepository<Product, Long> {
    OrderDetail save(OrderDetail orderDetail);
}
