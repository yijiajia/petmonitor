package com.pet.monitor.service;

import com.pet.monitor.model.PetImg;

import java.util.List;

/**
 * Created by luo on 2018/11/9
 */
public interface PetImgService {

    List<PetImg> getAllImg();

    PetImg getImgByCreateDate(String createDate);

    PetImg save(PetImg petImg);
}
