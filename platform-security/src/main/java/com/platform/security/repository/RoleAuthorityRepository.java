package com.platform.security.repository;

import com.platform.security.entity.RoleAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleAuthorityRepository extends JpaRepository<RoleAuthority, String> {

    @Query(value = "select t from RoleAuthority t where t.authority.authorityId = :authorityId")
    List<RoleAuthority> findByAuthorityId(@Param(value = "authorityId") String authorityId);

    @Query(value = "select t from RoleAuthority t where t.authority.authorityId in (:authorityIds)")
    List<RoleAuthority> findByAuthorityIds(@Param(value = "authorityIds") List<String> authorityIds);

}
