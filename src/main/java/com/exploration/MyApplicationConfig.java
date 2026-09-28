package com.exploration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class MyApplicationConfig {
    
    @Bean
    public List<String> messages() {
        return new ArrayList<>();
    }
}
