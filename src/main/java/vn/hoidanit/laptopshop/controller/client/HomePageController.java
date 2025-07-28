package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.eclipse.tags.shaded.java_cup.runtime.lr_parser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.OrderService;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomePageController {

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;

    public HomePageController(ProductService productService, UserService userService, OrderService orderService,
            PasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String getHomePage(Model model, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> products = this.productService.getAllProduct(pageable);
        List<Product> listProducts = products.getContent();
        model.addAttribute("product", listProducts);
        HttpSession session = request.getSession(false);
        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String postMethodName(Model model,
            @ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
            BindingResult bindingResult) {

        // Valitate data

        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }

        User user = this.userService.registerDTOtoUser(registerDTO);
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName("USER"));
        this.userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {

        return "client/auth/login";
    }

    @GetMapping("/access-deny")
    public String getDenyPage(Model model) {

        return "client/auth/deny";
    }

    @GetMapping("/order-history")
    public String getOrderHistory(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        User user = this.userService.getUserById(id);
        List<Order> orders = this.orderService.getOrderByUser(user);
        model.addAttribute("orders", orders);
        return "client/cart/order-history";
    }

    @GetMapping("/products")
    public String getProductPage(Model model,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        int safePage = Math.max(page, 1); // không cho nhỏ hơn 1
        Pageable pageable = PageRequest.of(safePage - 1, 4);
        Page<Product> pageProducts = productService.getAllProduct(pageable);

        // Nếu page > totalPages thì quay lại trang cuối cùng
        if (safePage > pageProducts.getTotalPages() && pageProducts.getTotalPages() > 0) {
            pageable = PageRequest.of(pageProducts.getTotalPages() - 1, 4);
            pageProducts = productService.getAllProduct(pageable);
            safePage = pageProducts.getTotalPages();
        }

        model.addAttribute("products", pageProducts.getContent());
        model.addAttribute("currentPage", safePage);
        model.addAttribute("totalPages", pageProducts.getTotalPages());

        return "client/product/show";
    }

}
