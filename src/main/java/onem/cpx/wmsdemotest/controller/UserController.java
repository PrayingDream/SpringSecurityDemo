package onem.cpx.wmsdemotest.controller;

import onem.cpx.wmsdemotest.domain.User;
import onem.cpx.wmsdemotest.result.ResultData;
import onem.cpx.wmsdemotest.result.ResultResponse;
import onem.cpx.wmsdemotest.service.UserService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResultResponse<Map<String,String>> login(@RequestBody User user){
        System.out.println("/login");
        String token = userService.login(user);
        System.out.println("token:");
        System.out.println(token);
        if (StringUtils.hasLength(token)) {
            Map<String,String> map = new HashMap<>();
            map.put("token",token);
            return ResultData.success(map);
        } else {
            return ResultData.Err(400,"登陆失败");
        }

    }

    @PostMapping("/add")
    public void add(@RequestBody User user){
        System.out.println(user);

    }

}
