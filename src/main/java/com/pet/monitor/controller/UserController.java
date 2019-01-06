package com.pet.monitor.controller;

import com.pet.monitor.model.PetImg;
import com.pet.monitor.model.User;
import com.pet.monitor.service.PetImgService;
import com.pet.monitor.service.UserService;
import com.pet.monitor.vo.PetImgVO;
import com.pet.monitor.vo.PetInOutVO;
import com.pet.monitor.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by luo on 2018/10/27
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private PetImgService petImgService;

    @RequestMapping(value = "/getInfo",method = RequestMethod.GET)
    @ResponseBody
    public User getUserInfo(String id){
        User user = userService.findUserById(id);
        if(user!=null){
            return user;
        }
        return null;
    }

    @RequestMapping(value = "/getChart",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public ResultVO<PetInOutVO> getChartData(@RequestParam(name = "openid",required = true) String openId){

        PetInOutVO petInOutVO = new PetInOutVO();

        User user = userService.findUserByOpenId(openId);
        if (user != null) {

            petInOutVO.setDataIn(Arrays.asList(user.getPetina(), user.getPetinb(), user.getPetinc(),
                    user.getPetind(), user.getPetine(), user.getPetinf(), user.getPeting()));

            petInOutVO.setDataOut(Arrays.asList(user.getPetouta(), user.getPetoutb(), user.getPetoutc(),
                    user.getPetoutd(), user.getPetoute(), user.getPetoutf(), user.getPetoutg()));

            this.logger.info("成功返回用户{}的宠物进出数据",user.getName());
            return ResultVO.ResultBuilder.getOK(petInOutVO);
        } else {
            this.logger.error("用户未成功绑定平台，请检查手机号");
            return ResultVO.ResultBuilder.getError(110, "该用户未绑定平台");
        }

    }

    @RequestMapping(value = "/img")
    @ResponseBody
    public ResultVO<List<PetInOutVO>> getAllImg() {

        List<PetImgVO> list = new ArrayList<>();
        List<PetImg> petImgs = petImgService.getAllImg();
        for(PetImg petImg:petImgs){
            PetImgVO petImgVO = new PetImgVO(petImg.getName(),petImg.getCreateDate());
            list.add(petImgVO);
        }

      return ResultVO.ResultBuilder.getOK(list);
    }

}
