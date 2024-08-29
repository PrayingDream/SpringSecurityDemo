package onem.cpx.wmsdemotest;

import onem.cpx.wmsdemotest.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WmsDemoTestApplicationTests {

    @Test
    void contextLoads() {
        String token = JwtUtil.generateToken("cpx","00000");
        System.out.println(token);
        System.out.println(JwtUtil.validateTokenClaims(token));
    }

}
