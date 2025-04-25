package com.kartik.authenticator;

import com.kartik.interfaces.AuthenticationStratergy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class Authenticator implements HandlerInterceptor{
    private ArrayList<AuthenticationStratergy> stratergies;

    @Autowired
    Authenticator(ArrayList<AuthenticationStratergy> strategies)
    {
        this.stratergies = strategies;
        if (this.stratergies.isEmpty()) {
            System.err.println("WARN: No AuthenticationStrategy beans found.");
        }
    }
    Authenticator(){}

    public void addStratergy(AuthenticationStratergy stratergy)
    {
        this.stratergies.add(stratergy);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        if(handler instanceof HandlerMethod handlerMethod)
        {
            if(handlerMethod.hasMethodAnnotation(com.kartik.annotations.Authenticate.class)){
                for(AuthenticationStratergy s : this.stratergies)
                {
                    if(s.authenticate(request, response)){
                        return true;
                    }
                }
                return false;
            }
        }
        return true;
    }
}
