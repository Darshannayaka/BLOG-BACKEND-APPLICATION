package com.codewithdurgesh.blog2.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
@io.swagger.v3.oas.annotations.security.SecurityScheme(
		name = "scheme1",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer")


@OpenAPIDefinition(
		info = @io.swagger.v3.oas.annotations.info.Info(title = "Blogging Application | Backend Project (JAVA)",
		description = "This Project is Developed by Junior Software Engineer Darshan",
		version = "1.0",
		contact = @Contact(name = "Darshan", email = "darshanpnayaka06@gmail.com",url = "https://github.com/Darshannayaka?tab=repositories"),
		license = @io.swagger.v3.oas.annotations.info.License(name = "OPEN License", url = "https://github.com/Darshannayaka?tab=repositories"))
		,
		externalDocs = @io.swagger.v3.oas.annotations.ExternalDocumentation(
				description = "This is external URL",url = "https://github.com/Darshannayaka?tab=repositories")
		)
public class SwaggerConfig {

	
	//BELOW-CODE-IS-ALSO-UPDATED-CODE
	//BUT-I-AM-GOING-TO-DO-IT-BY-ANNOTATIONS
	
	
//	@Bean
//	public OpenAPI openApi() {
//		
//		String schemeName = "bearerScheme";
//		
//	return new OpenAPI()
//			.addSecurityItem(new SecurityRequirement()
//			.addList(schemeName))
//			.components(new Components()
//			.addSecuritySchemes(schemeName, new SecurityScheme()
//			.name(schemeName)
//			.type(SecurityScheme.Type.HTTP)
//			.bearerFormat("JWT")
//			.scheme("bearer")))
//			
//			
//			.info(new Info()
//			.title("Blogging Application : Backend(JAVA)")
//			.description("This Project is Developed by Junior Software Engineer Darshan")
//			.version("1.0")
//			.contact(new io.swagger.v3.oas.models.info.Contact().name("Darshan").email("darshanpnayaka06@gmail.com").url("https://github.com/Darshannayaka?tab=repositories"))
//			.license(new License().name("Apache")))
//			.externalDocs(new ExternalDocumentation().url("https://github.com/Darshannayaka?tab=repositories").description("This is external URL"));
//	}
	
	
	
}
