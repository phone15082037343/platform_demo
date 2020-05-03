package com.platform.cas.user.repository;

import com.platform.cas.user.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminRoleRepository extends JpaRepository<AdminRole, String> {

    /**
     * 根据用户ID查询
     * @param adminId 用户ID
     * @return 结果
     */
    @Query("select t from AdminRole t where t.admin.adminId = ?1")
    List<AdminRole> findByAdminId(String adminId);

}
