package com.hanyang.dutychain;

import com.alipay.api.internal.util.StringUtils;
import com.hanyang.pojo.BusinessLaunch;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SexHandler extends AbstractBusinessHandler {
    @Override
    public List<BusinessLaunch> processHandler(List<BusinessLaunch> launchList, String targetCity, String targetSex, String targetProduct) {
        // 如果没有数据吗，直接返回
        if (launchList.isEmpty()) {
            return launchList;
        }
        // 按target进行筛选
        launchList = launchList.stream().filter(launch -> {
            String sex = launch.getTargetSex();
            if (StringUtils.isEmpty(sex)) {
                return true;
            }
            List<String> cityList = Arrays.asList(sex.split(","));
            return cityList.contains(targetSex);
        }).collect(Collectors.toList());
        // 如果还有下一个责任链，则继续进行筛选
        if (hasNextHandler()) {
            return nextHandler.processHandler(launchList, targetCity, targetSex, targetProduct);
        }
        return launchList;
    }
}
