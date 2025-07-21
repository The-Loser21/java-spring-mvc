package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.repository.OrderDetailReponsitory;
import vn.hoidanit.laptopshop.repository.OrderReponsitory;

@Service
public class OrderService {
    private final OrderReponsitory orderReponsitory;
    private final OrderDetailReponsitory orderDetailReponsitory;

    public OrderService(OrderReponsitory orderReponsitory, OrderDetailReponsitory orderDetailReponsitory) {
        this.orderReponsitory = orderReponsitory;
        this.orderDetailReponsitory = orderDetailReponsitory;
    }

    public List<Order> getAllOrder() {
        return this.orderReponsitory.findAll();
    }

    public List<OrderDetail> getOrderDetailsByOrder(Order order) {
        return this.orderDetailReponsitory.findByOrder(order);
    }

    public Order getOrderById(long id) {
        return this.orderReponsitory.findById(id);
    }

    public Order save(Order order) {
        return this.orderReponsitory.save(order);
    }

    public void deleteOrder(Order order) {
        List<OrderDetail> orderDetails = getOrderDetailsByOrder(order);
        for (OrderDetail orderDetail : orderDetails) {
            this.orderDetailReponsitory.delete(orderDetail);
        }
        this.orderReponsitory.delete(order);
    }

    public long countOrder() {
        return this.orderReponsitory.count();
    }
}
