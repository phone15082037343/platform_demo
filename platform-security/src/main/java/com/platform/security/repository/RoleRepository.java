package com.platform.security.repository;

import com.platform.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, String> {

    @Query(value = "select t from Role t where t.roleId in (:roleIds)")
    List<Role> findByRoleIds(@Param(value = "roleIds") List<String> roleIds);

}
