package cn.sqdyy.pojo;

/**
 * @author <a href="blog.sqdyy.cn">神奇的鸭鸭</a>
 * @ClassName SSL
 * @Description TODO
 * @Date: 2018/12/4 11:05
 * @Version 1.0.0
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cn.sqdyy.ssh")
@Data
public class SSH {
    private String host;
    private String mysql;
    private String user;
    private String password;
    private int port;
}
