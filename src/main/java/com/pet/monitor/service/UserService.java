package com.pet.monitor.service;

import com.pet.monitor.model.User;

/**
 * Created by luo on 2018/10/27
 */
public interface UserService {

    User findUserById(String id);

    User findUserByPhone(String phone);

    User findUserByOpenId(String openId);

    int updateOpenIdByPhone(String openId,String phone);


}
