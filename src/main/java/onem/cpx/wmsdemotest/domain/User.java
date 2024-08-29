package onem.cpx.wmsdemotest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private Boolean enabled;
    private Map<String,?> roles;
}
