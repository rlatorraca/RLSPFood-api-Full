package ca.com.rlsp.rlspfoodapi.core.web;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
    Interceptador de metodos dos Controladores do Spring Boot
 */
@Component
public class ApiDeprecationHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getRequestURI().startsWith("/v1/")) {
            response.addHeader("x-RLSPFood-Deprecated",
                    "API for Canada and Maritimes restaurants.<br/>This API " +
                            "is deprecated and deadline to use that is December 31, 2021");
        }
        return true;
    }
}
