package com.pavliuk.spring.service;

import com.pavliuk.spring.dto.SignUpFormDto;
import com.pavliuk.spring.exception.UserAlreadyExistsException;
import com.pavliuk.spring.model.User;
import com.pavliuk.spring.model.CustomUserDetails;
import com.pavliuk.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("user " + username + " not found"));

        return new CustomUserDetails(user);
    }

    public boolean blockUser(Long userId) {
        int updatedRows = userRepository.blockUserById(userId);

        return updatedRows != 0;
    }

    public boolean unblockUser(Long userId) {
        int updatedRows = userRepository.unblockUserById(userId);

        return updatedRows != 0;
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
        user.setRoleId((long)2);

        try {
            return userRepository.save(user);
        } catch (RuntimeException e) {
            throw new UserAlreadyExistsException("User with login " + user.getLogin() + " already exists");
        }
    }
}
