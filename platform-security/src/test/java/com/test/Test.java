package com.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

    @org.junit.Test
    public void passwod() {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }

}
