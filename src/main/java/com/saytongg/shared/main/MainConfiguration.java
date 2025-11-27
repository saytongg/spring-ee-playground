package com.saytongg.shared.main;

import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@ComponentScan(basePackages= {"com.saytongg"})
public class MainConfiguration {

	@Bean
	@Profile("dev")
	public static PropertySourcesPlaceholderConfigurer devPropertySourcesPlaceholderConfigurer() throws IOException {
		Resource[] resourceList = ArrayUtils.addAll(
				new PathMatchingResourcePatternResolver().getResources("classpath:properties/general/*.properties"),
				new PathMatchingResourcePatternResolver().getResources("classpath:properties/dev/*.properties"));

		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);
		propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
		propertySourcesPlaceholderConfigurer.setLocations(resourceList);
		propertySourcesPlaceholderConfigurer.setLocalOverride(true);

		return propertySourcesPlaceholderConfigurer;
	}
	
	@Bean
	@Profile("staging")
	public static PropertySourcesPlaceholderConfigurer stagingPropertySourcesPlaceholderConfigurer() throws IOException {
		Resource[] resourceList = ArrayUtils.addAll(
				new PathMatchingResourcePatternResolver().getResources("classpath:properties/general/*.properties"),
				new PathMatchingResourcePatternResolver().getResources("classpath:properties/staging/*.properties"));

		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);
		propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
		propertySourcesPlaceholderConfigurer.setLocations(resourceList);
		propertySourcesPlaceholderConfigurer.setLocalOverride(true);

		return propertySourcesPlaceholderConfigurer;
	}
	
	@Bean
	@Profile("prod")
	public static PropertySourcesPlaceholderConfigurer prodPropertySourcesPlaceholderConfigurer() throws IOException {
		Resource[] resourceList = ArrayUtils.addAll(
				new PathMatchingResourcePatternResolver().getResources("classpath:properties/general/*.properties"),
				new PathMatchingResourcePatternResolver().getResources("classpath:properties/prod/*.properties"));

		PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);
		propertySourcesPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
		propertySourcesPlaceholderConfigurer.setLocations(resourceList);
		propertySourcesPlaceholderConfigurer.setLocalOverride(true);

		return propertySourcesPlaceholderConfigurer;
	}
}
