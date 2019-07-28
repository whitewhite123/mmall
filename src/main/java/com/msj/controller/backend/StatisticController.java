package com.msj.controller.backend;

import com.msj.common.ServerResponse;
import com.msj.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/statistic")
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    //统计用户、商品、订单数量
    @RequestMapping("/base_count.do")
    @ResponseBody
    public ServerResponse baseCount(HttpSession session){
        return statisticService.baseCount(session);
    }

}
