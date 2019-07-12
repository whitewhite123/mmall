package com.msj.controller.backend;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msj.common.Const;
import com.msj.common.ManageConst;
import com.msj.common.ServerResponse;
import com.msj.pojo.User;
import com.msj.service.portal.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    public ServerResponse login(@Param("username")String username, @Param("password")String password,
            HttpSession session){
        User user = userService.getLogin(username, password);
        if(user!=null){
            session.setAttribute("user",user);
            return ServerResponse.createSuccess(user);
        }
        return ServerResponse.createErrorByMessage(ManageConst.LOGIN_ERROR);
    }

    /**
     * 查看用户列表
     * @param pageNum
     * @param pageSize
     * @param session
     * @return
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "pageNum",defaultValue = "10")Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "1")Integer pageSize,
                               HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null){
            PageHelper.startPage(pageNum,pageNum);
            List<User> userList = userService.getUserList();
            PageInfo<User> pageInfo = new PageInfo<User>(userList);
            return ServerResponse.createSuccess(pageInfo);
        }
        return ServerResponse.createErrorByMessage(ManageConst.GETLIST_ERROR);
    }
}
