package com.pet.monitor.service;

import com.pet.monitor.model.UserInfo;

/**
 * Created by luo on 2018/10/29
 */
public interface UserInfoService {

    UserInfo save(UserInfo userInfo);

    UserInfo findUserInfoById(Long id);

    UserInfo findUserInfoByPhone(String phone);

    UserInfo findUserInfoByOpenId(String openId);

    int updatePhoneByOpenId(String phone,String openId);



}
