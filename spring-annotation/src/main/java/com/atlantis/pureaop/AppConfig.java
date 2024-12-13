package com.atlantis.pureaop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.atlantis.pureaop")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppConfig {
}
