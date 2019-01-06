package com.pet.monitor.dao;

import com.pet.monitor.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by luo on 2018/10/29
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {

    UserInfo findByPhone(String phone);

    @Modifying
    @Transactional
    @Query("update UserInfo uInfo set uInfo.phone=?1 where uInfo.openId=?2")
    int updatePhoneByOpenId(String phone,String openId);

    UserInfo findByOpenId(String openId);
}
