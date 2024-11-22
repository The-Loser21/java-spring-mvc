package vn.hoidanit.laptopshop.controller.admin;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    // DI : Dipendency injection
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        // String test = this.userService.handleHello();
        // model.addAttribute("hai", test);
        List<User> arrUser = this.userService.getAllUser();
        System.out.println(arrUser);
        model.addAttribute("id", "Hello everyone");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String loginUser(Model model) {
        // model.addAttribute("newUser", new User());
        List<User> users = this.userService.getAllUser();
        model.addAttribute("user", users);
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        // model.addAttribute("newUser", new User());
        User userView = this.userService.getUserById(id);
        model.addAttribute("info", userView);
        model.addAttribute("id", id);
        return "admin/user/table";
    }

    // view information user
    @RequestMapping("/admin/user/create")
    public String createUser(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    // get data @ModelAttribute
    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User hoidanit) {
        System.out.println("run here" + hoidanit);
        this.userService.saveUser(hoidanit);
        return "redirect:/admin/user"; // quay về tran redirect + url
    }

    @RequestMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User curentUser = this.userService.getUserById(id);
        model.addAttribute("userUpdate", curentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("userUpdate") User user) {
        User currenUser = this.userService.getUserById(user.getId());
        if (currenUser != null) {
            currenUser.setAddress(user.getAddress());
            currenUser.setFullName(user.getFullName());
            currenUser.setPhone(user.getPhone());
            // currenUser.setEmail(user.getEmail());
            this.userService.saveUser(currenUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        User userDelete = new User();
        userDelete.setId(id);
        model.addAttribute("userDelete", userDelete);
        model.addAttribute("id", id);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("userDelete") User user) {
        this.userService.deleteUser(user.getId());
        return "redirect:/admin/user";
    }

}
