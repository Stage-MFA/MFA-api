package com.school.security.controllers.api;

import com.school.security.dtos.requests.AttachRoleReqDto;
import com.school.security.dtos.requests.PwdReqDto;
import com.school.security.dtos.responses.OrganisationStatisticsResDto;
import com.school.security.dtos.responses.TechnicianStatisticResDto;
import com.school.security.dtos.responses.UserResDto;
import com.school.security.dtos.responses.UserStatisticsResDto;
import com.school.security.services.contracts.UserService;
import com.school.security.services.implementations.OrganisationServiceImpl;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(
        origins = {
            "http://localhost:3000",
            "https://mfamaintenance.netlify.app/",
            "http://192.168.1.133:3000/"
        })
public class UserController {

    private final UserService userService;
    private final OrganisationServiceImpl organisationService;

    public UserController(UserService userService, OrganisationServiceImpl organisationService) {
        this.userService = userService;
        this.organisationService = organisationService;
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

    @GetMapping("/email")
    public UserResDto getUserByEmail(@RequestParam String email) {
        return this.userService.getUserRestByEmail(email);
    }

    @PutMapping("/pwd")
    public UserResDto updatePassword(@RequestBody PwdReqDto pwdReqDto) {
        return userService.updatePassword(pwdReqDto.email(), pwdReqDto.password());
    }

    @PutMapping("/role")
    public ResponseEntity<UserResDto> updateRole(@RequestBody AttachRoleReqDto attachRoleRegDto) {
        UserResDto userResDto =
                userService.attachRole(attachRoleRegDto.email(), attachRoleRegDto.role());
        return ResponseEntity.ok(userResDto);
    }

    @DeleteMapping("/role")
    public ResponseEntity<UserResDto> deleteRole(@RequestBody AttachRoleReqDto attachRoleRegDto) {
        UserResDto userResDto =
                userService.detachRole(attachRoleRegDto.email(), attachRoleRegDto.role());
        return ResponseEntity.ok(userResDto);
    }

    @GetMapping("/invitation")
    public Long getInvitationCount() {
        return this.userService.getAccountNoRole();
    }

    @GetMapping("/statistics")
    public UserStatisticsResDto userStatistics() {
        return this.userService.getStatisticUsers();
    }

    @GetMapping("/statistics/technician")
    public TechnicianStatisticResDto technician() {
        return this.userService.getStatisticTechnician();
    }

    @GetMapping("/organisation")
    public OrganisationStatisticsResDto getOrganisationStatistics() {
        return this.organisationService.getOrganisationStatistics();
    }
}
