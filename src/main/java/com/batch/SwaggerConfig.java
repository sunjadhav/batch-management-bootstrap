package com.batch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.common.base.Predicates;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//http://localhost:8080/swagger-ui.html#/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
				.paths(PathSelectors.any()).build()
				.apiInfo(metaData());
	}
	
	 @SuppressWarnings("deprecation")
	private ApiInfo metaData() {
			ApiInfo apiInfo = new ApiInfo(
	                "Batch Management REST APIs",
	                "Base package to scan Endpoints : 'com.batch'",
	                "v0.1",
	                "Terms of service",
	                "Developed by: Sunil.Jadhav @2020 SUN's SOFTWARE",
	                "Architectural Document",
	                "https://www.documentNotYetPrepared.org" );
	        return apiInfo;
	    }
}
