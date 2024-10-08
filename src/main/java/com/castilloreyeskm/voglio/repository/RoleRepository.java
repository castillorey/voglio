package com.castilloreyeskm.voglio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.castilloreyeskm.voglio.model.Role;

public interface  RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String role);
}
