package vn.hoidanit.laptopshop.controller;

import java.lang.reflect.Member;
import java.lang.reflect.Method;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {
    // DI : Dipendency injection
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        String test = this.userService.handleHello();
        model.addAttribute("hai", test);
        model.addAttribute("id", "Hello everyone");
        return "hello";
    }

    @RequestMapping("/admin/user") // method get
    public String loginUser(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    // get data @ModelAttribute
    @RequestMapping(value = "/admin/user/create1", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User hoidanit) {
        System.out.println("run here" + hoidanit);
        return "hello";
    }
}
