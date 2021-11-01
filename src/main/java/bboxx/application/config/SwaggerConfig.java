package bboxx.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.OAS_30)
//                .useDefaultResponseMessages(true)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.example.springswagger.controller"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(apiInfo());
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("BBOXX API")
//                .description("BBOXX API Documentation")
//                .version("1.0.0")
//                .build();
//    }
//}

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.swaggerInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("bboxx.application.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(true); //기본으로 세팅되는 200,400,403,404 메시지 표시
    }

    private ApiInfo swaggerInfo(){
        return new ApiInfoBuilder()
                .title("BBOXX API")
                .description("BBOXX API Documentation<br/>" +
                        "<br/>" +
                        "<b>ERROR CODE</b><br/>" +
                        "code : BX001, message : internal server error<br/>" +
                        "code : BD001, message : internal server error<br/>" +
                        "code : BD002, message : unauthorized error<br/>" +
                        "code : BD003, message : social user fetch error<br/>" +
                        "code : BD004, message : member existed<br/>" +
                        "code : BD005, message : not found member<br/>" +
                        "code : BD006, message : not found push token<br/>" +
                        "code : BD007, message : not found decibel<br/>" +
                        "code : BD008, message : not fond emotion diary<br/>" +
                        "code : BD009, message : not fond emotion<br/>" +
                        "")
                .version("1.0.0")
                .build();
    }
}