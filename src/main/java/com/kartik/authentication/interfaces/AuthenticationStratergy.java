package com.kartik.authentication.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public interface AuthenticationStratergy {
    Boolean authenticate(HttpServletRequest request, HttpServletResponse response);
    Boolean login(HttpServletRequest request, HttpServletResponse response) throws IOException;
    Boolean supports(HttpServletRequest request);
    String getName();
}
