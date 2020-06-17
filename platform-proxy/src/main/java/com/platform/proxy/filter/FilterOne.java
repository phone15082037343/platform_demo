package com.platform.proxy.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class FilterOne extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("FilterOne");
        RequestContext context = RequestContext.getCurrentContext();
        context.addZuulRequestHeader("user", "admin user");
        HttpServletRequest request = context.getRequest();
        request.setAttribute("user", "admin");
        return null;
    }

}
