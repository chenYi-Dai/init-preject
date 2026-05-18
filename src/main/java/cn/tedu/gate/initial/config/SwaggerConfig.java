package cn.tedu.gate.initial.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("通义千问AI接口文档")
                        .version("1.0.0")
                        .description("Spring Boot 3.3.4 集成通义千问大模型的API接口文档")
                        .contact(new Contact()
                                .name("开发者")
                                .email("developer@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}

//
///**
// * @Author wy
// * @Date 2018/8/14
// * @Description:
// */
//@Configuration
//@E
//public class SwaggerConfig {
//    @Bean
//    public Docket buildDocket() {
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .pathMapping("/")
//                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(apiInfo());
//    }
//
//    ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("系统")
//                .description("系统接口文档")
//                .version("2.0.0")
//                .build();
//    }
//
//}
