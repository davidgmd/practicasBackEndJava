package com.practise.config;

import com.viewnext.debugservice.service.ListService;
import com.viewnext.debugservice.service.impl.ListServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    ListService listService() {
        return new ListServiceImpl();
    }
}
