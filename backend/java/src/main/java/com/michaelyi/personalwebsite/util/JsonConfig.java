package com.michaelyi.personalwebsite.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {
        @Bean
        public ObjectMapper mapper() {
                return new ObjectMapper();
        }

        @Bean
        public ObjectWriter writer() {
                return mapper().writer().withDefaultPrettyPrinter();
        }
}
