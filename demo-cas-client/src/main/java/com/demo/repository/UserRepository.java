package com.demo.repository;

import com.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    /**
     * 根据用户名查询
     * @param username 用户名
     * @return 数据库查询结果，为查询到返回null
     */
    User findByUsername(String username);

}
