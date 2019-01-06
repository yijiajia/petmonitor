package com.pet.monitor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by luo on 2018/10/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class User implements Serializable {

    @Id
    @Column(name = "id",unique = true,nullable = false)
    private String id;

    private String email;

    @JsonIgnore
    private String passwd;

    @Column(columnDefinition = "tinyint(1) DEFAULT NULL")
    @JsonIgnore
    private Integer admin;

    private String name;

    private String image;

    private String phone;

    private String openId;

    @Column(name = "created_at")
    private Float created_time;

    private String rfid_id;

    private String petina;
    private String petinb;
    private String petinc;
    private String petind;
    private String petine;
    private String petinf;
    private String peting;
    private String petouta;
    private String petoutb;
    private String petoutc;
    private String petoutd;
    private String petoute;
    private String petoutf;
    private String petoutg;

    private String img;


}
