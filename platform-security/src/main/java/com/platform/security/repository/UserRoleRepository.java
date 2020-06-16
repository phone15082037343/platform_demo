package com.platform.security.repository;

import com.platform.security.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    @Query("select t from UserRole t where t.user.userId = :userId")
    List<UserRole> findByUserId(@Param(value = "userId") String userId);

}
