package com.pet.monitor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by luo on 2018/11/9
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "pet_img")
public class PetImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,nullable = false)
    private Long id;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "pic_url")
    private String picUrl;

    @Column(name = "create_date")
    private String createDate;

    private String name;


    public PetImg(String createTime, String picUrl, String createDate,String name) {
        this.createTime = createTime;
        this.picUrl = picUrl;
        this.createDate = createDate;
        this.name = name;
    }
}
