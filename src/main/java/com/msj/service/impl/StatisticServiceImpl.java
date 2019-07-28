package com.msj.service.impl;

import com.msj.common.ManageConst;
import com.msj.common.ResponseCode;
import com.msj.common.ServerResponse;
import com.msj.mapper.StatisticMapper;
import com.msj.pojo.User;
import com.msj.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticServiceImpl implements StatisticService{
    @Autowired
    private StatisticMapper statisticMapper;

    public ServerResponse baseCount(HttpSession session) {
        //1、判断是否登录
        User user = (User) session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.GETINFORMATION_ERROR.getDesc());
        }
        //2、查询userCount,productCount,orderCount,然后放在一个map对象中
        Integer userCount = statisticMapper.selectUserCount();
        Integer productCount = statisticMapper.selectProductCount();
        Integer orderCount = statisticMapper.selectOrderCount();

        Map<String, Integer> statisticMap = new HashMap<String, Integer>();
        statisticMap.put("userCount",userCount);
        statisticMap.put("productCount",productCount);
        statisticMap.put("orderCount",orderCount);
        if(statisticMap == null){
            return ServerResponse.createByErrorMessage(ManageConst.STATISTIC_DATA_ERROR);//统计出错
        }
        return ServerResponse.createSuccess(statisticMap);
    }
}
