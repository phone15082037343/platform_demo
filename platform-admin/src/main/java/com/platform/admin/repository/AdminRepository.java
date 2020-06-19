package com.platform.admin.repository;

import com.platform.admin.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Administrator, String> {

    Administrator findByUsername(String username);

}
