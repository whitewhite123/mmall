package com.msj.controller.portal;


import com.msj.common.ServerResponse;
import com.msj.pojo.User;
import com.msj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//用户
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //1、登录
    @RequestMapping("/login.do")
    @ResponseBody
    public ServerResponse login(String username,String password, HttpSession session) {
        return userService.login(username,password,session);

    }

    //2、注册
    @RequestMapping("/register.do")
    @ResponseBody
    public ServerResponse register(User user){
        return userService.Register(user);

    }

    //3、检查用户名是否有效
    @RequestMapping("/check_valid.do")
    @ResponseBody
    public ServerResponse checkValid(String str, String type){
        return userService.checkValid(str,type);
    }

    //4、获取用户信息
    //登录成功才可获取用户信息，未登录不可获取用户信息
    @RequestMapping("/get_user_info.do")
    @ResponseBody
    public ServerResponse getUserInfo(HttpSession session){
        return userService.getUserInfo(session);
    }

    //5、忘记密码--获取问题
    @RequestMapping("/forget_get_question.do")
    @ResponseBody
    public ServerResponse forgetGetQuestion(String username){
        return userService.getQuestion(username);

    }

    //6、提交问题答案
    @RequestMapping("/forget_check_answer.do")
    @ResponseBody
    public ServerResponse forgetCheckAnswer(User user, HttpSession session){
        return userService.checkAnswer(user,session);

    }

    //7、忘记密码：重设密码
    @RequestMapping("/forget_reset_password.do")
    @ResponseBody
    public ServerResponse forgetResetPassword(String username,String passwordNew,String forgetToken,HttpSession session){
        return userService.forgetResetPassword(username,passwordNew,forgetToken,session);

    }

    //8、登录状态：重置密码
    @RequestMapping("/reset_password.do")
    @ResponseBody
    public ServerResponse ResetPassword(String passwordOld,String passwordNew,HttpSession session){
        return userService.resetPassword(passwordOld,passwordNew,session);

    }

    //9、登录状态更新个人信息
    @RequestMapping("/update_information.do")
    @ResponseBody
    public ServerResponse updateInformation(User user,HttpSession session){
        return userService.updateInformation(user,session);

    }

    //10、获得当前用户的详细信息，并强制登录
    @RequestMapping("/get_information.do")
    @ResponseBody
    public ServerResponse getInformation(HttpSession session, HttpServletResponse response){
        return userService.getInformation(session);

    }

    //11、退出登录，设置session值为空
    @RequestMapping("/logout.do")
    @ResponseBody
    public ServerResponse logout(HttpSession session) {
        return userService.logout(session);
    }

}
