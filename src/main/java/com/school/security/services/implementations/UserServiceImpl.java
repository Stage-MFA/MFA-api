package com.school.security.services.implementations;

import com.school.security.dtos.requests.RoleReqDto;
import com.school.security.dtos.requests.UserReqDto;
import com.school.security.dtos.responses.UserResDto;
import com.school.security.entities.Role;
import com.school.security.entities.User;
import com.school.security.enums.RoleType;
import com.school.security.exceptions.EntityException;
import com.school.security.mappers.RoleMapper;
import com.school.security.mappers.UserMapper;
import com.school.security.repositories.RoleRepository;
import com.school.security.repositories.UserRepository;
import com.school.security.services.contracts.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    private RoleMapper roleMapper;
    private BCryptPasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    private UserRepository userRepository;

    @Override
    public UserResDto createOrUpdate(UserReqDto toSave) {
        String pwd = toSave.password();
        User user = this.userMapper.fromDto(toSave);
        if (pwd != null) {
            user.setPwd(passwordEncoder.encode(pwd));
        }
        if (user.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByName(RoleType.USER)
                    .orElseGet(() -> {
                        RoleReqDto roleReqDto = new RoleReqDto(RoleType.USER);
                        Role role = roleMapper.fromDto(roleReqDto);
                        return roleRepository.save(role);
                    });
            user.addRole(defaultRole);
        }

        return this.userMapper.toDto(this.userRepository.save(user));
    }

    @Override
    public List<UserResDto> findAll() {
        return this.userRepository.findAll()
                .stream().map(this.userMapper::toDto)
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
            user.addRole(role);
            return  this.userMapper.toDto(userRepository.save(user));
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
           return  this.userMapper.toDto(userRepository.save(user));
        } else {
            throw new EntityException("User or Role not found");
        }
    }

    @Override
    public UserDetailsService userDetailsService() {
        return email -> userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    }
}
