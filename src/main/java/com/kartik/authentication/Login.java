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
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Service
public class Login implements HandlerInterceptor {
    private final AuthenticationManager manager;

    @Autowired
    public Login(AuthenticationManager manager){
        this.manager = manager;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
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
                        if(s.login(request, response))
                        {
                            response.setStatus(HttpServletResponse.SC_OK);
                            return;
                        }
                        return;
                    }
                }
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }



}
