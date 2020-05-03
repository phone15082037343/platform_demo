package com.platform.cas.user.repository;

import com.platform.cas.user.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, String> {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 未查询到返回null
     */
    Administrator findByUsername(String username);

}
