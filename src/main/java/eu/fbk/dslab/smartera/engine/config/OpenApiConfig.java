package eu.fbk.dslab.smartera.engine.config;

import org.springframework.context.annotation.Configuration;

import eu.fbk.security.UserContext;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@SecurityScheme(type = SecuritySchemeType.APIKEY, name = UserContext.USER_ID, in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(info = @Info(title = "Smartera Engine API", version = "1.0.0"), security = { @SecurityRequirement(name = UserContext.USER_ID) })
public class OpenApiConfig {

}