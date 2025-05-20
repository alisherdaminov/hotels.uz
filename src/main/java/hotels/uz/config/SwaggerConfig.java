package hotels.uz.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // general info
        Info info = new Info()
                .title("Hotels.uz")
                .version("1.0.0")
                .description("Hotel.uz is for all users and hotel owners to register and log in, daily usage, search hotels, order rooms, etc.")
                .contact(new Contact()
                        .name("Alisher")
                        .email("alisherdaminov135@gmail.com")
                        .url("https://github.com/Alisher19121994/hotels.uz")
                )
                .license(new License()
                        .name("hotels.uz")

                );


        // security type (bizning holatda JWT)
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("bearerAuth");

        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setName("bearerAuth");
        securityScheme.setType(SecurityScheme.Type.HTTP);
        securityScheme.bearerFormat("JWT");
        securityScheme.setIn(SecurityScheme.In.HEADER);
        securityScheme.setScheme("bearer");

        Components components = new Components();
        components.addSecuritySchemes("bearerAuth", securityScheme);

        // collect all together
        OpenAPI openAPI = new OpenAPI();
        openAPI.setInfo(info);
        openAPI.setSecurity(List.of(securityRequirement));
        openAPI.components(components);

        // return-xe
        return openAPI;
    }
}
