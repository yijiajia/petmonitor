package com.pet.monitor.listener;

import com.pet.monitor.model.PetImg;
import com.pet.monitor.service.PetImgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by luo on 2018/11/9
 */
public class ServerRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String HOST = "192.168.188.103";
    private final int PORT = 8003;


    private ServerSocket serverSocket = null;

    private PetImgService petImgService;

    public ServerRunnable(PetImgService petImgService) {
        try {
            serverSocket = new ServerSocket(PORT);
            this.petImgService = petImgService;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        this.logger.info("socket服务端通信开始");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {

            while (true) {
                Socket socket = serverSocket.accept();
                this.logger.info("检测到客户端，准备数据接收");
                //格式化
                Date date = new Date();
                String createDate = sdf.format(date);
                String createTime = ""+date.getTime();

                InputStream is = socket.getInputStream();
                byte[] buf = new byte[1024];
                int len = 0;
                String name = createTime+".png";
                String imgFilePath = "D:\\Code\\static_resources\\petmonitor\\"+name;
                File file = new File(imgFilePath);
                if(!file.exists()){
                    file.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(file);
                logger.info("开始接收图片数据");
                while((len=is.read(buf))!=-1){
                    System.out.print(buf+" ");
                    fos.write(buf,0,len);
                }
                System.out.println();
                fos.flush();
                logger.info("图片传输结束");
                //需插入数据库
                PetImg petImg  = petImgService.save(new PetImg(createTime,imgFilePath,createDate,name));
                if(petImg!=null){
                    logger.info("图片保存成功");
                }else{
                    logger.info("图片保存失败");
                }
//                StringBuffer sb = new StringBuffer();
//                logger.info("开始接收图片数据");
//                int readLen;
//                byte[] bytes = new byte[1024];
//                readLen = is.read(bytes);
//                if (readLen == -1) {
//                    throw new RuntimeException("socket已关闭");
//                }
//                String s = new String(bytes, 0, readLen);
//                byte[] bRes = generateImgForString(s);
//                String sRes = new String(bRes, 0, bRes.length);
//                sb.append(sRes);
//                logger.info("拼接中。。。{}", sb.toString());
//
//                logger.info("图片传输结束，结果为:{}", sb);
//                Boolean b = generateImg(sb);
//                if (b) {
//                    System.out.println("图片保存成功");
//                } else {
//                    System.out.println("图片保存失败");
//                }
            }

        } catch (IOException e) {
            this.logger.info("socket 服务端开启失败");
            e.printStackTrace();
        }

    }

    /**
     * 将字符串保存为图片
     *
     * @param sb
     */
    private Boolean generateImg(StringBuffer sb) {
        this.logger.info("开始解密图片");

        if (sb == null || sb.length() <= 0) {
            return false;
        }

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(sb.toString());
            for (int i = 0; i < b.length; i++) {
                //调整非法数据
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }

            String imgFilePath = "D:\\Code\\static_resources\\petmonitor\\test.jpg";
            OutputStream os = new FileOutputStream(imgFilePath);
            os.write(b);
            os.flush();
            os.close();

        } catch (IOException e) {
            this.logger.info("解密字符串失败");
//            return false;
            e.printStackTrace();
        }


        return true;
    }

    private byte[] generateImgForString(String sb) {
        this.logger.info("开始解密图片");

        if (sb == null || sb.length() <= 0) {
            return null;
        }

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(sb);
            for (int i = 0; i < b.length; i++) {
                //调整非法数据
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            logger.info("解密字符串成功");
            return b;

        } catch (IOException e) {
            this.logger.info("解密字符串失败");
            e.printStackTrace();
        }
        return null;

    }
}
