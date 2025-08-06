package com.school.security.services.implementations;

import com.school.security.controllers.api.InvitationSseController;
import com.school.security.dtos.requests.UserReqDto;
import com.school.security.dtos.responses.UserResDto;
import com.school.security.entities.Role;
import com.school.security.entities.User;
import com.school.security.enums.RoleType;
import com.school.security.exceptions.EntityException;
import com.school.security.mappers.UserMapper;
import com.school.security.repositories.DirectionRepository;
import com.school.security.repositories.RoleRepository;
import com.school.security.repositories.SpecialityRepository;
import com.school.security.repositories.UserRepository;
import com.school.security.services.contracts.UserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    private BCryptPasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private DirectionRepository directionRepository;
    private SpecialityRepository specialityRepository;
    private InvitationSseController invitationSseController;

    @Override
    public UserResDto createOrUpdate(UserReqDto toSave) {
        Optional<User> userOptional = userRepository.findByEmail(toSave.email());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            user.setFirstname(toSave.firstname());
            user.setLastname(toSave.lastname());
            user.setGender(toSave.gender());

            if (toSave.password() != null && !toSave.password().isBlank()) {
                user.setPwd(passwordEncoder.encode(toSave.password()));
            }

            user.setDirection(directionRepository.getReferenceById(toSave.directionId()));

            if (toSave.specialityId() != null) {
                user.setSpeciality(specialityRepository.getReferenceById(toSave.specialityId()));
            } else {
                user.setSpeciality(null);
            }
            var userToSave = userRepository.save(user);
            Long count = getAccountNoRole();
            invitationSseController.sendInvitationUpdate(count);
            invitationSseController.notifyInvitationList(
                    userRepository.findAll().stream()
                            .filter(users -> users.getRoles().isEmpty())
                            .map(userMapper::toDto)
                            .collect(Collectors.toList()));
            return userMapper.toDto(userToSave);

        } else {
            User user = userMapper.fromDto(toSave);

            user.setDirection(directionRepository.getReferenceById(toSave.directionId()));

            if (toSave.specialityId() != null) {
                user.setSpeciality(specialityRepository.getReferenceById(toSave.specialityId()));
            }

            if (toSave.password() != null && !toSave.password().isBlank()) {
                user.setPwd(passwordEncoder.encode(toSave.password()));
            }
            var userToSave = userRepository.save(user);
            Long count = getAccountNoRole();
            invitationSseController.sendInvitationUpdate(count);
            invitationSseController.notifyInvitationList(
                    userRepository.findAll().stream()
                            .filter(users -> users.getRoles().isEmpty())
                            .map(userMapper::toDto)
                            .collect(Collectors.toList()));
            return userMapper.toDto(userToSave);
        }
    }

    @Override
    public List<UserResDto> findAll() {
        return this.userRepository.findAll().stream()
                .map(this.userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResDto findById(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return this.userMapper.toDto(user);
        } else {
            throw new EntityException("User not found with ID " + id);
        }
    }

    @Override
    public UserResDto deleteById(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            User userToDelete = userOptional.get();
            this.userRepository.deleteById(id);
            Long count = getAccountNoRole();
            invitationSseController.sendInvitationUpdate(count);
            return this.userMapper.toDto(userToDelete);
        } else {
            throw new EntityException("Unable to delete user: user not found with ID " + id);
        }
    }

    @Override
    public UserResDto attachRole(String email, RoleType name) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        Optional<Role> optionalRole = roleRepository.findByName(name);

        if (optionalUser.isPresent() && optionalRole.isPresent()) {
            User user = optionalUser.get();
            Role role = optionalRole.get();
            user.getRoles().clear();
            user.addRole(role);
            Long count = getAccountNoRole();
            invitationSseController.sendInvitationUpdate(count);
            return this.userMapper.toDto(userRepository.save(user));
        } else {
            throw new EntityException("User or Role not found");
        }
    }

    @Override
    public UserResDto detachRole(String email, RoleType name) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        Optional<Role> optionalRole = roleRepository.findByName(name);

        if (optionalUser.isPresent() && optionalRole.isPresent()) {
            User user = optionalUser.get();
            Role role = optionalRole.get();
            user.removeRole(role);
            Long count = getAccountNoRole();
            invitationSseController.sendInvitationUpdate(count);
            return this.userMapper.toDto(userRepository.save(user));
        } else {
            throw new EntityException("User or Role not found");
        }
    }

    @Override
    public UserDetailsService userDetailsService() {
        return email ->
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository
                .findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    }

    @Override
    public UserResDto updatePassword(String email, String newPassword) {
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPwd(passwordEncoder.encode(newPassword));
        User saved = userRepository.save(user);
        return this.userMapper.toDto(saved);
    }

    @Override
    public UserResDto getUserRestByEmail(String email) {
        Optional<User> users = this.userRepository.findByEmail(email);
        if (users.isPresent()) {
            var user = users.get();
            return this.userMapper.toDto(user);
        } else {
            throw new RuntimeException("User not found ");
        }
    }

    @Override
    public Long getAccountNoRole() {
        return this.userRepository.findAll().stream()
                .filter((user -> user.getRoles().isEmpty()))
                .count();
    }
    ;
}
