package onem.cpx.wmsdemotest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import onem.cpx.wmsdemotest.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
