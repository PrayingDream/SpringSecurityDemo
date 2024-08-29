package onem.cpx.wmsdemotest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import onem.cpx.wmsdemotest.domain.User;

public interface UserService extends IService<User> {

    String login(User user);
    String add(User user);
}
