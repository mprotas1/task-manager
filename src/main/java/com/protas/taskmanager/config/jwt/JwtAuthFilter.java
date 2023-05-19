package com.protas.taskmanager.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwt;
        String username;

        // if our request is not request containing the JWT token -> pass it to the next filter
        if(authHeader == null ||!authHeader.startsWith("Bearer ")) {

            // passing the request and response to the next filter in FilterChain
            filterChain.doFilter(request, response);
            return;
        }

        // get JWT token from header
        jwt = authHeader.substring(7);

        username = jwtUtils.extractUsername(jwt);

        // if username is present and user is not authenticated
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // get UserDetails from database
            UserDetails user = this.userDetailsService.loadUserByUsername(username);

            // check if user and token are valid
            if(jwtUtils.isTokenValid(jwt, user)) {

                // update SecurityContext
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // update SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(request, response);
        }
    }
}
