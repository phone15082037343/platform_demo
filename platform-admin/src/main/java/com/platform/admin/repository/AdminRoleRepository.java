package com.platform.admin.repository;

import com.platform.admin.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface AdminRoleRepository extends JpaRepository<AdminRole, String> {

    @Query("select t from AdminRole t where t.admin.adminId = :adminId")
    List<AdminRole> findByAdminId(@Param(value = "adminId") String adminId);

    @Transactional
    @Modifying
    @Query("delete from AdminRole where admin.adminId = :adminId and role.roleId = :roleId")
    void delete(@Param(value = "adminId") String adminId, @Param(value = "roleId") String roleId);

}
