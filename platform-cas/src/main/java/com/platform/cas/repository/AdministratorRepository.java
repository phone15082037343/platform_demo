package com.platform.cas.repository;

import com.platform.cas.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, String> {

    /**
     * 根据用户名查询
     * @param username 用户名
     * @return 数据库查询结果，为查询到返回null
     */
    Administrator findByUsername(String username);

}
