package com.pet.monitor.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * Created by luo on 2018/11/9
 */
public class ClientRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String HOST = "127.0.0.1";
    private final int PORT = 5612;

    private Socket socket = null;

    public ClientRunnable(){
        try {
            socket = new Socket(HOST,PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        this.logger.info("socket 客户端开启。。。");
        //获取图片输入流
        File  file = new File("D:\\Code\\static_resources\\smart-home\\miniapp\\basics\\device\\button_open@3x.png");
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream out = socket.getOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while((len=fis.read(bytes)) !=-1){
                out.write(bytes,0,len);
            }
            //通知服务端，数据发送完毕
            socket.shutdownInput();

            fis.close();
            out.close();
            socket.close();
            this.logger.info("socket 客户端连接结束");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
