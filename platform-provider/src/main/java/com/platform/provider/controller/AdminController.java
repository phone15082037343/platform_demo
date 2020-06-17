package com.platform.provider.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platform.provider.entity.Administrator;
import com.platform.provider.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/admin")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping
    public Administrator save(@RequestBody Administrator administrator) {
        administrator.setCreateTime(new Date());
        adminRepository.save(administrator);
        return administrator;
    }

}
