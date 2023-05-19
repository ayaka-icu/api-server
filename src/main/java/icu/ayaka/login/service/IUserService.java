package icu.ayaka.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.ayaka.config.Result;
import icu.ayaka.login.dto.LoginFormDTO;
import icu.ayaka.login.dto.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface IUserService extends IService<User> {

    String login(LoginFormDTO loginForm, HttpSession session, Model model);

}
