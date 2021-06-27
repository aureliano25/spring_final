package com.pavliuk.spring.service;

import com.pavliuk.spring.model.User;
import com.pavliuk.spring.model.CustomUserDetails;
import com.pavliuk.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

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
}
