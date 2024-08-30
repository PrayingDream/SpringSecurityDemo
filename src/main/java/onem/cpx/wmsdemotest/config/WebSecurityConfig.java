package onem.cpx.wmsdemotest.config;

import onem.cpx.wmsdemotest.filter.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;


// 配置类
@Configuration
// 开启SpringSecurity自定义配置
@EnableWebSecurity
public class WebSecurityConfig {

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationFilter;

    public static final String[] AUTH_WHITELIST = {
            "/user/login"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 关闭csrf防御
        http.csrf(csrf -> csrf.disable());
        //支持跨域访问
        http.cors();
        // 被指请求拦截方式
        // permitAll:随意访问
        http.authorizeRequests(auth -> auth.regexMatchers(AUTH_WHITELIST)
                .permitAll()
                .antMatchers("/hello").hasRole("USER")
                .anyRequest()
                .authenticated());
        //将token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        //设置session失效
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.httpBasic();
        return http.build();
    }

    /**
     *登陆时需要调用AuthenticationManager.authenticate方法进行一次校验
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * 解决spring security环境下前端请求跨域问题
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


}