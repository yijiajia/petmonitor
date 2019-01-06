package com.pet.monitor.listener;

import com.pet.monitor.service.PetImgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by luo on 2018/11/9
 */
@WebListener
@Component
public class ReceiveImgListener implements ServletContextListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PetImgService petImgService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        this.logger.info("自定义监听器启动。。。");

        petImgService = WebApplicationContextUtils.
                    getWebApplicationContext(servletContextEvent.getServletContext()).getBean(PetImgService.class);
        ExecutorService executorService = Executors.newCachedThreadPool();
        //开启socket Server端
        startServerSocket(executorService,petImgService);
//        startClientSocket(executorService);
    }

    private void startClientSocket(ExecutorService executorService) {
        ClientRunnable clientRunnable = new ClientRunnable();
        executorService.submit(clientRunnable);
        logger.info("客户端socket线程启动。。。");
    }

    private void startServerSocket(ExecutorService executorService,PetImgService petImgService) {

        ServerRunnable serverRunnable = new ServerRunnable(petImgService);
        executorService.submit(serverRunnable);
        logger.info("服务端socket线程启动。。。");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        this.logger.info("自定义监听器销毁。。。");
        System.out.println("自定义监听器销毁。。。");
    }
}
