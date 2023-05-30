package com.example.picshare.controller;

import com.example.picshare.domain.User;
import com.example.picshare.dto.UserDto;
import com.example.picshare.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list"; // user-list.html로 렌더링
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-details"; // user-details.html로 렌더링
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") UserDto userDto) {
        userService.createUser(userDto);
        return "redirect:/users";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "user-form"; // user-form.html로 렌더링
    }
}
