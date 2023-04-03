package com.codewithsahu.blog.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.event.PublicInvocationEvent;
import org.springframework.security.web.header.writers.frameoptions.StaticAllowFromStrategy;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER="Authorization";
	
	private ApiKey apiKeys()
	{
		return new ApiKey("JWT",AUTHORIZATION_HEADER , "header");
	}
	
	
	private List<SecurityContext> securityContexts()
	{
		return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
	}
	
	
	
	private List<SecurityReference> securityReferences()
	{
		AuthorizationScope scopes = new springfox.documentation.service.AuthorizationScope("global","AccessEverything");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] { scopes }));
	}
	
	@Bean
	 public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
			
	}

	private ApiInfo getInfo() {
	
		
		return new ApiInfo("Blogging Application : Backend Course",
				"This project is developed by Sahu", "1.0", "Terms of Service",
				new Contact("Rishabh", "https://learncodewithdurgesh.com", "Rishabh.sahu@hybrisworld.com"),
				"License of APIS", "API license URL",Collections.emptyList());
	}

}
