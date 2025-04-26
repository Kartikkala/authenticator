package com.kartik.authentication;

import com.kartik.authentication.interfaces.AuthenticationStratergy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationManager {
    private final List<AuthenticationStratergy> strategies = new ArrayList<>();

    public AuthenticationManager(List<AuthenticationStratergy> strategies) {
        if (strategies.isEmpty()) {
            System.err.println("WARN: No AuthenticationStrategy beans found.");
        }
        this.strategies.addAll(strategies);
    }

    public List<AuthenticationStratergy> getStrategies() {
        return strategies;
    }

    public void addStrategy(AuthenticationStratergy strategy) {
        strategies.add(strategy);
    }
}
