package onem.cpx.wmsdemotest.controller;

import onem.cpx.wmsdemotest.domain.UserInfo;
import onem.cpx.wmsdemotest.result.ResultData;
import onem.cpx.wmsdemotest.result.ResultResponse;
import onem.cpx.wmsdemotest.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/userinfo")
public class UserInfoController {
    @Resource
    UserInfoService userInfoService;

    @GetMapping("/selectAll")
    public ResultResponse<List<UserInfo>> selectAll() {
        List<UserInfo> all = userInfoService.selectall();
        System.out.println("开始处理敏感信息");
        if (all != null) {
            for (UserInfo userInfo : all) {
                userInfo.setIdNumber(userInfo.getIdNumber().substring(0, 2) + "**************" + userInfo.getIdNumber().substring(16, 18));
            }
            return ResultData.success(all);
        } else {
            return ResultData.Err(500, "无法查询到可用数据");
        }
    }

    @PostMapping("/insert")
    public ResultResponse<String> insert(@RequestBody UserInfo userInfo) {
        int i = userInfoService.insert(userInfo);
        if (i == 1) {
            return ResultData.success();
        } else if(i == 0){
            return ResultData.Err(500, "添加信息失败,工号不能重复");
        } else{
            return ResultData.Err(500, "服务器内部错误");
        }

    }

    @PostMapping("/select")
    public ResultResponse<List<UserInfo>> selectUser(@RequestBody UserInfo userInfo) {
        List<UserInfo> returnUser = new ArrayList<>();
        if (userInfo.getJobNumber() != null) {
            System.out.println("JobNumber");
            returnUser.add(userInfoService.selectWithJobNumber(userInfo.getJobNumber()));
        } else {
            System.out.println("name");
            returnUser = userInfoService.selectWithName(userInfo.getName());
        }
        if (returnUser != null) {
            return ResultData.success(returnUser);
        } else {
            return ResultData.Err(500, "服务器无此人信息");
        }
    }

    @PostMapping("/delete")
    public ResultResponse<String> delete(@RequestParam int id) {
        int i = userInfoService.delete(id);
        if (i == 1) {
            return ResultData.success();
        } else if (i == 0) {
            return ResultData.Err(500, "服务器无此人信息");
        } else {
            return ResultData.Err(500, "删除错误");
        }
    }

    @PostMapping("/update")
    public ResultResponse<String> update(@RequestBody UserInfo userInfo) {
        boolean updateIdNumber = true;
        String idNumber = userInfo.getIdNumber();
        for (int i = 0; i < idNumber.length(); i++) {
            if (idNumber.charAt(i) == '*') {
                updateIdNumber = false;
                break;
            }
        }
        int i;
        if (updateIdNumber) {
            System.out.println("带身份证号");
            i = userInfoService.update(userInfo);
        } else {
            System.out.println("不带身份证号");
            i = userInfoService.updateWithoutIdNumber(userInfo);
        }

        if (i == 1) {
            return ResultData.success("更新信息成功");
        } else if (i == 0) {
            return ResultData.Err(500, "服务器无此人信息");
        } else {
            return ResultData.Err(500, "更新错误");
        }
    }
}
