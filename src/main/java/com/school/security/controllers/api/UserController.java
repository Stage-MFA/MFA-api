package com.school.security.controllers.api;

import com.school.security.dtos.requests.PwdReqDto;
import com.school.security.dtos.responses.UserResDto;
import com.school.security.services.contracts.UserService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.1.133:3000/"})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResDto> getAllUsers() {
        return this.userService.findAll();
    }

    @DeleteMapping("/{id}")
    public UserResDto deleteById(@PathVariable Long id) {
        return this.userService.deleteById(id);
    }

    @GetMapping("/{id}")
    public UserResDto getUser(@PathVariable Long id) {
        return this.userService.findById(id);
    }

    @PutMapping("/pwd")
    public UserResDto updatePassword(@RequestBody PwdReqDto pwdReqDto) {
        return userService.updatePassword(pwdReqDto.email(), pwdReqDto.password());
    }
}
