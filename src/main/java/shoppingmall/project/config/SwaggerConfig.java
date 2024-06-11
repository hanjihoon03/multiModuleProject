package shoppingmall.project.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Project API 명세",
        description = "SIMPLE API",
                version = "v1"
        ))
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        // "/v1/**" 경로에 매칭되는 API를 그룹화하여 문서화한다.
        String[] paths = {"/v1/**"};

        return GroupedOpenApi.builder()
                .group("API v1")  // 그룹 이름을 설정한다.
                .pathsToMatch(paths) // 그룹에 속하는 경로 패턴을 지정한다.
                .build();
    }
}
