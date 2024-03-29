package com.pavliuk.spring.service;

import com.pavliuk.spring.dto.SignUpFormDto;
import com.pavliuk.spring.exception.UserAlreadyExistsException;
import com.pavliuk.spring.model.Role;
import com.pavliuk.spring.model.User;
import com.pavliuk.spring.model.CustomUserDetails;
import com.pavliuk.spring.model.UserRole;
import com.pavliuk.spring.repository.RoleRepository;
import com.pavliuk.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("user " + username + " not found"));

        return new CustomUserDetails(user);
    }

    public boolean blockUser(Long userId) {
        return userRepository.blockUserById(userId) != 0;
    }

    public boolean unblockUser(Long userId) {
        return userRepository.unblockUserById(userId) != 0;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User registerNewUserAccount(SignUpFormDto signUpForm) throws UserAlreadyExistsException {
        User user = new User();
        user.setFirstName(HtmlUtils.htmlEscape(signUpForm.getFirstName()));
        user.setLastName(HtmlUtils.htmlEscape(signUpForm.getLastName()));
        user.setLogin(HtmlUtils.htmlEscape(signUpForm.getLogin()));
        user.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
        Role studentRole = roleRepository.findByRole(UserRole.STUDENT);
        user.setRoles(Collections.singletonList(studentRole));

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new UserAlreadyExistsException("User with login " + user.getLogin() + " already exists");
        }
    }
}
