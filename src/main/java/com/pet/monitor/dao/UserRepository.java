package com.pet.monitor.dao;

import com.pet.monitor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by luo on 2018/10/27
 */
public interface UserRepository extends JpaRepository<User,String> {

    User findByPhone(String phone);

    User findByOpenId(String openId);

    @Modifying
    @Transactional
    @Query("update User u set u.openId=?1 where u.phone=?2")
    int updateOpenIdByPhone(String openId,String phone);

    @Modifying
    @Transactional
    @Query("update User u set u.openId=?1,u.name=?2 where u.phone=?3")
    int updateOpenIdAndNameByPhone(String openId,String name,String phone);


}
