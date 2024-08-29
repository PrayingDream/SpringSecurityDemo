package onem.cpx.wmsdemotest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import onem.cpx.wmsdemotest.domain.LoginUser;
import onem.cpx.wmsdemotest.domain.User;
import onem.cpx.wmsdemotest.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 连接数据库,根据用户名查询账号信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        System.out.println(username);
        User user = userMapper.selectOne(queryWrapper);

        if(Objects.isNull(user)) {
            System.out.println("用户不存在");
        }

        // 赋权


        // 返回对象
        return new LoginUser(user);
    }
}
