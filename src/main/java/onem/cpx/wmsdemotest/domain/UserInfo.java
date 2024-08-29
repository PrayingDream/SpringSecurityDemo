package onem.cpx.wmsdemotest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("test")
public class UserInfo {
    private Integer id;
    private String jobNumber;
    private String name;
    private int age;
    private String idNumber;
    private String phoneNumber;
    private String department;
    private double salary;
    private boolean deleteSign;
}
