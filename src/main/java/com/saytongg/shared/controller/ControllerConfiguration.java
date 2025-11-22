package com.saytongg.shared.controller;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
@EnableWebMvc
public class ControllerConfiguration implements WebMvcConfigurer {

	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> messageConverters) {        
        // JSON converter
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(objectMapper());
        
        // XML converters
     	MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
     	xmlConverter.setMarshaller(marshaller());
     	xmlConverter.setUnmarshaller(marshaller());
     	
        messageConverters.add(jsonConverter);
        messageConverters.add(xmlConverter);
    }
	
	@Bean
    public ObjectMapper objectMapper() {
    	final ObjectMapper mapper = new ObjectMapper();
    	
    	mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    	mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    	
    	mapper.registerModule(new JavaTimeModule());
    	
        return mapper;
    }
	
	@Bean
    public Jaxb2Marshaller marshaller() {
		final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.saytongg");
        
        return marshaller;
    }
}
