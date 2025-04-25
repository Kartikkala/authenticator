package com.kartik.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public interface AuthenticationStratergy {
    Boolean authenticate(HttpServletRequest request, HttpServletResponse response);
}
