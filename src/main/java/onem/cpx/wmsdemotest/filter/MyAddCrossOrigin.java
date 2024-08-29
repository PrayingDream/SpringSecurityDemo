/*
package onem.cpx.wmsdemotest.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
public class MyAddCrossOrigin implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        //允许来自任何源的跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");
        //设置允许的请求头为"X-Requested-With"和"Content-Type"。这样设置允许浏览器发送带有这些请求头的跨域请求。
        response.setHeader("Access-Control-Allow-Headers","X-Requested-With, Content-Type");
        //指定了服务器允许的跨域请求方法
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE");
        filterChain.doFilter(servletRequest,response);


    }
}
*/
