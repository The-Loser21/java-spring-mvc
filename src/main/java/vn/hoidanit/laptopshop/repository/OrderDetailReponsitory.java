package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;

@Repository
public interface OrderDetailReponsitory extends JpaRepository<OrderDetail, Long> {
    OrderDetail save(OrderDetail orderDetail);

    List<OrderDetail> findByOrder(Order order);
}
