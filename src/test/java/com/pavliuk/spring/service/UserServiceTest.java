package com.pavliuk.spring.service;

import com.pavliuk.spring.dto.SignUpFormDto;
import com.pavliuk.spring.dto.UserDto;
import com.pavliuk.spring.exception.UserAlreadyExistsException;
import com.pavliuk.spring.model.User;
import com.pavliuk.spring.repository.RoleRepository;
import com.pavliuk.spring.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Before
    public void init() {
        User admin = mock(User.class);
        when(admin.getFirstName()).thenReturn("firstName");
        when(admin.getLastName()).thenReturn("lastName");

        when(userRepository.findByLogin("admin"))
                .thenReturn(Optional.of(admin));
    }

    @Test
    public void shouldReturnUserDetailsIfUserFound() {
        UserDetails user = userService.loadUserByUsername("admin");
        assertEquals("firstName lastName", user.getUsername());
    }

    @Test
    public void shouldThrowExceptionIfUserNotFound() {
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("student"));
    }

    @Test
    public void shouldDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldCreateUserAccount() throws UserAlreadyExistsException {
        SignUpFormDto formDto = mock(SignUpFormDto.class);
        when(formDto.getFirstName()).thenReturn("firstName");
        when(formDto.getLastName()).thenReturn("lastName");
        when(formDto.getLogin()).thenReturn("student");

        userService.registerNewUserAccount(formDto);
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void shouldThrowExceptionIfUserAlreadyExists() {
        when(userRepository.save(any(User.class))).thenThrow(RuntimeException.class);
        SignUpFormDto formDto = mock(SignUpFormDto.class);
        when(formDto.getFirstName()).thenReturn("firstName");
        when(formDto.getLastName()).thenReturn("lastName");
        when(formDto.getLogin()).thenReturn("student");

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerNewUserAccount(formDto));
    }
}