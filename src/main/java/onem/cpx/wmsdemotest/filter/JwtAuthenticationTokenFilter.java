package onem.cpx.wmsdemotest.filter;

import io.jsonwebtoken.Claims;
import onem.cpx.wmsdemotest.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.equals("/user/login")) {
            System.out.println("登陆界面,直接放行");
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(JwtUtil.AUTHORIZATION);
        if (!StringUtils.hasText(token)) {
            System.out.println("token为空");
            //throw new RuntimeException("token为空");
            //response.getWriter().print();
        }

        String userName;
        try {
            userName = JwtUtil.validateTokenUserName(token);

            System.out.print("userName:");
            System.out.println(userName);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("token校验失败");
        }

        // 设置通用权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userName,null,authorities);
        System.out.println("A:");
        System.out.println(authentication);
        System.out.println("userName:");
        System.out.println(userName);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        System.out.println("通过token过滤器校验");
        filterChain.doFilter(request, response);

    }
}
