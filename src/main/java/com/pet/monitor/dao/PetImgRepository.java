package com.pet.monitor.dao;

import com.pet.monitor.model.PetImg;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by luo on 2018/11/9
 */
public interface PetImgRepository extends JpaRepository<PetImg,Long> {

    PetImg findByCreateDate(String createDate);

}
