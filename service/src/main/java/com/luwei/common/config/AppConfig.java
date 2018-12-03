package com.luwei.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * @author Leone
 * @since 2018-07-31
 **/
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Converter<String, Date> timestampConverter() {
        Converter<String, Date> timestampConverter = new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                final Date date = new Date(new Long(source));
                return date;
            }
        };
        return timestampConverter;
    }

}
