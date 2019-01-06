package com.pet.monitor.service.impl;

import com.pet.monitor.dao.UserInfoRepository;
import com.pet.monitor.model.UserInfo;
import com.pet.monitor.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luo on 2018/10/29
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo save(UserInfo userInfo) {
        if(userInfo!=null){
           return userInfoRepository.save(userInfo);
        }
        return null;
    }


    @Override
    public UserInfo findUserInfoByPhone(String phone) {
        if(StringUtils.isNotEmpty(phone)){
            return userInfoRepository.findByPhone(phone);
        }
        return null;

    }

    @Override
    public UserInfo findUserInfoByOpenId(String openId) {
        if(openId!=null){
            return userInfoRepository.findByOpenId(openId);
        }
        return null;
    }

    @Override
    public int updatePhoneByOpenId(String phone, String openId) {

        if(StringUtils.isAnyBlank(phone,openId)){
            return 0;
        }
        return userInfoRepository.updatePhoneByOpenId(phone,openId);
    }

    @Override
    public UserInfo findUserInfoById(Long id) {
        if(id!=null){
            return userInfoRepository.getOne(id);
        }
        return null;
    }
}
