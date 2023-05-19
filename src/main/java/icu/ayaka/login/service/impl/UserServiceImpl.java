package icu.ayaka.login.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.ayaka.config.Result;
import icu.ayaka.login.dto.LoginFormDTO;
import icu.ayaka.login.dto.User;
import icu.ayaka.login.dto.UserDTO;
import icu.ayaka.login.mapper.UserMapper;
import icu.ayaka.login.service.IUserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    /**
     * 注入RedisTemplate
     */
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 登录功能 redis
     */
    @Override
    public String login(LoginFormDTO loginForm, HttpSession session, Model model) {

        //验证码
        String code = loginForm.getCode();
        Object sessionCode = session.getAttribute("code");
        if (code == null || !code.equals(sessionCode)){
            model.addAttribute("errorMsg", "验证码错误！");
            return "login";
        }

        //用户名
        String username = loginForm.getUsername();
        //密码
        String password = loginForm.getPassword();

        //判断 账户-密码 是否正确
        User user = query()
                .eq("username", username)
                .eq("password",password)
                .one();
        //不存在
        if (ObjectUtils.isEmpty(user)) {
            model.addAttribute("errorMsg", "用户名或密码错误！");
            return "login";
        }

        //生成user对象
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setNickName(user.getNickName());

        //将 User对象 保存在redis 使用hash结构
        // 键：token
        String token = "api:token:" + username;
        // 值：用户信息
        Map<String, String> userMap = new HashMap<>();
        userMap.put(UserDTO.USER_DTO_ID, userDTO.getId().toString());
        userMap.put(UserDTO.USER_DTO_USERNAME, userDTO.getUsername());
        userMap.put(UserDTO.USER_DTO_NICKNAME, userDTO.getNickName());
        //存储
        stringRedisTemplate.opsForHash().putAll(token, userMap);
        //设置有效时间 1 小时
        stringRedisTemplate.expire(token, 1, TimeUnit.HOURS);

        //返回给前端用户数据 token
        model.addAttribute("errorMsg", "登录成功！TODO:页面开发中...");
        return "login";
    }

}
