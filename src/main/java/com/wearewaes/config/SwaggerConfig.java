package com.wearewaes.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Represents the Swagger Configuration
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    /**
     * Create a documentation bean that defines the API documentation (based on Endpoints)
     *
     * @return the {@link Docket} with the documentation
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.wearewaes.controller"))
                .paths(PathSelectors.any()).build().pathMapping("/").apiInfo(metaData());
    }

    /**
     * Provides the API metadata with some custom info
     *
     * @return the {@link ApiInfo} with the API metadata
     */
    private ApiInfo metaData() {
        Contact contact = new Contact("Pedro Humberto Mattiollo", "https://github.com/pmattiollo", "pmattiollo@gmail.com");

        return new ApiInfo("Scalable Web Assigment", "Scalable Web Assigment Spring Boot project for WAES", "1.0",
                "Terms of Service: free to use and colaborate", contact, "Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }

}
