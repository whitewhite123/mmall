package com.msj.controller.backend;

import com.msj.common.ManageConst;
import com.msj.common.ServerResponse;
import com.msj.pojo.User;
import com.msj.service.portal.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manage/user")
public class UserManageController {
    @Autowired
    private UserService userService;


    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login.do")
    @ResponseBody
    public ServerResponse login(@Param("username")String username, @Param("password")String password){
        User user = userService.getLogin(username, password);
        if(user!=null){
            return ServerResponse.createSuccess(user);
        }
        return ServerResponse.createErrorByMessage(ManageConst.LOGIN_ERROR);
    }
}
