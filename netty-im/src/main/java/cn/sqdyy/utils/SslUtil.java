package cn.sqdyy.utils;

import org.springframework.util.ResourceUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * @author <a href="blog.sqdyy.cn">神奇的鸭鸭</a>
 * @ClassName SslUtil
 * @Description SslUtil 配置 SSLContext
 * @Date: 2018/12/3 17:36
 * @Version 1.0.0
 */
public class SslUtil {
    public static SSLContext createSSLContext(String type , File file,String password) throws Exception {
        KeyStore ks = KeyStore.getInstance(type); /// "JKS"
        InputStream ksInputStream = new FileInputStream(file); /// 证书存放地址
        ks.load(ksInputStream, password.toCharArray());
        // KeyManagerFactory 充当基于密钥内容源的密钥管理器的工厂。
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()); // getDefaultAlgorithm: 获取默认的 KeyManagerFactory 算法名称。
        kmf.init(ks, password.toCharArray());
        // SSLContext 的实例表示安全套接字协议的实现，它充当用于安全套接字工厂或 SSLEngine 的工厂。
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, null);
        return sslContext;
    }
}
