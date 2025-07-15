package com.school.security.controllers.api;

import com.school.security.dtos.responses.UserResDto;
import com.school.security.services.contracts.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public List<UserResDto> getAllUsers(){
        return this.userService.findAll();
    }

    @DeleteMapping("/{id}")
    public UserResDto deleteById(@PathVariable Long id){
        return this.userService.deleteById(id);
    }

    @GetMapping("/{id}")
    public UserResDto getUser(@PathVariable Long id){
        return this.userService.findById(id);
    }
}
