package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.OrderService;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    public String getOrderPage(Model model) {
        List<Order> orders = this.orderService.getAllOrder();
        model.addAttribute("order", orders);
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    public String getOrderDetailPage(Model model, @PathVariable long id) {
        Order order = this.orderService.getOrderById(id);
        List<OrderDetail> orderDetails = this.orderService.getOrderDetailsByOrder(order);
        model.addAttribute("orderDetail", orderDetails);
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        Order order = this.orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String postUpdateOrder(Model model, @ModelAttribute("order") Order order) {
        Order orderUpdate = this.orderService.getOrderById(order.getId());
        if (orderUpdate != null) {
            orderUpdate.setStatus(order.getStatus());
            this.orderService.save(orderUpdate);
        }
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String getDeleteOrderPage(Model model, @PathVariable long id) {
        Order order = this.orderService.getOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("id", id);
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete")
    public String postDeleteProduct(Model model, @ModelAttribute("order") Order order) {
        this.orderService.deleteOrder(order);
        return "redirect:/admin/order";
    }
}
