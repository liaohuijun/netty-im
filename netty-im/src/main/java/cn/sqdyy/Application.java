package cn.sqdyy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author <a href="blog.sqdyy.cn">神奇的鸭鸭</a>
 * @ClassName Application
 * @Description TODO
 * @Date: 2018/11/30 10:39
 * @Version 1.0.0
 */
@SpringBootApplication
@MapperScan(basePackages="cn.sqdyy.mapper")
@ComponentScan(basePackages= {"cn.sqdyy","org.n3r.idworker"})
@EnableSwagger2
@ServletComponentScan
public class Application {

    @Bean
    public SpringUtil getSpingUtil() {
        return new SpringUtil();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
