//package cn.tedu.gate.initial.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.bind.annotation.RestController;
//
//
//
///**
// * @Author wy
// * @Date 2018/8/14
// */
//@Configuration
//@EnableSwagger2
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
//                .title("CD系统")
//                .description("CD系统接口文档")
//                .version("2.0.0")
//                .build();
//    }
//
//}
