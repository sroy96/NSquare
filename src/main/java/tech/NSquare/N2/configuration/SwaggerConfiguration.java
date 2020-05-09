package tech.NSquare.N2.configuration;


import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(postPaths())
                .build();
    }

    private Predicate<String> postPaths() {
        return regex("/api/.*");
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Nsquare API")
                .description("Nsquare API reference for developers")
                .termsOfServiceUrl("http://nsquare.tech")
                .contact("code.o.meter@gmail.com").license("Nsquare License")
                .licenseUrl("code.o.meter@gmail.com").version("1.0").build();
    }
}