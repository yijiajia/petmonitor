package com.pet.monitor.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.pet.monitor.config.WxMaConfiguration;
import com.pet.monitor.model.User;
import com.pet.monitor.model.UserInfo;
import com.pet.monitor.service.UserInfoService;
import com.pet.monitor.service.UserService;
import com.pet.monitor.utils.JsonUtil;
import com.pet.monitor.vo.ResultVO;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 微信小程序用户接口
 * Created by luo on 2018/10/29
 */
@RestController
@RequestMapping("/wx/user")
public class WxMaUserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/login")
    public String login(String code){
        this.logger.info("微信用户登录,code为：{}",code);

        if(StringUtils.isEmpty(code)){
            return "empty code";
        }
        try{
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            this.logger.info("session_key为{}",session.getSessionKey());
            this.logger.info("openid为{}",session.getOpenid());
            //关联自身用户

            return JsonUtil.toJson(session);

        }catch (WxErrorException e){
            this.logger.error(e.getMessage(), e);
            return e.toString();
        }

    }

    @RequestMapping(value = "/info")
    public ResultVO<String> info(String sessionKey,String signature,
                       String rawData, String encryptedData, String iv){
        this.logger.info("\n小程序获取用户信息的参数为sessionkey：{}，signature：{}," +
                "rawData:{},encryptedData:{},iv:{}",sessionKey,signature,rawData,encryptedData,iv);

        if (!wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return ResultVO.ResultBuilder.getError("user check failed");
        }
        //解密用户信息
        WxMaUserInfo wxMaUserInfo = wxMaService.getUserService().getUserInfo(sessionKey,encryptedData,iv);

        logger.info("解密后的用户信息为:{}",wxMaUserInfo);

        UserInfo userInfo  = new UserInfo();
        UserInfo uInfo  = userInfoService.findUserInfoByOpenId(wxMaUserInfo.getOpenId());
        if(uInfo==null){
            BeanUtils.copyProperties(wxMaUserInfo,userInfo);
            //保存用户信息
            userInfoService.save(userInfo);
            return ResultVO.ResultBuilder.getOK();
        }else{
            return ResultVO.ResultBuilder.getError("用户信息已保存成功。。");
        }



    }


    /**
     * 获取用户绑定手机号信息
     * @param sessionKey
     * @param signature
     * @param rawData
     * @param encryptedData
     * @param iv
     * @param openId
     * @return
     */
    @GetMapping("/phone")
    public ResultVO<String> phone(String sessionKey, String signature,
                          String rawData, String encryptedData, String iv,
                          @RequestParam(required = false) String openId) {

        this.logger.info("\n小程序获取用户手机的参数为\nsessionkey：{}，signature：{}," +
                "rawData:{},encryptedData:{},iv:{},openId:{}",sessionKey,signature,rawData,encryptedData,iv,openId);

        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return ResultVO.ResultBuilder.getError("user check failed");
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        this.logger.info("\n解密后的用户手机信息为{}",phoneNoInfo);

        String phone = phoneNoInfo.getPhoneNumber();

        //更新用户信息
        try{
            if(openId!=null){
                //需加上判断，避免不必要的更新
                userService.updateOpenIdByPhone(openId,phone);
                userInfoService.updatePhoneByOpenId(phone,openId);
                return ResultVO.ResultBuilder.getOK();
            }else{
                return ResultVO.ResultBuilder.getError("openId为空！");
            }
            //捕获更新异常
        }catch (Exception e){
            e.printStackTrace();
            return ResultVO.ResultBuilder.getError("用户绑定失败！");

        }


    }


}
