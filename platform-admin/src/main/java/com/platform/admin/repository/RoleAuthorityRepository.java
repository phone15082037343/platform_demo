package com.platform.admin.repository;

import com.platform.admin.entity.RoleAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface RoleAuthorityRepository extends JpaRepository<RoleAuthority, String> {

    @Query(value = "select t from RoleAuthority t where t.authority.authorityId = :authorityId")
    List<RoleAuthority> findByAuthorityId(@Param(value = "authorityId") String authorityId);

    @Query(value = "select t from RoleAuthority t where t.authority.authorityId in (:authorityIds)")
    List<RoleAuthority> findByAuthorityIds(@Param(value = "authorityIds") List<String> authorityIds);

    @Query(value = "select t from RoleAuthority t where t.role.roleId = :roleId")
    List<RoleAuthority> findbyRoleId(@Param(value = "roleId") String roleId);

    @Transactional
    @Modifying
    @Query("delete from RoleAuthority where role.roleId = :roleId and authority.authorityId = :authorityId")
    void delete(@Param(value = "roleId") String roleId, @Param(value = "authorityId") String authorityId);

}
