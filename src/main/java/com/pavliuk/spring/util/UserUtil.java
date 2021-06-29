package com.pavliuk.spring.util;

import com.pavliuk.spring.model.CustomUserDetails;
import com.pavliuk.spring.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
    public static User getCurrentUser() {
        CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return userDetails.getUser();
    }
}
