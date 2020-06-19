package com.platform.admin.repository;

import com.platform.admin.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminRoleRepository extends JpaRepository<AdminRole, String> {

    @Query("select t from AdminRole t where t.admin.adminId = :adminId")
    List<AdminRole> findByUserId(@Param(value = "adminId") String adminId);

}
