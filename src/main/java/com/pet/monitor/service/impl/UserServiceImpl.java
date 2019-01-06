package com.pet.monitor.service.impl;

import com.pet.monitor.dao.UserRepository;
import com.pet.monitor.model.User;
import com.pet.monitor.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luo on 2018/10/27
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User findUserById(String id) {

        if(StringUtils.isNotEmpty(id)){
            User user = userRepository.getOne(id);
            return user;
        }

        return null;

    }

    @Override
    public User findUserByPhone(String phone) {

        if(StringUtils.isNotEmpty(phone)){
          return userRepository.findByPhone(phone);
        }

        return null;
    }

    @Override
    public User findUserByOpenId(String openId) {
        if(StringUtils.isNotEmpty(openId)){
            return userRepository.findByOpenId(openId);
        }
        return null;
    }

    @Override
    public int updateOpenIdByPhone(String openId, String phone) {

        if(StringUtils.isAnyBlank(openId,phone)){
            return 0;
        }

        return userRepository.updateOpenIdByPhone(openId,phone);
    }
}
