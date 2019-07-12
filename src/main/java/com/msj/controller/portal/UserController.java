package com.msj.controller.portal;

import com.msj.common.Const;
import com.msj.common.ResponseCode;
import com.msj.common.ServerResponse;
import com.msj.pojo.User;
import com.msj.service.portal.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //1、登录
    @RequestMapping("/login.do")
    @ResponseBody
    public ServerResponse login(@Param("username") String username,
                                @Param("password") String password, HttpSession session) {
        User user = userService.getLogin(username,password);
        //如果取得出来user，证明已经被注册过了的，登录成功，否则登录失败
        if (user!= null) {
            session.setAttribute("user",user);
            return ServerResponse.createSuccess(user);
        }
        return ServerResponse.createErrorByCodeMessage(ResponseCode.LOGIN_ERROR.getCode(),ResponseCode.LOGIN_ERROR.getDesc());
    }

    //2、注册
    @RequestMapping("/register.do")
    @ResponseBody
    public ServerResponse register(User user){
        User userList = userService.getRegister(user);
        //如果取得出来user，证明已经注册过了，注册失败，否则注册成功
        if(userList!=null){
            return ServerResponse.createErrorByMessage(Const.REGISTER_ERROR_MESSAGE);
        }
        return  ServerResponse.createSuccessByMessage(Const.REGISTER_SUCCESS_MESSAGE);
    }

    //3、检查用户名是否有效
    @RequestMapping("/check_valid.do")
    @ResponseBody
    public ServerResponse checkValid(@Param("str") String str,@Param("type") String type){
        User user = userService.getCheck(str,type);
        //如果取得出来user，证明已经注册过了
        if(user!=null){
            return ServerResponse.createErrorByMessage(Const.REGISTER_ERROR_MESSAGE);
        }
        return ServerResponse.createSuccessByMessage(Const.REGISTER_SUCCESS_MESSAGE);
    }

    //4、获取用户信息
    //登录成功才可获取用户信息，未登录不可获取用户信息
    @RequestMapping("/get_user_info.do")
    @ResponseBody
    public ServerResponse getUserInfo(HttpSession session){
        if(session.getAttribute("user")!=null){
            return ServerResponse.createSuccess(session.getAttribute("user"));
        }
        return ServerResponse.createErrorByMessage(Const.GETINFORMATION_ERROR_MESSAGE);
    }

    //5、忘记密码
    @RequestMapping("/forget_get_question.do")
    @ResponseBody
    public ServerResponse forgetGetQuestion(String username){
        User question = userService.getQuestion(username);
        if(question!=null){
            return ServerResponse.createSuccess(Const.FORGETQUESTION_SUCCESS);
        }
        return ServerResponse.createErrorByMessage(Const.FORGETQUESTION_ERROR);
    }

    //6、提交问题答案
    @RequestMapping("/forget_check_answer.do")
    @ResponseBody
    public ServerResponse forgetCheckAnswer(@Param("username")String username,@Param("question")String question,
                                            @Param("answer")String answer,HttpSession session){
        User user = userService.getAnswer(username, question);
        if(user!=null){
            if((user.getAnswer()).equals(answer)){
                ServerResponse serverResponse = ServerResponse.createSuccessByMessage(Const.CHECKANSWER_SUCCESS);
                String msg = ServerResponse.createSuccessByMessage(Const.CHECKANSWER_SUCCESS).getMsg();
                session.setAttribute("forgetToken",msg);
                return serverResponse;
            }
        }
        return ServerResponse.createErrorByMessage(Const.CHECKEANSWER_ERROR);
    }

    //7、忘记密码重设密码
    @RequestMapping("/forget_reset_password.do")
    @ResponseBody
    public ServerResponse forgetResetPassword(String username,String passwordNew,String forgetToken,HttpSession session){
        System.out.println(session.getAttribute("forgetToken"));
        if((session.getAttribute("forgetToken")).equals(forgetToken)){
            Integer num = userService.getUpdatePassword(username, passwordNew);
            if(num>0){
                return ServerResponse.createSuccessByMessage(Const.UPDATE_PASSWORD_SUCCESS);
            }
        }
        return ServerResponse.createErrorByMessage(Const.UPDATE_PASSWORD_ERROR);
    }

    //8、登录状态：重置密码
    @RequestMapping("/reset_password.do")
    @ResponseBody
    public ServerResponse loginResetPassword(String passwordOld,String passwordNew,HttpSession session){
        User user = (User)session.getAttribute("user");
        String password = user.getPassword();
        String username = user.getUsername();
        if(passwordOld.equals(password)){
            Integer num = userService.getUpdatePassword(username, passwordNew);
            if(num>0){
                return ServerResponse.createSuccessByMessage(Const.UPDATE_PASSWORD_SUCCESS);
            }
        }
        return ServerResponse.createErrorByMessage(Const.UPDATE_PASSWORD_ERROR2);
    }

    //9、登录状态更新个人信息
    @RequestMapping("/update_information.do")
    @ResponseBody
    public ServerResponse updateInformation(HttpSession session){
        //如果session取出来不为空，就进行判断，为空说明未登录
        User userList = (User)session.getAttribute("user");
        if(userList!=null){
            userList.setUpdateTime(new Date());
            Integer num = userService.getUpdateInformation(userList);
            if(num>0){
                return ServerResponse.createSuccessByMessage(Const.UPDATE_INFORMATION_SUCCESS);
            }
        }
        return ServerResponse.createErrorByMessage(Const.UPDATE_INFORMATION_ERROR);
    }


    //10、退出登录，设置session值为空
    @RequestMapping("/logout.do")
    @ResponseBody
    public ServerResponse logout(HttpSession session){
        if(session.getAttribute("user")!=null){
            session.setAttribute("user",null);
            return ServerResponse.createSuccessByMessage(Const.LOGOUT_SUCCESS);
        }
        return ServerResponse.createErrorByMessage(Const.LOGOUT_ERROR);
    }

}
