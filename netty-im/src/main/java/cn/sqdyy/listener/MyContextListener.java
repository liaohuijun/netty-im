package cn.sqdyy.listener;

import cn.sqdyy.pojo.SSH;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author <a href="blog.sqdyy.cn">神奇的鸭鸭</a>
 * @ClassName MyContextListener
 * @Description TODO
 * @Date: 2018/12/4 11:16
 * @Version 1.0.0
 */
@WebListener
@Slf4j
public class MyContextListener implements ServletContextListener {

    @Autowired
    private SSH ssh;

    private Session session;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if(ssh==null) {
            return;
        }
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(ssh.getUser(), ssh.getHost(), ssh.getPort());
            session.setConfig("PreferredAuthentications","password");
            session.setPassword(ssh.getPassword());
            session.setConfig("StrictHostKeyChecking","no");
            session.connect(3000);   // making a connection with timeout.
            session.setPortForwardingL(3306, ssh.getMysql(), 3306);
            if(session.isConnected()){
                log.info("ssh session connect success");
            }else{
                log.info("ssh session connect fail");
            }
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        session.disconnect();
    }
}