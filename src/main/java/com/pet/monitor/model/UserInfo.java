package com.pet.monitor.model;

import lombok.Data;

import javax.persistence.*;

/**
 * 用户的微信信息
 * Created by luo on 2018/10/29
 */
@Data
@Entity
@Table(name = "user_info" )
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,nullable = false)
    private Long id;

    private String nickName;

    private String province;

    private String gender;

    private String country;

    private String city;

    private String avatarUrl;

    private String openId;

    private String unionId;

    private String phone;
}
