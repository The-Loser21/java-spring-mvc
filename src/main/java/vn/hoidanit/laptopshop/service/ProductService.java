package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailReponsitory;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.OrderDetailReponsitory;
import vn.hoidanit.laptopshop.repository.OrderReponsitory;
import vn.hoidanit.laptopshop.repository.ProductReponsitory;

@Service
public class ProductService {
    private final ProductReponsitory productReponsitory;
    private final CartRepository cartRepository;
    private final CartDetailReponsitory cartDetailReponsitory;
    private final UserService userService;
    private final OrderReponsitory orderReponsitory;
    private final OrderDetailReponsitory orderDetailReponsitory;

    public ProductService(ProductReponsitory productReponsitory, CartRepository cartRepository, UserService userService,
            CartDetailReponsitory cartDetailReponsitory, OrderReponsitory orderReponsitory,
            OrderDetailReponsitory orderDetailReponsitory) {
        this.productReponsitory = productReponsitory;
        this.cartDetailReponsitory = cartDetailReponsitory;
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.orderDetailReponsitory = orderDetailReponsitory;
        this.orderReponsitory = orderReponsitory;
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

    public void handleAddProductToCart(String email, long productId, HttpSession session) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            Cart cart = this.cartRepository.findByUser(user);
            if (cart == null) {
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setSum(0);

                cart = this.cartRepository.save(newCart);
            }

            Product product = this.productReponsitory.findById(productId);
            if (product != null) {
                // check đã tồn tại sản phẩm trong Cart hay chưa?
                CartDetail checkCartDetail = this.cartDetailReponsitory.findByCartAndProduct(cart, product);

                if (checkCartDetail == null) {
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setPrice(product.getPrice());
                    cartDetail.setQuanity(1);
                    cartDetail.setProduct(product);
                    this.cartDetailReponsitory.save(cartDetail);
                    // lưu sum sản phẩm vào cart
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);
                } else {
                    checkCartDetail.setQuanity(checkCartDetail.getQuanity() + 1);
                    this.cartDetailReponsitory.save(checkCartDetail);
                }

            }
        }
    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public void handleDeleteProductToCart(long id, HttpSession session) {
        Optional<CartDetail> cartDetail = this.cartDetailReponsitory.findById(id);
        if (cartDetail.isPresent()) {
            CartDetail cartDetailDelete = cartDetail.get();

            Cart cart = cartDetailDelete.getCart();

            this.cartDetailReponsitory.delete(cartDetailDelete);
            if (cart.getSum() > 1) {
                int s = cart.getSum() - 1;
                cart.setSum(s);
                this.cartRepository.save(cart);
                session.setAttribute("sum", s);
            } else {
                this.cartRepository.delete(cart);
                session.setAttribute("sum", 0);
            }
        }
    }

    public void handleUpdateCartBeforeCheckOut(List<CartDetail> cartDetail) {
        for (CartDetail cartDetail1 : cartDetail) {
            Optional<CartDetail> cdOptional = this.cartDetailReponsitory.findById(cartDetail1.getId());
            if (cdOptional.isPresent()) {
                CartDetail currenCartDetail = cdOptional.get();
                currenCartDetail.setQuanity(cartDetail1.getQuanity());
                this.cartDetailReponsitory.save(currenCartDetail);
            }
        }
    }

    public void handlePalceOrder(User user, HttpSession session, String receiveName, String receiveAddress,
            String receivePhone) {

        // set orderDetail
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = this.cartDetailReponsitory.findByCart(cart);
            if (cartDetails != null) {

                double sum = 0;
                for (CartDetail cartDetail : cartDetails) {
                    sum += cartDetail.getPrice() * cartDetail.getQuanity();
                }
                Order order = new Order();
                order.setUser(user);
                order.setReceiveName(receiveName);
                order.setReceiveAddress(receiveAddress);
                order.setReceivePhone(receivePhone);
                order.setStatus("PENDING");
                order.setTotalPrice(sum);
                order = this.orderReponsitory.save(order);

                for (CartDetail cartDetail : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cartDetail.getProduct());
                    orderDetail.setPrice(cartDetail.getPrice());
                    orderDetail.setQuantity(cartDetail.getQuanity());
                    this.orderDetailReponsitory.save(orderDetail);
                }

                for (CartDetail cartDetail : cartDetails) {
                    this.cartDetailReponsitory.deleteById(cartDetail.getId());
                }

                this.cartRepository.deleteById(cart.getId());
                session.setAttribute("sum", 0);
            }
        }
    }

    public long countProduct() {
        return this.productReponsitory.count();
    }
}
