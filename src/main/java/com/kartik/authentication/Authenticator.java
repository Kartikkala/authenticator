package com.kartik.authentication;

import com.kartik.authentication.annotations.Authenticate;
import com.kartik.authentication.interfaces.AuthenticationStratergy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.stereotype.Service;

@Service
public class Authenticator implements HandlerInterceptor{
    private final AuthenticationManager manager;

    @Autowired
    public Authenticator(AuthenticationManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        if(handler instanceof HandlerMethod handlerMethod)
        {
            if(handlerMethod.hasMethodAnnotation(Authenticate.class) ||  handlerMethod.getBeanType().isAnnotationPresent(Authenticate.class)){
                for(AuthenticationStratergy s : this.manager.getStrategies())
                {
                    if(s.supports(request))
                    {
                        if(s.authenticate(request, response)){
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                }
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication stratergies ended. No challege passed!");
                return false;
            }
        }
        return true;
    }
}
