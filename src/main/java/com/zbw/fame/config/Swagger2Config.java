package com.zbw.fame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2 配置类
 *
 * @author zbw
 * @create 2018/1/2 11:41
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zbw.fame.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalResponseMessage(RequestMethod.GET, initResponseMessage())
                .globalResponseMessage(RequestMethod.POST, initResponseMessage())
                .globalResponseMessage(RequestMethod.PUT, initResponseMessage())
                .globalResponseMessage(RequestMethod.DELETE, initResponseMessage());
    }

    @Bean
    public UiConfiguration uiConfig() {
        return new UiConfiguration(
                "validatorUrl",
                "none",
                "alpha",
                "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
                false,
                true,
                60000L);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Fame博客Api文档")
                .description("基于Spring Boot的Restful博客后端Api文档")
                .contact(new Contact("zbw", "http://zzzzbw.cn/", "zzzzbw@gmail.com"))
                .version("1.0")
                .build();
    }

    private List<ResponseMessage> initResponseMessage() {
        return new ArrayList<ResponseMessage>() {{
            add(new ResponseMessageBuilder().code(999).message("Not Login").build());
            add(new ResponseMessageBuilder().code(1000).message("RuntimeException").build());
            add(new ResponseMessageBuilder().code(1001).message("NullPointerException").build());
            add(new ResponseMessageBuilder().code(1002).message("ClassCastException").build());
            add(new ResponseMessageBuilder().code(1003).message("IOException").build());
            add(new ResponseMessageBuilder().code(1004).message("NoSuchMethodException").build());
            add(new ResponseMessageBuilder().code(1005).message("IndexOutOfBoundsException").build());
            add(new ResponseMessageBuilder().code(400).message("Bad Request").build());
            add(new ResponseMessageBuilder().code(404).message("Not Found").build());
            add(new ResponseMessageBuilder().code(405).message("Method Not Allowed").build());
            add(new ResponseMessageBuilder().code(406).message("Not Acceptable").build());
            add(new ResponseMessageBuilder().code(500).message("Internal Server Error").build());
        }};
    }
}
