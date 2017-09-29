package com.globallogic.melnykanton.parking.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan("com.globallogic.melnykanton.parking")
@Profile("memory")
public class MemoryConfig {
}
