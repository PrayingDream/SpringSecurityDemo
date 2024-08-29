package onem.cpx.wmsdemotest.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import onem.cpx.wmsdemotest.domain.UserInfo;
import onem.cpx.wmsdemotest.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserInfoService {
    @Resource
    UserInfoMapper userInfoMapper;
    QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
    UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();

    public List<UserInfo> selectall(){
        wrapper.clear();
        wrapper.ne("delete_sign",1);
        return userInfoMapper.selectList(wrapper);
    }
    public int insert(UserInfo userInfo){
        wrapper.clear();
        wrapper.eq("job_number",userInfo.getJobNumber());
        if (userInfoMapper.selectOne(wrapper) == null){
            return userInfoMapper.insert(userInfo);
        } else {
            return 0;
        }

    }
    public UserInfo selectWithJobNumber(String jobNumber) {
        wrapper.clear();
        wrapper.eq("job_number",jobNumber).ne("delete_sign",1);
        return userInfoMapper.selectOne(wrapper);
    }
    public List<UserInfo> selectWithName(String name) {
        wrapper.clear();
        wrapper.eq("name",name).ne("delete_sign",1);
        return userInfoMapper.selectList(wrapper);
    }
    public int update(UserInfo user){
        return userInfoMapper.updateById(user);
    }
    public int updateWithoutIdNumber(UserInfo userInfo){
        userInfo.setIdNumber(userInfoMapper.selectById(userInfo.getId()).getIdNumber());
        return userInfoMapper.updateById(userInfo);
    }
    public int delete(int id){
        updateWrapper.clear();
        updateWrapper.eq("id",id);
        UserInfo updateUserInfo = new UserInfo();
        updateUserInfo = userInfoMapper.selectById(id);
        updateUserInfo.setDeleteSign(true);
        return userInfoMapper.update(updateUserInfo,updateWrapper);
    }
}
