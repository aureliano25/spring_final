package com.pavliuk.spring.repository;

import com.pavliuk.spring.model.Role;
import com.pavliuk.spring.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(UserRole roleName);
}
