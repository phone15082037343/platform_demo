package com.platform.provider.repository;

import com.platform.provider.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Administrator, String> {

}
