package com.kartik.authentication;

import com.kartik.authentication.annotations.LogIn;
import com.kartik.authentication.exceptions.XAuthHeaderException;
import com.kartik.authentication.interfaces.AuthenticationStratergy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Service
public class Login implements HandlerInterceptor {
    private final AuthenticationManager manager;

    @Autowired
    public Login(AuthenticationManager manager){
        this.manager = manager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        System.out.println("Login prehandler");
        System.out.println(request.getRequestURI());
        if(handler instanceof HandlerMethod handlerMethod)
        {
            if(handlerMethod.hasMethodAnnotation(LogIn.class)){
                String strategyName = request.getHeader("X-Auth-Strategy");
                if (strategyName == null) {
                    throw new XAuthHeaderException("No X-Auth-Stratergy header found!");
                }
                for(AuthenticationStratergy s : this.manager.getStrategies())
                {
                    if(Objects.equals(s.getName(), strategyName))
                    {
                        return s.login(request, response);
                    }
                }
                // Throw some error here
                return false;
            }
        }
        return true;
    }



}
