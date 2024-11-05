package com.napico.sbb;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ControllerSupport {
    @ModelAttribute("rootUrl")
    public String rootUrl(HttpServletRequest request) {
        String scheme = request.getScheme();                        // http
        String method = request.getMethod();                        // GET
        String serverName = request.getServerName();                // localhost
        int serverPort = request.getServerPort();                   // 8080
        String contextPath = request.getContextPath();              // /user
        String servletPath = request.getServletPath();              // /temp
        String requestURI = request.getRequestURI();                // /user/temp
        String requestURL = request.getRequestURL().toString();     // http://localhost:8080/user/temp
        String queryString = request.getQueryString();              //
        //return requestURI + (queryString != null ? "?" + queryString : "");
        return scheme + "://" + serverName + ":" + serverPort;
    }

    @ModelAttribute("currentUser")
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "testUser";
    }
}
