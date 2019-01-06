package com.pet.monitor.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luo on 2018/10/27
 */
@Data
public class PetInOutVO {

    private List<String> dataIn = new ArrayList<>();

    private List<String> dataOut = new ArrayList<>();

}
