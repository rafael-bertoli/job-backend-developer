/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelipost.userservice;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Componente de configuração do swagger 2.
 *
 * @see https://swagger.io/
 * @author Rafael
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.intelipost.userservice.controller"))
                .paths(PathSelectors.ant("/users/*"))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Arrays.asList(new BasicAuth("basicAuth")));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Serviço de usuário",
                "Esta documentação é referente ao serviço de usuário desenvolvido para o desafio proposto pela empresa Intelipost.",
                "v1",
                "Termo de serviço",
                new Contact("Rafael Bertoli", "", "rafabertolyy@hotmail.com"),
                "Apache License Version 2.0", 
                "https://www.apache.org/licenses/LICENSE-2.0", 
                Collections.emptyList());
    }
}
