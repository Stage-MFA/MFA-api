package com.school.security.controllers.auth;

import com.school.security.dtos.requests.LoginReqDto;
import com.school.security.dtos.requests.RefreshReqDto;
import com.school.security.dtos.requests.UserReqDto;
import com.school.security.dtos.responses.LoginResDto;
import com.school.security.dtos.responses.UserResDto;
import com.school.security.entities.User;
import com.school.security.securities.services.JwtService;
import com.school.security.services.contracts.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/auth")

public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResDto login(@RequestBody LoginReqDto credential){
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credential.email(), credential.password())
        );
        var user = this.userService.findByEmail(credential.email());
        var jwt = this.jwtService.generateToken(user);
        var refreshToken = this.jwtService.generateRefreshToken(new HashMap<>(), user);
        return new LoginResDto(
                jwt,
                refreshToken
        );
    }

    @PostMapping("/register")
    public UserResDto register(@RequestBody UserReqDto userReqDto){
        return  this.userService.createOrUpdate(userReqDto);
    }

    @PostMapping("/refresh")
    public LoginResDto refreshToken(@RequestBody RefreshReqDto refreshReqDto){
        var username = jwtService.extractUsername(refreshReqDto.refreshToken());
        var user = this.userService.findByEmail(username);
        if (this.jwtService.isTokenValid(refreshReqDto.refreshToken(), user)){
            var jwt = this.jwtService.generateToken(user);
            return new LoginResDto(
                    jwt,
                    refreshReqDto.refreshToken()
            );
        }
        return null;
    }
}
