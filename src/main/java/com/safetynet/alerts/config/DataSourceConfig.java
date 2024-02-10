package com.safetynet.alerts.config;

import com.safetynet.alerts.constant.Data;
import com.safetynet.alerts.dto.alerts.DataDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Log4j2
public class DataSourceConfig {
    @Bean
    public DataDto dataSource(WebClient client) {
        DataDto data = null;
        try {
            data = client.get().uri(Data.URL).retrieve().bodyToMono(DataDto.class).block();
            log.info("Object Data loaded");
        } catch (Exception e) {
            log.error("Failed to load Data : " + e.getMessage());
        }
        return data;
    }
}
