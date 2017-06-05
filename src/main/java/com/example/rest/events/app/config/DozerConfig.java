package com.example.rest.events.app.config;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Dozer configuration.
 */
@Configuration
public class DozerConfig {

    /**
     * Path of dozer configuration.
     */
    private static final String DOZER_FOR_REST_API_CONFIG_LOCATION = "mappings/dozer-dto-mapping.xml";

    /**
     * Initialize mapper for DTOs.
     */
    @Bean
    @Qualifier(value = "dtoDozerMapper")
    public DozerBeanMapper viewServiceDtoDozerMapper() {
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(Arrays.asList(DOZER_FOR_REST_API_CONFIG_LOCATION));
        return dozerBeanMapper;
    }
}
