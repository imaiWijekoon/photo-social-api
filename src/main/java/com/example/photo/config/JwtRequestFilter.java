package com.example.photo.config;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    // Define paths to exclude from token validation
    private static final String[] EXCLUDED_PATHS = {
            "/api/**" // Exclude all paths starting with /api
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Check if the current request path matches any excluded path
        boolean isExcludedPath = false;
        for (String excludedPath : EXCLUDED_PATHS) {
            if (path.matches(excludedPath.replace("**", ".*"))) { // Match wildcard paths
                isExcludedPath = true;
                break;
            }
        }

        // Skip token validation for excluded paths
        if (isExcludedPath) {
            chain.doFilter(request, response);
            return;
        }

        // Proceed with token validation for other paths
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwtToken);
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token expired");
                return;
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token");
                return;
            }
        }

        if (username != null && jwtUtil.validateToken(jwtToken, username)) {
            request.setAttribute("username", username);
        } else if (authorizationHeader != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or missing token");
            return;
        }

        chain.doFilter(request, response);
    }
}