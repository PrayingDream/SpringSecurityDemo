#### 1.postman可以访问接口 但前端返回403

问题分析:

postman在第一次不携带cookie是访问会返回**403**，并返回cookie，第二次携带后端返回的cookie访问时，可以正常访问并返回，当在Config中加入**http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);**关闭session生成后，postman测试接口不会再返回cookie，第二次测试也不会正常访问，前面postman可以访问是因为使用了cookie认证，由此推断问题可能出在了认证信息的存储上，认证信息没有正常存储到安全上下文。通过在**application.properties**中加入**logging.level.org.springframework.security=DEBUG**开启日志，发现过滤器**AnonymousAuthenticationFilter**将认证信息覆盖，标记成匿名请求。这也验证了前面的分析，认证信息没有正确的流到后续过滤器，确认问题出现在添加安全上下文的自定义过滤器**JwtAuthenticationTonkenFilter**上。

解决办法:

```java
String userName;
        try {
            userName = JwtUtil.validateTokenUserName(token);

            System.out.print("userName:");
            System.out.println(userName);
            
            filterChain.doFilter(request, response);
            //问题出现在此处 在写一半测试时 让请求直接通过自定义过滤器
            //但在后续补全时忘记注释掉，导致控制权被传递给下一个过滤器
            //注释掉即可
            
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
```

#### 2.