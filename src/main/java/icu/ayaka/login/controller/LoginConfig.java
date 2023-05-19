package icu.ayaka.login.controller;

import icu.ayaka.login.dto.LoginFormDTO;
import icu.ayaka.login.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class LoginConfig {

    @Resource
    private IUserService userService;

    /**
     * 登录功能
     * 登录参数，包含手机号、验证码；或者手机号、密码
     */
    @PostMapping("/login/auth")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("code") String code,
                        HttpSession httpSession,
                        Model model){
        //实现登录功能
        return userService.login(new LoginFormDTO(username,password,code), httpSession, model);
    }


}
