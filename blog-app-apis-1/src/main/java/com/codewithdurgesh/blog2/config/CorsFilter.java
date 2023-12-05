package com.codewithdurgesh.blog2.config;

import java.io.IOException;
import org.springframework.core.annotation.Order;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;

@WebFilter("/*")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		 HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
	        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
	        httpServletResponse.setHeader("Access-Control-Max-Age", "3600"); // 1 hour

	        chain.doFilter(request, response);
		
	}
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
