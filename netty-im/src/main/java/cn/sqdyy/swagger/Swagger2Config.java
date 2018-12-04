package cn.sqdyy.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
/**
 * @author <a href="blog.sqdyy.cn">神奇的鸭鸭</a>
 * @ClassName Swagger2Config
 * @Description api 文档配置
 * @Date: 2018/11/30 16:55
 * @Version 1.0.0
 */
@Configuration
public class Swagger2Config {
    @Bean
    public Docket controllerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("深家协 im 文档")
                        .description("深家协 im 文档")
                        .contact(new Contact("神奇的鸭鸭", null, null))
                        .version("1.0.0")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.sqdyy"))
                .paths(PathSelectors.any())
                .build();
    }
}
