package com.atlantis.config.scan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;

@Configuration
//@ComponentScan(basePackages = "com.atlantis.config.scan",
//excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Service.class}),
//        @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = {"*..User1"})
//})

@ComponentScan(basePackages = "com.atlantis.config.scan", useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Service.class}),
//                @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = {"*..User1"})
        })
//@ImportResource("applicationContext.xml")
public class AppConfig3 {

}
