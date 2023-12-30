package com.safetynet.alerts.config;

import com.safetynet.alerts.constant.Data;
import com.safetynet.alerts.dto.DataDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataDto dataSource(WebClient client) {
        DataDto data = null;
        try {
            data = client.get().uri(Data.URL).retrieve().bodyToMono(DataDto.class).block();
        } catch (Exception e) {
            // log
            System.out.println(e.getMessage());
        }
        return data;
    }
}
