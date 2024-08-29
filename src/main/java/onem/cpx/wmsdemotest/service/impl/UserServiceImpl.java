package onem.cpx.wmsdemotest.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import onem.cpx.wmsdemotest.domain.LoginUser;
import onem.cpx.wmsdemotest.domain.User;
import onem.cpx.wmsdemotest.mapper.UserMapper;
import onem.cpx.wmsdemotest.service.UserService;
import onem.cpx.wmsdemotest.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public String login(User user) {
        // 封装authenticationToken对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(),user.getPassword(),null);
        // 通过AuthenticationManager的authenticate方法来进行用户验证
        System.out.println(JSON.toJSONString(authenticationToken));
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 认证不通过就抛出异常
        if(Objects.isNull(authenticate)) {
            throw new RuntimeException("登陆失败");
        }
        System.out.println("::");
        System.out.println(authenticate.getPrincipal());
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String jwt = JwtUtil.generateToken(loginUser.getUsername());
        //
        return jwt;
    }

    @Override
    public String add(User user) {
        return null;
    }
}
