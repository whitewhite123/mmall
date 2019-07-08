package com.msj.controller.portal;

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
        User user = userService.selectByName(username, password);
        //如果取得出来user，证明已经被注册过了的，登录成功，否则登录失败
        if (user != null) {
            session.setAttribute("user",user);
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByFailMessage();
    }

    //2、注册
    @RequestMapping("/register.do")
    @ResponseBody
    public ServerResponse register(@Param("username")String username,@Param("password")String password,
                                   @Param("email")String email,@Param("phone")String phone,
                                   @Param("question")String question,@Param("answer")String answer){
        User user = userService.selectForRegister(username, password, email, phone, question, answer);
        //如果取得出来user，证明已经注册过了，注册失败，否则注册成功
        if(user!=null){
            return ServerResponse.registerByFail();
        }
        return  ServerResponse.registerBySuccess();
    }

    //3、检查用户名是否有效
    @RequestMapping("/check_valid.do")
    @ResponseBody
    public ServerResponse checkValid(@Param("str") String str,@Param("type") String type){
        User user = userService.selectForCheck(str,type);
        //如果取得出来user，证明已经注册过了
        if(user!=null){
            return ServerResponse.registerByFail();
        }
        return ServerResponse.registerBySuccess();
    }

    //4、获取用户信息
    //登录成功才可获取用户信息，未登录不可获取用户信息
    @RequestMapping("/get_user_info.do")
    @ResponseBody
    public ServerResponse getUserInfo(HttpSession session){
        if(session.getAttribute("user")!=null){
            return ServerResponse.loginBySuccess(session.getAttribute("user"));
        }
        return ServerResponse.loginByFail();
    }

    //5、忘记密码
    @RequestMapping("/forget_get_question.do")
    @ResponseBody
    public ServerResponse forgetGetQuestion(String username){
        User question = userService.getQuestion(username);
        if(question!=null){
            return ServerResponse.getQuestionSuccess();
        }
        return ServerResponse.getQuestionFail();
    }

    //6、提交问题答案
    @RequestMapping("/forget_check_answer.do")
    @ResponseBody
    public ServerResponse forgetCheckAnswer(@Param("username")String username,
                                            @Param("question")String question,
                                            @Param("answer")String answer,HttpSession session){
        User user = userService.checkAnswer(username, question);
        if(user!=null){
            if((user.getAnswer()).equals(answer)){
                ServerResponse serverResponse = ServerResponse.checkAnswerSuccess();
                String msg = ServerResponse.checkAnswerSuccess().getMsg();
                session.setAttribute("forgetToken",msg);
                return serverResponse;
            }
        }
        return ServerResponse.checkAnswerFail();
    }

    //7、忘记密码重设密码
    @RequestMapping("/forget_reset_password.do")
    @ResponseBody
    public ServerResponse forgetResetPassword(String username,String passwordNew,String forgetToken,HttpSession session){
        System.out.println(session.getAttribute("forgetToken"));
        if((session.getAttribute("forgetToken")).equals(forgetToken)){
            Integer num = userService.updatePassword(username, passwordNew);
            if(num>0){
                return ServerResponse.updatePasswordSuccess();
            }
        }
        return ServerResponse.updatePasswordFail();
    }

    //8、登录状态：重置密码
    @RequestMapping("/reset_password.do")
    @ResponseBody
    public ServerResponse loginResetPassword(String passwordOld,String passwordNew,HttpSession session){
        User user = (User)session.getAttribute("user");
        String password = user.getPassword();
        String username = user.getUsername();
        if(passwordOld.equals(password)){
            Integer num = userService.updatePassword(username, passwordNew);
            if(num>0){
                return ServerResponse.updatePasswordSuccess();
            }
        }
        return ServerResponse.updatePasswordFail2();
    }

    //9、登录状态更新个人信息
    @RequestMapping("/update_information.do")
    @ResponseBody
    public ServerResponse updateInformation(@Param("email") String email,@Param("phone") String phone,
                                            @Param("question")String question,@Param("answer")String answer,
                                            HttpSession session){
        //如果session取出来不为空，就进行判断，为空说明未登录
        User user = (User)session.getAttribute("user");
        if(user!=null){
            Integer id = user.getId();
            User user1 = new User();
            user1.setId(id);
            user1.setEmail(email);
            user1.setPhone(phone);
            user1.setQuestion(question);
            user1.setAnswer(answer);
            user1.setUpdateTime(new Date());
            Integer num = userService.updateInformation(user1);
            if(num>0){
                return ServerResponse.updateInformationSuccess();
            }
        }
        return ServerResponse.updateInformationFail();
    }


    //10、退出登录
    @RequestMapping("/logout.do")
    @ResponseBody
    public ServerResponse logout(HttpSession session){
        if(session.getAttribute("user")!=null){
            session.setAttribute("user",null);
            return ServerResponse.logoutSuccess();
        }
        return ServerResponse.logoutFail();

    }

}
