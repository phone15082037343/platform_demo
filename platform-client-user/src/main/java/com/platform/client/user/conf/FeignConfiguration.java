package com.platform.client.user.conf;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public Logger.Level level() {
        return Logger.Level.FULL;
    }


}
