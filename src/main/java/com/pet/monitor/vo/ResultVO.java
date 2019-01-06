package com.pet.monitor.vo;

import lombok.Data;

/**
 * 标准返回数据格式
 * Created by luo on 2018/10/30
 */
@Data
public class ResultVO<T> {

    private int code;

    private String message;

    private T data;

    public static class ResultBuilder{

        public static ResultVO getOK(Object object){
            ResultVO result = new ResultVO();
            result.setCode(0);
            result.setMessage("成功");
            result.setData(object);
            return result;
        }

        public static ResultVO getOK(){
            return getOK(null);
        }

        public static ResultVO getError(Integer code,String msg){
            ResultVO result = new ResultVO();
            result.setCode(code);
            result.setMessage(msg);
            return result;
        }

        public static ResultVO getError(String msg){
            ResultVO result = new ResultVO();
            result.setMessage(msg);
            result.setCode(405);
            return result;
        }


    }


}
