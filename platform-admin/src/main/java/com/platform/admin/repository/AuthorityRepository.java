package com.platform.admin.repository;

import com.platform.admin.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

    /**
     * 根据url查询权限
     * @param authorityUrl url地址
     * @return 数据库查询结果
     */
    Authority findByAuthorityUrl(String authorityUrl);

}
