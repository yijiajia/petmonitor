package com.pet.monitor.service.impl;

import com.pet.monitor.dao.PetImgRepository;
import com.pet.monitor.model.PetImg;
import com.pet.monitor.service.PetImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luo on 2018/11/9
 */
@Service
public class PetImgServiceImpl implements PetImgService {

    @Autowired
    private PetImgRepository petImgRepository;

    @Override
    public List<PetImg> getAllImg() {
        return petImgRepository.findAll();
    }

    @Override
    public PetImg getImgByCreateDate(String createDate) {
        return petImgRepository.findByCreateDate(createDate);
    }

    @Override
    public PetImg save(PetImg petImg) {

        if(petImg!=null){
            return petImgRepository.save(petImg);
        }
        return null;
    }
}
