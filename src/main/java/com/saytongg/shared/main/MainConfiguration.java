package com.saytongg.shared.main;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@ComponentScan(basePackages= {"com.saytongg"})
public class MainConfiguration {

	@Bean
	public static PropertySourcesPlaceholderConfigurer devPropertySourcesPlaceholderConfigurer() throws IOException {
		Resource[] resourceList = new PathMatchingResourcePatternResolver().getResources("classpath:*.properties");

		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);
		propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
		propertySourcesPlaceholderConfigurer.setLocations(resourceList);
		propertySourcesPlaceholderConfigurer.setLocalOverride(true);

		return propertySourcesPlaceholderConfigurer;
	}
}
